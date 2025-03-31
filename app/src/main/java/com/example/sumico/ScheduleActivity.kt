package com.example.sumico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class ScheduleActivity : AppCompatActivity() {

    private lateinit var menuButton: ImageView
    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)


        drawerLayout = findViewById(R.id.drawer_layout)
        menuButton = findViewById(R.id.menu)


        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

    }
}