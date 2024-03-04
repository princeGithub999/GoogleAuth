package com.example.googleauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.googleauth.databinding.ActivityUpdateBinding
import com.google.firebase.firestore.FirebaseFirestore

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id_key")
        val name = intent.getStringExtra("name_key")
        val email = intent.getStringExtra("email_key")

        binding.updateNameEditText.setText(name)
        binding.updateGenderEditeText.setText(email)

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