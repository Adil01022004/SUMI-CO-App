package com.example.sumico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CourseActivity : AppCompatActivity() {

    lateinit var nullableContentText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        nullableContentText = findViewById(R.id.null_content)




    }
}