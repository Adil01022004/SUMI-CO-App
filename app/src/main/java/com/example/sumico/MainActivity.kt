package com.example.sumico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {


    private lateinit var courseButton: LinearLayout
    private lateinit var markButton: LinearLayout
    private lateinit var recordButton: LinearLayout
    private lateinit var scheduleButton: LinearLayout
    private lateinit var competitionsButton: LinearLayout
    private lateinit var commandToSubjectButton: LinearLayout

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menuButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val firebaseDb = Firebase.database
        val myRef = firebaseDb.getReference("message")

        //myRef.setValue("Hello World")


        courseButton = findViewById(R.id.course_button)
        markButton = findViewById(R.id.mark_button)
        recordButton = findViewById(R.id.record_button)
        scheduleButton = findViewById(R.id.schedule_button)
        competitionsButton = findViewById(R.id.competitions_button)
        commandToSubjectButton = findViewById(R.id.command_to_subject_button)
        drawerLayout = findViewById(R.id.drawer_layout)
        menuButton = findViewById(R.id.menu)


        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }




        try {
            courseButton.setOnClickListener{
                val intent = Intent(this, EmptyContentActivity::class.java)
                intent.putExtra("some_data", "example")
                startActivity(intent)
            }
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        }

        try {
            markButton.setOnClickListener{
                val intent = Intent(this, EmptyContentActivity::class.java)
                intent.putExtra("some_data", "example")
                startActivity(intent)
            }
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        }

        try {
            recordButton.setOnClickListener{
                val intent = Intent(this, EmptyContentActivity::class.java)
                intent.putExtra("some_data", "example")
                startActivity(intent)
            }
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        }


        try {
            scheduleButton.setOnClickListener{
                val intent = Intent(this, EmptyContentActivity::class.java)
                intent.putExtra("some_data", "example")
                startActivity(intent)
            }
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        }


        try {
            competitionsButton.setOnClickListener{
                val intent = Intent(this, EmptyContentActivity::class.java)
                intent.putExtra("some_data", "example")
                startActivity(intent)
            }
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        }


        try {
            commandToSubjectButton.setOnClickListener{
                val intent = Intent(this, EmptyContentActivity::class.java)
                intent.putExtra("some_data", "example")
                startActivity(intent)
            }
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        }

    }
}