package com.example.sumico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterToSubjectActivity : AppCompatActivity() {
    private lateinit var tableLayout: TableLayout
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_to_subject)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        tableLayout = findViewById(R.id.teachersAndCoursesTable)
        database = FirebaseDatabase.getInstance().getReference("teacherInfo")

        loadTeachersAndCourses()
    }


    private fun loadTeachersAndCourses() {
        val tableLayout = findViewById<TableLayout>(R.id.teachersAndCoursesTable)
        val database = FirebaseDatabase.getInstance().getReference("teacherInfo")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (teacherSnap in snapshot.children) {
                    val teacherName = teacherSnap.child("fullName").getValue(String::class.java) ?: "Неизвестно"
                    val coursesSnap = teacherSnap.child("courses")

                    for (courseSnap in coursesSnap.children) {
                        val courseName = courseSnap.key ?: "Курс"
                        val scheduleSnap = courseSnap.child("schedule")
                        val scheduleMap = mutableMapOf<String, String>()
                        for (day in scheduleSnap.children) {
                            scheduleMap[day.key ?: ""] = day.getValue(String::class.java) ?: ""
                        }

                        val course = TeacherCourse(teacherName, courseName, scheduleMap)
                        addCourseRow(tableLayout, course)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Ошибка: ${error.message}")
            }
        })
    }



    private fun addCourseRow(table: TableLayout, course: TeacherCourse) {
        val row = TableRow(this)

        val courseText = TextView(this)
        courseText.text = course.courseName
        courseText.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        courseText.setPadding(10, 10, 10, 10)

        val teacherText = TextView(this)
        teacherText.text = course.teacherName
        teacherText.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        teacherText.setPadding(10, 10, 10, 10)

        val selectBtn = Button(this)
        selectBtn.text = "Выбрать"
        selectBtn.setOnClickListener {
            showScheduleDialog(course.schedule)
        }

        row.addView(courseText)
        row.addView(teacherText)
        row.addView(selectBtn)
        table.addView(row)
    }


    private fun showScheduleDialog(schedule: Map<String, String>) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_schedule_picker, null)

        val daySpinner = dialogView.findViewById<Spinner>(R.id.daySpinner)
        val timeSpinner = dialogView.findViewById<Spinner>(R.id.timeSpinner)

        val days = schedule.keys.toList()
        val times = schedule.values.toList()

        daySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, days)
        timeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, times)

        AlertDialog.Builder(this)
            .setTitle("Выбрать дату")
            .setView(dialogView)
            .setPositiveButton("ОК") { _, _ ->
                val selectedDay = daySpinner.selectedItem.toString()
                val selectedTime = timeSpinner.selectedItem.toString()
                Toast.makeText(this, "Вы выбрали $selectedDay, $selectedTime", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }



}