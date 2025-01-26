package com.example.sumico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    private lateinit var courseButton: LinearLayout
    private lateinit var markButton: LinearLayout
    private lateinit var recordButton: LinearLayout
    private lateinit var scheduleButton: LinearLayout
    private lateinit var competitionsButton: LinearLayout
    private lateinit var commandToSubjectButton: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        courseButton = findViewById(R.id.course_button)
        markButton = findViewById(R.id.mark_button)
        recordButton = findViewById(R.id.record_button)
        scheduleButton = findViewById(R.id.schedule_button)
        competitionsButton = findViewById(R.id.competitions_button)
        commandToSubjectButton = findViewById(R.id.command_to_subject_button)



        try {
            courseButton.setOnClickListener{
                val intent = Intent(this, CourseActivity::class.java)
                intent.putExtra("some_data", "example")
                startActivity(intent)
            }
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        }








    }
}