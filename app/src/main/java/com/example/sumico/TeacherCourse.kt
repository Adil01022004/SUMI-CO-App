package com.example.sumico

data class TeacherCourse(
    val teacherName: String = "",
    val courseName: String = "",
    val schedule: Map<String, String> = emptyMap()
)