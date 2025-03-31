package com.example.sumico.userInfoGlobal

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class UserInfo(
    val userEmail: String,
    val userPassword: String,
    val userFullName: String? = null,
    val userPoints: Int? = 0
) {
    private val firebaseDb = Firebase.database

    // Получение имени пользователя с Firebase (через callback)
    fun getUserFullName(userEmail: String, callback: (String?) -> Unit) {
        val safeUserEmail = userEmail.substringBefore("@")

        val myRef = firebaseDb.getReference("userInfo").child(safeUserEmail)

        myRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val userName = snapshot.child("userFullName").getValue(String::class.java)
                Log.d("FirebaseData", "Имя пользователя: $userName")
                callback(userName) // Передаем имя в callback
            } else {
                Log.d("FirebaseData", "Данные не найдены!")
                callback(null) // Если данных нет, передаем null
            }
        }.addOnFailureListener {
            Log.e("FirebaseData", "Ошибка получения данных", it)
            callback(null) // В случае ошибки передаем null
        }
    }




    fun getMarkStudent( callback: (MutableMap<String, Int>) -> Unit) {
        val safeUserEmail = userEmail.substringBefore("@")
        val myRef = firebaseDb.getReference("userInfo").child(safeUserEmail).child("mark")

        val marksMap = mutableMapOf<String, Int>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val marksMap = mutableMapOf<String, Int>()

                if (snapshot.exists()) {
                    for (child in snapshot.children) {
                        val subject = child.key ?: continue
                        val mark = child.getValue(Int::class.java) ?: continue
                        marksMap[subject] = mark
                        Log.d("FirebaseData", "Предмет: $subject, Оценка: $mark")
                    }
                } else {
                    Log.d("FirebaseData", "Оценок нет!")
                    Log.d("Name", "$safeUserEmail")

                }

                callback(marksMap)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseData", "Ошибка получения данных", error.toException())
                callback(mutableMapOf())
            }
        })

    }
}