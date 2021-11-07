package com.tech.tracktask

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.cardview.widget.CardView

class HomeActivity : AppCompatActivity() {

    lateinit var addTask:CardView
    lateinit var viewTask:CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        hideStatusBar()

        viewTask = findViewById(R.id.viewTaskCardView)
        addTask = findViewById(R.id.addTaskCardView)

        viewTask.setOnClickListener{
            val intent = Intent(this@HomeActivity, ViewTaskActivity::class.java)
            startActivity(intent)
        }

        addTask.setOnClickListener{
            val intent = Intent(this@HomeActivity, AddTaskActivity::class.java)
            startActivity(intent)
        }

    }

    fun hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        } else {
            val decorView: View = window.decorView
            val uiOptions: Int = View.SYSTEM_UI_FLAG_FULLSCREEN
            decorView.setSystemUiVisibility(uiOptions)
        }
    }
}