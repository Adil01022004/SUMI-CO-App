package com.example.sumico

import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class CommandToCompetitionActivity : AppCompatActivity() {

    private lateinit var commandNameView: TextView
    private lateinit var leaderView: TextView
    private lateinit var table: TableLayout
    private val dbRef = FirebaseDatabase.getInstance().getReference("commandToCompetitions")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_to_competition)

        commandNameView = findViewById(R.id.commandName)
        leaderView = findViewById(R.id.commandLeader)
        table = findViewById(R.id.commandParticipantTable)

        val commandID = intent.getStringExtra("commandID") ?: "cm_147258" // запасной вариант

        loadCommandInfo(commandID)
    }

    private fun loadCommandInfo(commandID: String) {
        dbRef.child(commandID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val commandName = snapshot.child("commandName").getValue(String::class.java)
                commandNameView.text = "Название команды $commandName" ?: "Без названия"

                val personsNode = snapshot.child("commandPersons")
                val participants = personsNode.children.mapNotNull { it.getValue(String::class.java) }

                if (participants.isEmpty()) return

                loadUsers(participants)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Ошибка загрузки команды: ${error.message}")
            }
        })
    }

    private fun loadUsers(userKeys: List<String>) {
        val userInfoRef = FirebaseDatabase.getInstance().getReference("userInfo")
        var loadedCount = 0
        val names = MutableList(userKeys.size) { "" }

        for ((index, key) in userKeys.withIndex()) {
            userInfoRef.child(key).child("userFullName")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val name = snapshot.getValue(String::class.java) ?: "Неизвестный"
                        names[index] = name
                        loadedCount++

                        if (loadedCount == userKeys.size) {
                            showUsers(names)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        names[index] = "Ошибка"
                        loadedCount++
                        if (loadedCount == userKeys.size) {
                            showUsers(names)
                        }
                    }
                })
        }
    }

    private fun showUsers(userNames: List<String>) {
        for ((index, name) in userNames.withIndex()) {
            val row = TableRow(this)

            val number = TextView(this).apply {
                text = (index + 1).toString()
                setPadding(20, 20, 20, 20)
                setBackgroundResource(R.drawable.cell_border)
            }

            val fullName = TextView(this).apply {
                text = name
                setPadding(20, 20, 20, 20)
                setBackgroundResource(R.drawable.cell_border)

            }

            row.addView(number)
            row.addView(fullName)

            table.addView(row)
        }

        leaderView.text = "Лидер команды: ${userNames.firstOrNull() ?: "Неизвестно"}"
    }

}
