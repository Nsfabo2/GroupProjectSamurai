package com.tech.tracktask

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tech.tracktask.data.Task
import kotlin.math.roundToInt

class DetailTasks : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var detailButton: CardView
    lateinit var mainViewModel: myMainViewModel

    private lateinit var adapter: DetailListAdapter
    private lateinit var taskList: List<Task>

    var totalTime: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tasks)
        taskList =listOf()
        mainViewModel = ViewModelProvider(this).get(myMainViewModel::class.java)
        mainViewModel.getTask().observe(this, {
                tasks -> taskList = tasks
                adapter.update(taskList)
                handle_totalTime()
        })

        hideStatusBar()


        recyclerView = findViewById(R.id.recyclerViewD)

        initializeRecyclerView()


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

    private fun initializeRecyclerView() {

        recyclerView.layoutManager = GridLayoutManager(this@DetailTasks, 2)
        adapter = DetailListAdapter(taskList)

        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        recyclerView.adapter = adapter

    }

    private fun handle_totalTime() {
        taskList.forEach {
            totalTime += convertTimeToLong(it.tskSpent)
        }
        val tv_totalTime = findViewById<TextView>(R.id.tv_totalTime)
        tv_totalTime.setText("Total Time: ${getTimeStringFromDouble(totalTime)}")
    }

    fun convertTimeToLong(newTimer: String): Double {
        val hrs = newTimer.substring(0, newTimer.indexOf(":"))
        val min = newTimer.substring(newTimer.indexOf(":") + 1, newTimer.lastIndexOf(":"))
        val sec = newTimer.substring(newTimer.lastIndexOf(":") + 1)
        val returnValue: Double =( (hrs.toInt() * 3600) + (min.toInt() * 60) + (sec.toInt() * 1) ).toDouble()
        return returnValue
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)
}
