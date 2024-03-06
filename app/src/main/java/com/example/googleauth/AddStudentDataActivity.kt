package com.example.googleauth

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.googleauth.databinding.AddStudentDataBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso
import java.util.UUID

class AddStudentDataActivity : AppCompatActivity() {

    private lateinit var binding: AddStudentDataBinding
    private val db = FirebaseFirestore.getInstance()
    private var uri: Uri? = null
    private val imageChildName = "${UUID.randomUUID()}"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddStudentDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener {
            setUserData()
        }


        binding.addImage.setOnClickListener {
            openGallery()
        }

    }
    private fun setUserData(){
        uri?.let {

            val progressBar = ProgressDialog(this)
            progressBar.setTitle("User Added please wait!")
            progressBar.setMessage("Processing...")
            progressBar.show()
            val imageShare = FirebaseStorage.getInstance().reference
                .child("Image")
                .child(UUID.randomUUID().toString())

            imageShare.putFile(uri!!).addOnSuccessListener {

                imageShare.downloadUrl.addOnSuccessListener {
                    val id = UUID.randomUUID().toString()

                  val name = binding.nameStudentEditText.text.toString()
                  val email = binding.emailEditeText.text.toString()
                    val map = hashMapOf(
                        "id" to id,
                        "name" to name,
                        "email" to email,
                        "image" to  imageChildName
                    )
                    db.collection("student").document(id).set(map)
                        .addOnSuccessListener {
                            progressBar.dismiss()
                            Toast.makeText(this, "User Add", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        }
                }
            }
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
                binding.addImage.setImageURI(imageUri)
                UplodeImage()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
   private fun UplodeImage(){
        if (uri == null){
            Toast.makeText(this, "No select Image", Toast.LENGTH_SHORT).show()
            return
        }
       val imageRefrence = FirebaseStorage.getInstance().reference
           .child(imageChildName)

       val uplodeTask = imageRefrence.putFile(uri!!)
       uplodeTask.addOnSuccessListener {
           Toast.makeText(this, "Image Uplode success", Toast.LENGTH_SHORT).show()
           return@addOnSuccessListener
       }
           .addOnFailureListener {
               Toast.makeText(this, "Image Uplode field", Toast.LENGTH_SHORT).show()
           }
    }
}


