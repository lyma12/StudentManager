package com.example.studentmanager

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader


class form : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_form, container, false)

        val items = mutableListOf<String>()

// Đọc dữ liệu từ file text
        val inputStream = resources.openRawResource(R.raw.data) // Giả sử ID tài nguyên là R.raw.data
        val reader = InputStreamReader(inputStream)
        val lines = reader.readLines()
        reader.close()

// Xử lý dữ liệu
        lines.forEach {
            items.add(it)
        }

// Tạo một adapter cho autocompleteView
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)

// Gán adapter cho autocompleteView
        val autocompleteView = view.findViewById<AutoCompleteTextView>(R.id.actv_que_quan)
        autocompleteView.setAdapter(adapter)

        val editTextNgaySinh = view.findViewById<EditText>(R.id.at_ngay_sinh)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    editTextNgaySinh.setText("${year}-${month + 1}-${dayOfMonth}")
                }
            },
            2023,
            0,
            1
        )

        editTextNgaySinh.setOnClickListener {
            datePickerDialog.show()
        }

        view.findViewById<Button>(R.id.btn_luu).setOnClickListener{
            val ho_ten: String = view.findViewById<EditText>(R.id.et_ho_ten).text.toString()
            val ngay_sinh: String = view.findViewById<EditText>(R.id.at_ngay_sinh).text.toString()
            val mssv: String = view.findViewById<EditText>(R.id.et_mssv).text.toString()
            val que_quan: String = view.findViewById<AutoCompleteTextView>(R.id.actv_que_quan).text.toString()
            insertDatatoDataBase(ho_ten, ngay_sinh, mssv, que_quan)
        }
        return view
    }
    private fun insertDatatoDataBase(ho_ten: String, ngay_sinh: String, mssv: String, que_quan: String) {
        if(checkData(ho_ten, ngay_sinh, mssv, que_quan)){
            val student: Student = Student(0, ho_ten, mssv, ngay_sinh, que_quan)
            val studentDao = StudentDatabase.getDatabase(requireContext()).studentDao()
            lifecycleScope.launch(Dispatchers.IO) {
                val id = studentDao.insertStudent(student)
                withContext(Dispatchers.Main) {
                    if (id > 0) {
                        Toast.makeText(requireContext(), "New record inserted", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(requireContext(), "Insert failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_form_to_list)
        }
        else{
            Toast.makeText(requireContext(), "Fill out all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkData(hoTen: String, ngaySinh: String, mssv: String, queQuan: String): Boolean {
        if(hoTen.isEmpty()) return false
        if(ngaySinh.isEmpty()) return false
        if(mssv.isEmpty()) return false
        if(queQuan.isEmpty()) return false
        if(mssv.length < 8) return false
        return true
    }


}