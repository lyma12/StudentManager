package com.example.studentmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ho_ten: String,
    val mssv: String,
    val ngay_sinh: String,
    val que_quan: String)