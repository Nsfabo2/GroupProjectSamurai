package com.tech.tracktask

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider

class AddTaskActivity : AppCompatActivity() {
    lateinit var saveTask:CardView
    lateinit var edTask:EditText
    lateinit var edTaskDes:EditText
    lateinit var mainViewModel: myMainViewModel
    lateinit var rvAdapter:TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        mainViewModel = ViewModelProvider(this).get(myMainViewModel::class.java)

        hideStatusBar()
        edTask = findViewById(R.id.edAddTask)
        edTaskDes=findViewById(R.id.edAddTaskDec)
        saveTask = findViewById(R.id.savetask)

        saveTask.setOnClickListener{
            var s1=edTask.text.toString()
            var s2=edTaskDes.text.toString()
            if(s1.isNotEmpty() || s2.isNotEmpty() )
            {
                mainViewModel.addTask(s1,s2)
                edTask.text.clear()
                edTaskDes.text.clear()
                Toast.makeText(applicationContext, "data successfully added", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this@AddTaskActivity, ViewTaskActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext,"one or more field empty", Toast.LENGTH_SHORT).show()
            }

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