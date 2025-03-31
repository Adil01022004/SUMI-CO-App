package com.example.sumico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import java.security.interfaces.EdECKey

class SignInActivity : AppCompatActivity() {


    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var loginButton: TextView
    private lateinit var menuButton: ImageView
    private lateinit var drawerLayout: DrawerLayout

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
                            val intent = Intent(this, MainActivity::class.java)
                            val userEmailAccess = email.toString()
                            val userPasswordAccess = password.toString()
                            Toast.makeText(this, "Вход выполнен!", Toast.LENGTH_SHORT).show()
                            intent.putExtra("userAccessEmail", userEmailAccess)
                            intent.putExtra("userAccessPassword", userPasswordAccess)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Ошибка при входе: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }



        }












    }
}