package com.example.studentmanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {

    @Insert
    suspend fun insertStudent(student: Student): Long

    @Query("select * from student where id = :id")
    suspend fun findColorById(id: Int): Student

    @Update
    suspend fun updateStudent(student: Student): Int

    @Delete
    suspend fun deleteStudent(student: Student): Int

    @Query("SELECT * FROM student ORDER BY id ASC")
    suspend fun getAllStudents(): List<Student>
}