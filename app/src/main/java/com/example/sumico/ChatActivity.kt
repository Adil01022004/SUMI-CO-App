package com.example.sumico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<MessageModel>()
    private lateinit var currentUserId: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var sendButton: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        currentUserId = getUserCurrentId()

        database = FirebaseDatabase.getInstance().reference.child("Chat").child("messages")
        recyclerView = findViewById(R.id.recyclerView)
        editText = findViewById(R.id.userWriteMessage)
        sendButton = findViewById(R.id.sendMessage)

        adapter = MessageAdapter(messages, currentUserId)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadMessages()
        sendButton.setOnClickListener { sendMessage() }
    }

    private fun sendMessage() {
        currentUserId = getUserCurrentId()
        val plainText = editText.text.toString().trim()
        if (plainText.isNotEmpty()) {
            val encrypted = HandshakeEncryption.encrypt(plainText) // Используем рукопожатие
            val newMessage = mapOf(
                "text" to encrypted,
                "senderId" to currentUserId,
                "timestamp" to ServerValue.TIMESTAMP
            )
            database.push().setValue(newMessage)
            editText.text.clear()
        }
    }

    private fun loadMessages() {
        currentUserId = getUserCurrentId()
        database
            .orderByChild("timestamp")
            .limitToLast(50)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messages.clear()
                    val tempList = mutableListOf<MessageModel>()
                    val children = snapshot.children.toList()
                    var processedCount = 0

                    for (snap in children) {
                        val text = snap.child("text").value as? String ?: continue
                        val decrypted = HandshakeEncryption.decrypt(text)
                        val senderId = snap.child("senderId").value as? String ?: ""
                        val timestamp = snap.child("timestamp").value as? Long ?: 0L

                        // Получение имени пользователя после расшифровки
                        val fullNameRef = FirebaseDatabase.getInstance()
                            .getReference("userInfo")
                            .child(senderId)
                            .child("userFullName")

                        fullNameRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(nameSnapshot: DataSnapshot) {
                                val name = nameSnapshot.value as? String ?: "Unknown"
                                tempList.add(MessageModel(decrypted, senderId, name, timestamp))
                                processedCount++

                                // После обработки всех сообщений обновляем список
                                if (processedCount == children.size) {
                                    messages.clear()
                                    messages.addAll(tempList.sortedBy { it.timestamp })
                                    adapter.notifyDataSetChanged()
                                    recyclerView.scrollToPosition(messages.size - 1)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                processedCount++
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun getUserCurrentId(): String{
        val userEmail = intent.getStringExtra("userAccessEmail") ?: "default_userEmail"
        val safeUserEmail = userEmail.substringBefore("@")
        return safeUserEmail
    }
}
