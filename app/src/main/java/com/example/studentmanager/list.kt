package com.example.studentmanager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class list : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_list, container, false)

        val studentDao = StudentDatabase.getDatabase(requireContext()).studentDao()
        val adapter = StudentAdapter()
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.setOnClickListener(object:
            StudentAdapter.OnClickListener{
            override fun onClick(position: Int, model: Student) {
                findNavController().navigate(R.id.action_list_to_infoStudent)
            }
            }
        )

        lifecycleScope.launch(Dispatchers.IO) {
            val students = studentDao.getAllStudents()
            adapter.setDate(students)
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton5).setOnClickListener{
            findNavController().navigate(R.id.action_list_to_form)
        }

        return view
    }

}