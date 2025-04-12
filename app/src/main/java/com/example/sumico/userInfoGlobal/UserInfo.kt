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
    val userPoints: Int? = 0,
    val commandID: String? = null
) {
    private val firebaseDb = Firebase.database

    // Получить userFullName по userId (usr_...)
    fun getUserFullNameById(userId: String, callback: (String?) -> Unit) {
        val myRef = firebaseDb.getReference("userInfo")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    val idValue = child.child("id").getValue(String::class.java)
                    if (idValue == userId) {
                        val name = child.child("userFullName").getValue(String::class.java)
                        callback(name)
                        return
                    }
                }
                callback(null) // Не найден
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseData", "Ошибка поиска userFullName по ID", error.toException())
                callback(null)
            }
        })
    }





    fun setUserInfo(userEmail: String, userPassword: String){
        val safeUserEmail = userEmail.substringBefore("@")
        val myRef = firebaseDb.getReference("userInfo")

        val updates = mapOf(
            "userEmail" to userEmail,
            "userPassword" to userPassword
        )

        myRef.child(safeUserEmail).updateChildren(updates)
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