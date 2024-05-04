package com.example.googleauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.googleauth.databinding.SignupActivityBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: SignupActivityBinding

    var verificationID = ""
    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signUpButton.setOnClickListener {

           SendOtp()

        }


        binding.alreadyLoginTextView.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

    private fun signUp() {
        val email = binding.emailEditText.text.toString()
        val pass = binding.passwordEditText.text.toString()
        val confromPassword = binding.confromPasswordEditText.text.toString()
        val number = binding.numberEditText.text.toString()

        if (email.isBlank() || pass.isBlank() || confromPassword.isBlank() || number.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        if (pass != confromPassword) {
            Toast.makeText(this, "Dont mach email and password", Toast.LENGTH_SHORT).show()
        }
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "fled SignUp ", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun SendOtp(){
        var number = ""
        number ="+91${binding.numberEditText.text}"
        val phoneAuthOptions = PhoneAuthOptions.newBuilder()
            .setActivity(this)
            .setPhoneNumber(number)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Toast.makeText(this@SignUpActivity, "Verification compledt", Toast.LENGTH_SHORT).show()

                    signUp()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(this@SignUpActivity, "Verification failed ${p0.message}", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, token)
                    verificationID = verificationId

                    val intent = Intent(this@SignUpActivity, OtpVerification::class.java)
                    intent.putExtra("OTP",verificationId)
                    intent.putExtra("ResendToken",token)
                    intent.putExtra("number",number)
                    startActivity(intent)
                }

            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }


}