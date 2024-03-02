package com.example.googleauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.googleauth.databinding.ActivityOtpVerificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken

class OtpVerification : AppCompatActivity() {

    private lateinit var binding: ActivityOtpVerificationBinding

    private lateinit var auth : FirebaseAuth
    private lateinit var otp:String
    private lateinit var number: String
    private lateinit var resendingToken: PhoneAuthProvider.ForceResendingToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        otp = intent.getStringExtra("OTP").toString()
        number = intent.getStringExtra("number").toString()
        resendingToken = ((intent.getParcelableExtra("ResendToken") as? ForceResendingToken)!!)

        binding.verifyButton.setOnClickListener {
                VerifyOtp()
        }
        binding.backArow.setOnClickListener {
            onBackPressed()
        }

    }
   private fun VerifyOtp(){
        val otpText = binding.pinview.text.toString()
        val phoneAuthCredential = PhoneAuthProvider.getCredential(otp ,otpText)
       auth.signInWithCredential(phoneAuthCredential)
           .addOnSuccessListener {
               Toast.makeText(this, "SignUp success", Toast.LENGTH_SHORT).show()
               startActivity(Intent(this,HomeActivity::class.java))
               finish()
           }
           .addOnFailureListener {
               Toast.makeText(this, "Sign fleid", Toast.LENGTH_SHORT).show()

           }

    }
}