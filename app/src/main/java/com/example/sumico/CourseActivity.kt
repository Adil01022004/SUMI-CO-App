package com.example.sumico

import CourseAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class CourseActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private val courseList = mutableListOf<CourseInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_course)
        recyclerView.layoutManager = LinearLayoutManager(this)

        database = FirebaseDatabase.getInstance().getReference("course")

        // Получаем все курсы
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                courseList.clear()
                for (courseSnapshot in snapshot.children) {
                    val courseName = courseSnapshot.child("name").getValue(String::class.java) ?: "Unknown Course"
                    val text = courseSnapshot.child("text").getValue(String::class.java) ?: "No Description"
                    val imageURL = courseSnapshot.child("imageURL").getValue(String::class.java) ?: ""

                    val course = CourseInfo(courseName, text, imageURL)
                    courseList.add(course)
                }

                Log.d("FirebaseCourse", "Курсы загружены: ${courseList.size}")

                // Обновляем RecyclerView после загрузки данных

                recyclerView.adapter = CourseAdapter(courseList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseCourse", "Ошибка при загрузке данных: ${error.message}")
            }
        })
    }
}
