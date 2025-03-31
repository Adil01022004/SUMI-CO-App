package com.example.sumico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.sumico.userInfoGlobal.UserInfo
import com.google.firebase.Firebase
import com.google.firebase.database.database

class GradeActivity : AppCompatActivity() {
    private lateinit var studentGradeTable: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade)

        studentGradeTable = findViewById(R.id.user_grades_table)

        val firebaseDb = Firebase.database
        val myRef = firebaseDb.getReference("userInfo")

        val userEmail = intent.getStringExtra("userAccessEmail") ?: "default_userEmail"
        val userPassword = intent.getStringExtra("userAccessPassword") ?: "default_userPassword"
        val userInfo = UserInfo(userEmail, userPassword)

        userInfo.getMarkStudent() { userGrades ->
            Log.d("FirebaseData", "Полученные оценки: $userGrades") // Логируем полученные оценки
            // Добавляем все оценки в таблицу
            userGrades.forEach { (courseName, grade) ->
                addTableRow(courseName, grade.toString())
            }
        }


    }


    private fun addTableRow(courseName: String, grade: String) {
        // Создаем новый TableRow
        val tableRow = TableRow(this)

        // Создаем TextView для названия курса
        val courseTextView = TextView(this).apply {
            text = courseName
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3f)
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        // Создаем TextView для оценки
        val gradeTextView = TextView(this).apply {
            text = grade
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        // Добавляем TextView в TableRow
        tableRow.addView(courseTextView)
        tableRow.addView(gradeTextView)

        // Добавляем TableRow в TableLayout
        studentGradeTable.addView(tableRow)


    }
}