package com.example.sumico

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.sumico.userInfoGlobal.UserInfo
import com.google.firebase.Firebase
import com.google.firebase.database.database

class GradeActivity : AppCompatActivity() {
    private lateinit var studentGradeTable: TableLayout
    private var menuButton: ImageView? = null
    private var drawerLayout: DrawerLayout? = null
    //private lateinit var menuUserName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade)

        // Инициализация UI элементов
        //menuUserName = findViewById(R.id.username) ?: return
        studentGradeTable = findViewById(R.id.user_grades_table)

        drawerLayout = findViewById(R.id.drawer_layout)
        menuButton = findViewById(R.id.menu)

        // Устанавливаем обработчик нажатия на кнопку меню
        menuButton?.setOnClickListener {
            drawerLayout?.openDrawer(GravityCompat.START)
        }

        val firebaseDb = Firebase.database
        val myRef = firebaseDb.getReference("userInfo")

        // Получение email пользователя из intent
        val userEmail = intent.getStringExtra("userAccessEmail") ?: "default_userEmail"
        val userPassword = intent.getStringExtra("userAccessPassword") ?: "default_userPassword"
        val safeUserEmail = if (userEmail.contains("@")) userEmail.substringBefore("@") else "unknown_user"

        // Логируем ошибку, если email некорректен
        if (safeUserEmail == "unknown_user") {
            Log.e("FirebaseData", "Ошибка: некорректный email пользователя")
            return
        }

        val userInfo = UserInfo(userEmail, userPassword)

        // Получаем оценки студента из Firebase
        userInfo.getMarkStudent() { userGrades ->
            Log.d("FirebaseData", "Полученные оценки: $userGrades")
            userGrades.forEach { (courseName, grade) ->
                addTableRow(courseName, grade.toString())
            }
        }

        // Загружаем имя пользователя
//        userInfo.getUserFullName() { userFullName ->
//            menuUserName.text = userFullName ?: "Имя не найдено"
//        }
    }

    // Добавление строки в таблицу оценок
    private fun addTableRow(courseName: String, grade: String) {
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
