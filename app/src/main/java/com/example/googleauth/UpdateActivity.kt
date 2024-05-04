package com.example.googleauth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.googleauth.ModleClass.StudentModle
import com.example.googleauth.databinding.ActivityUpdateBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private val db = FirebaseFirestore.getInstance()
    private var uri: Uri? = null
    private var imageChildName: String? = null

    var id: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("id_key")
        val name = intent.getStringExtra("name_key")
        val email = intent.getStringExtra("email_key")
        imageChildName = intent.getStringExtra("image")


        binding.updateNameEditText.setText(name)
        binding.updateGenderEditeText.setText(email)

        FirebaseStorage.getInstance().reference
            .child(imageChildName!!)
            .downloadUrl
            .addOnSuccessListener {
                Glide.with(this)
                    .load(it)
                    .apply(RequestOptions())
                    .into(binding.updateImage)
            }


        binding.updateButton.setOnClickListener {
            UpdateData()
        }
        binding.updateImage.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, ""), 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            try {
                uri = imageUri
                binding.updateImage.setImageURI(imageUri)
                Toast.makeText(this, "Image is select", Toast.LENGTH_SHORT).show()
                    UplodeImage()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun UplodeImage() {
        if (uri == null) {
            Toast.makeText(this, "No select Image", Toast.LENGTH_SHORT).show()
            return
        } else {
            FirebaseStorage.getInstance().reference
                .child(imageChildName!!)
                .putFile(uri!!)
                .addOnSuccessListener {
                    Toast.makeText(this, "Image Uplode success", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                .addOnFailureListener {
                    Toast.makeText(this, "Image Uplode field", Toast.LENGTH_SHORT).show()
                    return@addOnFailureListener
                }
        }

    }

    private fun UpdateData() {
        uri.let {
            val imageSheya = FirebaseStorage.getInstance().reference
                .child("Image")

            imageSheya.putFile(uri!!).addOnSuccessListener {
                imageSheya.downloadUrl.addOnSuccessListener {
                    val u_Name = binding.updateNameEditText.text.toString()
                    val u_email = binding.updateGenderEditeText.text.toString()

                    val hiiMap = mapOf(
                        "name" to u_Name,
                        "email" to u_email,
                        "Image" to imageChildName
                    )

                    db.collection("student").document(id!!).update(hiiMap)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Update fell", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}

