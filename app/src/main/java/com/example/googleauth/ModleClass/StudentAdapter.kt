package com.example.googleauth.ModleClass

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.example.googleauth.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class StudentAdapter(
    var context: Context,
    private var userList: List<StudentModle>,
    private var userClickListener: SetOnStudentClcickLisner
) : BaseAdapter() {
    override fun getCount(): Int {
        return userList.size
    }

    override fun getItem(position: Int): Any {
        return userList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "MissingInflatedId", "CheckResult")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.data_list, parent, false)

        val name = view.findViewById<TextView>(R.id.studentName_textView)
        val email = view.findViewById<TextView>(R.id.student_email_text_view)
        val updateImage = view.findViewById<ImageView>(R.id.updateButton)
        val deleteImage = view.findViewById<ImageView>(R.id.deleteButton)
        val uplodeImage = view.findViewById<ShapeableImageView>(R.id.addListImage)

        name.text = userList[position].name
        email.text = userList[position].email

        val imageStorage = FirebaseStorage.getInstance().reference
            .child(userList[position].image!!)
            .downloadUrl
            .addOnSuccessListener {
                Glide.with(context)
                    .load(it)
                    .apply(RequestOptions())
                    .into(uplodeImage)
                Toast.makeText(parent?.context, "image downloadUrl Success", Toast.LENGTH_SHORT)
                    .show()
                return@addOnSuccessListener
            }.addOnFailureListener {
                Toast.makeText(context, "Image downloading failed", Toast.LENGTH_SHORT).show()
                return@addOnFailureListener
            }
        updateImage.setOnClickListener {
            userClickListener.onUpdateClcik(userList[position])

        }
        deleteImage.setOnClickListener {
            userClickListener.onDeleteClcik(userList[position])
        }

        return view
    }

    interface SetOnStudentClcickLisner {
        fun onDeleteClcik(list: StudentModle)
        fun onUpdateClcik(list: StudentModle)
    }


}


