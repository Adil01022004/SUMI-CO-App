package com.example.sumico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.security.interfaces.EdECKey

class SignInActivity : AppCompatActivity() {


    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var loginButton: TextView

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        userEmail = findViewById(R.id.user_email)
        userPassword = findViewById(R.id.user_password)
        loginButton = findViewById(R.id.login_button)


        loginButton.setOnClickListener {
            val email = userEmail.text.toString()
            val password = userPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userEmailAccess = email.toString()
                            val userPasswordAccess = password.toString()
                            Toast.makeText(this, "Вход выполнен!", Toast.LENGTH_SHORT).show()
                            intent.putExtra("userAccessEmail", userEmailAccess)
                            intent.putExtra("userAccessPassword", userPasswordAccess)
                            // Переход к основному экрану приложения
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Ошибка при входе: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }



        }









    }
}