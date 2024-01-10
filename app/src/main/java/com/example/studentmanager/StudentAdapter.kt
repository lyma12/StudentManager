package com.example.studentmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(): RecyclerView.Adapter<StudentAdapter.ViewHolder>(){
    private var students = emptyList<Student>()
    private var onClickListener: OnClickListener? = null
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvMssv: TextView = itemView.findViewById(R.id.tv_mssv)
        val tvHoTen: TextView = itemView.findViewById(R.id.tv_ho_ten)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.tvMssv.text = student.mssv
        holder.tvHoTen.text = student.ho_ten
        holder.tvEmail.text = createEmail(student.mssv, student.ho_ten)
        holder.itemView.setOnClickListener{
            if (onClickListener != null) {
                onClickListener!!.onClick(position, students[position] )
            }
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }

    private fun createEmail(mssv: String, hoTen: String): String{
        hoTen.toLowerCase();
        val lastIndex = hoTen.lastIndexOf(" ")
        val ten = hoTen.subSequence(lastIndex + 1, hoTen.length)
        var ho = hoTen.substring(0, lastIndex - 1)
        var index = ho.indexOf(" ")
        var email: String = ""
        while(ho.length > 0 && index <= ho.length && index > 0 ){
            email = email + ho[0]
            ho = ho.substring(index + 1, ho.length)
            index = ho.indexOf(" ")
        }
        email = email + ho[0]
        return "$ten.$email${mssv.substring(0, 5)}@sis.hust.edu.vn"
    }
    fun setDate(students: List<Student>){
        this.students = students
        notifyDataSetChanged()
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Student)
    }
}