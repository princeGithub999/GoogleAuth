package com.example.googleauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.googleauth.databinding.AddStudentDataBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class AddStudentDataActivity : AppCompatActivity() {

    private lateinit var binding: AddStudentDataBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddStudentDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener {
            val name = binding.nameStudentEditText.text.toString()
            val gender = binding.emailEditeText.text.toString()

            val uid = UUID.randomUUID().toString()

            val map = hashMapOf(
                "id" to uid,
                "name" to name,
                "email" to gender
            )
            db.collection("student").document(uid).set(map)
                .addOnSuccessListener {
                    Toast.makeText(this, "User Add", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
        }

    }
}