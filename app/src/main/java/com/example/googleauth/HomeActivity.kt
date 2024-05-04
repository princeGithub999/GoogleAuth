package com.example.googleauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.googleauth.Adapter.StudentAdapter
import com.example.googleauth.ModleClass.StudentModle
import com.example.googleauth.databinding.ActivityHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList


class HomeActivity : AppCompatActivity(), StudentAdapter.SetOnStudentClcickLisner {

    private lateinit var binding: ActivityHomeBinding
    private var db = FirebaseFirestore.getInstance()
    private lateinit var studentAdpater: StudentAdapter

    private val studentArray = ArrayList<StudentModle>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        studentAdpater = StudentAdapter(this,studentArray,this)
        binding.studentListView.adapter = studentAdpater

        showStudentData()
        binding.addButton.setOnClickListener {
            startActivity(Intent(this, AddStudentDataActivity::class.java))
            finish()
        }

    }

    private fun showStudentData() {
        db.collection("student").get()
            .addOnSuccessListener {
                val data = it.toObjects(StudentModle::class.java)
                studentAdpater = StudentAdapter(this, data, this)
                binding.studentListView.adapter = studentAdpater

            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }

    }
    override fun onUpdateClcik(list: StudentModle) {
        val intent = Intent(this,UpdateActivity::class.java)
        intent.putExtra("id_key",list.id.toString())
        intent.putExtra("name_key",list.name.toString())
        intent.putExtra("email_key",list.email.toString())
        intent.putExtra("image",list.image)
        startActivity(intent)
    }
    override fun onDeleteClcik(list: StudentModle) {
     deleteStudentData(list.id.toString())

    }

    private fun deleteStudentData(id: String){
        db.collection("student").document(id.toString()).delete()
            .addOnSuccessListener {
                showStudentData()
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                Toast.makeText(this, "Delete filed", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onResume() {
        super.onResume()

    }
}

