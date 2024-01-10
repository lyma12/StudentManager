package com.example.studentmanager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoStudent : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_info_student, container, false)
        val studentDao = StudentDatabase.getDatabase(requireContext()).studentDao()
        lifecycleScope.launch(Dispatchers.IO) {
            var student: Student = studentDao.findColorById(1)
            view.findViewById<TextView>(R.id.t_ho_ten).text = student.ho_ten
            view.findViewById<TextView>(R.id.t_mssv).text = student.mssv
            view.findViewById<TextView>(R.id.t_ngay_sinh).text = student.ngay_sinh
            view.findViewById<TextView>(R.id.t_que_quan).text = student.que_quan
            var email = getEmail(student.mssv, student.ho_ten)
            view.findViewById<TextView>(R.id.emailText).text = email


        }
        view.findViewById<Button>(R.id.btn_delete).setOnClickListener{
            findNavController().navigate(R.id.action_infoStudent_to_list)
        }
        view.findViewById<Button>(R.id.btn_cap_nhat).setOnClickListener{
            findNavController().navigate(R.id.action_infoStudent_to_form)
        }

        return view
    }

    private fun getEmail(mssv: String, hoTen: String): String {
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


}