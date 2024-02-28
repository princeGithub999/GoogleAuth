package com.example.googleauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SplaceScreen : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splace_screen)

        auth = FirebaseAuth.getInstance()

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            if (auth.currentUser != null){
                startActivity(Intent(this,HomeActivity::class.java))
            } else{
               startActivity(Intent(this,MenyPletForm::class.java))
            }
            finish()
        }, 3000)

    }

}