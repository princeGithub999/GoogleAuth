package com.example.googleauth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.bumptech.glide.Glide
import com.example.googleauth.ModleClass.ModleClass
import com.example.googleauth.databinding.ActivityHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private var db = FirebaseFirestore.getInstance()


    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        binding.logOutButton.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "signOut success", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MenyPletForm::class.java))
            finish()
        }

        binding.addButton.setOnClickListener {
            val view = LayoutInflater.from(this).inflate(R.layout.add_data, null, false)
            val dailog = AlertDialog.Builder(this)
            val create = dailog.create()
            create.setView(view)
            val rollNumberEdit: EditText = view.findViewById(R.id.rollNumber)
            val nameEdit: EditText = view.findViewById(R.id.nameStudent_EditText)
            val subjectEdit: EditText = view.findViewById(R.id.subject_EditeText)
            val addButton: AppCompatButton = view.findViewById(R.id.submit)
            create.show()

            addButton.setOnClickListener {
                val rollNumber = rollNumberEdit.text.toString()
                val name = nameEdit.text.toString()
                val subject = subjectEdit.text.toString()

                val userMap =
                    hashMapOf("rollNumber" to rollNumber, "name" to name, "subject" to subject)

                db.collection("school")
                    .add(userMap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "SuccessFully add", Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show()
                    }
                create.dismiss()
            }

        }

        db.collection("school")
            .get()
            .addOnSuccessListener {
                val result = it.toObjects(ModleClass::class.java)

                try {
                    binding.rollTextView.text = result[0].rollNumber.toString()
                    binding.nameTextView.text = result[0].name
                }
                catch (error: Exception){
                    Log.e("", error.message.toString() )
                }

            }


//        val imageUrl = auth.currentUser?.photoUrl
//            Glide.with(this).load(imageUrl).into(binding.emailImage)
//
//        binding.displyName.text=auth.currentUser?.displayName
//        binding.email.text = auth.currentUser?.email
    }

}
