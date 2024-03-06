package com.example.googleauth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.googleauth.ModleClass.StudentModle
import com.example.googleauth.databinding.ActivityUpdateBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private val db = FirebaseFirestore.getInstance()
    private val uri:Uri?=null
    private lateinit var userList:StudentModle
    private val imageChildName = "${UUID.randomUUID()}"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id_key")
        val name = intent.getStringExtra("name_key")
        val email = intent.getStringExtra("email_key")



        binding.updateNameEditText.setText(name)
        binding.updateGenderEditeText.setText(email)


        val imageStore = FirebaseStorage.getInstance().reference


        binding.updateButton.setOnClickListener {
            val u_Name = binding.updateNameEditText.text.toString()
            val u_email = binding.updateGenderEditeText.text.toString()

            val hiiMap = mapOf(
                "name" to u_Name,
                "email" to u_email
            )

            db.collection("student").document("$id").update(hiiMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Update fell", Toast.LENGTH_SHORT).show()
                }

        }
    }

}