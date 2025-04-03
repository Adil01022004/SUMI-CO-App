package com.example.sumico

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.sumico.userInfoGlobal.UserInfo

class EmptyContentActivity : AppCompatActivity() {

    private var menuButton: ImageView? = null
    private var drawerLayout: DrawerLayout? = null
    private var menuUserName: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_content)

        val userEmail = intent.getStringExtra("userAccessEmail") ?: "default_userEmail"


        // Проверяем наличие drawer_layout в activity_empty_content.xml
        drawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout == null) {
            Log.e("EmptyContentActivity", "Ошибка: drawer_layout не найден в activity_empty_content.xml")
        }

        // Проверяем наличие menuButton в activity_empty_content.xml
        menuButton = findViewById(R.id.menu)
        if (menuButton == null) {
            Log.e("EmptyContentActivity", "Ошибка: menuButton (R.id.menu) не найден в activity_empty_content.xml")
        } else {
            menuButton?.setOnClickListener {
                drawerLayout?.openDrawer(GravityCompat.START)
            }
        }

        menuUserName = findViewById(R.id.username)?: null

        val userInfo = UserInfo(userEmail)
        userInfo.getUserFullName { userFullName ->
            menuUserName?.text = userFullName
            Log.d("Username", "Username: $menuUserName")
        }
    }
}
