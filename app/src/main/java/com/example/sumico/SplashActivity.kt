package com.example.sumico

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.sumico.R
import com.example.sumico.SignInActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Задержка для отображения splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Переход к SignInActivity
            startActivity(Intent(this, SignInActivity::class.java))
            finish() // Закрываем SplashActivity, чтобы не возвращаться к ней
        }, 2000) // Задержка в 2 секунды
    }
}