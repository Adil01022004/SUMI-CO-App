package com.example.sumico

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.sumico.userInfoGlobal.UserInfo
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menuButton: ImageView
    private lateinit var courseButton: LinearLayout
    private lateinit var markButton: LinearLayout
    private lateinit var recordButton: LinearLayout
    private lateinit var scheduleButton: LinearLayout
    private lateinit var competitionsButton:LinearLayout
    private lateinit var commandToSubjectButton: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebaseDb = Firebase.database
        val myRef = firebaseDb.getReference("userInfo")

        val userEmail = intent.getStringExtra("userAccessEmail") ?: "default_userEmail"
        val userPassword = intent.getStringExtra("userAccessPassword") ?: "default_userPassword"
        val safeUserEmail = userEmail.substringBefore("@")

        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userInfo = UserInfo(userEmail, userPassword)



        setupUI()
    }

    private fun setupUI() {
        val userEmail = intent.getStringExtra("userAccessEmail") ?: "default_userEmail"


        drawerLayout = findViewById(R.id.drawer_layout)
        menuButton = findViewById(R.id.menu)
        courseButton = findViewById(R.id.course_button)
        markButton = findViewById(R.id.mark_button)
        recordButton = findViewById(R.id.record_button)
        scheduleButton = findViewById(R.id.schedule_button)
        competitionsButton = findViewById(R.id.competitions_button)
        commandToSubjectButton = findViewById(R.id.command_to_subject_button)

        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        courseButton.setOnClickListener {
            val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)
        }

        markButton.setOnClickListener {
            val intent = Intent(this, GradeActivity::class.java)

            Toast.makeText(this, "Вход выполнен!", Toast.LENGTH_SHORT).show()
            intent.putExtra("userAccessEmail",userEmail )


            startActivity(intent)
        }

        recordButton.setOnClickListener {
            val intent = Intent(this, EmptyContentActivity::class.java)
            startActivity(intent)
        }

        scheduleButton.setOnClickListener {
            val intent = Intent(this, EmptyContentActivity::class.java)
            startActivity(intent)
        }

        competitionsButton.setOnClickListener {
            val intent = Intent(this, EmptyContentActivity::class.java)
            startActivity(intent)
        }

        commandToSubjectButton.setOnClickListener {
            val intent = Intent(this, EmptyContentActivity::class.java)
            startActivity(intent)
        }




    }


}
