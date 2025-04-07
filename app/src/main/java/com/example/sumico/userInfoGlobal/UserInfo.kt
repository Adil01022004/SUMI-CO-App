package com.example.sumico.userInfoGlobal

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class UserInfo(
    val userEmail: String,
    val userPassword: String? = null,
    val userFullName: String? = null,
    val userPoints: Int? = 0
) {
    private val firebaseDb = Firebase.database



    fun setUserInfo(userEmail: String, userPassword: String){
        val safeUserEmail = userEmail.substringBefore("@")
        val myRef = firebaseDb.getReference("userInfo")
        myRef.child(safeUserEmail).setValue(UserInfo(userEmail, userPassword))
    }

    // Получение имени пользователя с Firebase (через callback)
    fun getUserFullName(callback: (String?) -> Unit) {
        val safeUserEmail = userEmail.substringBefore("@")
        val myRef = firebaseDb.getReference("userInfo").child(safeUserEmail).child("userFullName")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userName = snapshot.getValue(String::class.java)
                    Log.d("FirebaseData", "Имя пользователя: $userName")
                    callback(userName)
                } else {
                    Log.d("FirebaseData", "Имя пользователя не найдено!")
                    callback(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseData", "Ошибка получения данных", error.toException())
                callback(null)
            }
        })
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