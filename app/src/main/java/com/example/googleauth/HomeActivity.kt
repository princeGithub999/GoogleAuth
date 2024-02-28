package com.example.googleauth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.googleauth.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.logOutButton.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "signOut success", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MenyPletForm::class.java))
        }


        val imageUrl = auth.currentUser?.photoUrl
            Glide.with(this).load(imageUrl).into(binding.emailImage)

        binding.displyName.text=auth.currentUser?.displayName
        binding.email.text = auth.currentUser?.email
    }
}
