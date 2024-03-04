package com.example.googleauth.ModleClass

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.googleauth.R


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

    @SuppressLint("ViewHolder", "MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.data_list, parent, false)

        val name = view.findViewById<TextView>(R.id.studentName_textView)
        val email = view.findViewById<TextView>(R.id.student_email_text_view)
        val updateImage = view.findViewById<ImageView>(R.id.updateButton)
        val deleteImage = view.findViewById<ImageView>(R.id.deleteButton)

        name.text = userList[position].name
        email.text = userList[position].email

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


