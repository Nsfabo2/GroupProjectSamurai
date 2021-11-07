package com.tech.tracktask

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tech.tracktask.data.Task
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class ViewTaskActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var detailButton: CardView
    lateinit var mainViewModel: myMainViewModel

    private lateinit var adapter: TaskListAdapter
    private lateinit var taskList: List<Task>

    // timer
    private var currentTaskRunning: Int = 0
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)
        taskList =listOf()
        mainViewModel = ViewModelProvider(this).get(myMainViewModel::class.java)
        mainViewModel.getTask().observe(this, {
                tasks ->  taskList = tasks
                            adapter.update(taskList)
        })
        hideStatusBar()
        recyclerView = findViewById(R.id.recyclerView)
        detailButton = findViewById(R.id.detail)


        initializeRecyclerView()

        detailButton.setOnClickListener{
            val intent = Intent(this@ViewTaskActivity, DetailTasks::class.java)
            startActivity(intent)
        }

        // timer
        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

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

        recyclerView.layoutManager = GridLayoutManager(this@ViewTaskActivity, 2)
        adapter = TaskListAdapter(this, taskList)

        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        recyclerView.adapter = adapter

    }

    // timer

    fun taskTimer(tasks: Task) {
        time = convertTimeToLong(tasks.tskSpent)
        Log.d("Logger", "ret time is $time")
        if (currentTaskRunning == 0) { // init timer
            currentTaskRunning = tasks.id
            startStopTimer()
        } else if (currentTaskRunning == tasks.id){ // same task start/stop
            startStopTimer()
        } else { // new task
            resetTimer()
            currentTaskRunning = tasks.id
            startStopTimer()
        }

    }

    private fun resetTimer() {
        stopTimer()
    }

    private fun startStopTimer() {
        if(timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
        timerStarted = true

    }

    private fun stopTimer() {
        stopService(serviceIntent)
        timerStarted = false
        taskList.forEach {
            if (it.id == currentTaskRunning) {
                mainViewModel.taskTimer(it)
            }
        }
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            //binding.timeTV.text = getTimeStringFromDouble(time)
            taskList.forEach {
                if (it.id == currentTaskRunning) {
                    it.tskSpent = getTimeStringFromDouble(time)
                }
            }
//            taskList[taskIndex].tskSpent = "${getTimeStringFromDouble(time)}"
            adapter.update(taskList)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)
    fun convertTimeToLong(newTimer: String): Double {
        val hrs = newTimer.substring(0, newTimer.indexOf(":"))
        val min = newTimer.substring(newTimer.indexOf(":") + 1, newTimer.lastIndexOf(":"))
        val sec = newTimer.substring(newTimer.lastIndexOf(":") + 1)
        val returnValue: Double =( (hrs.toInt() * 3600) + (min.toInt() * 60) + (sec.toInt() * 1) ).toDouble()
        return returnValue
    }
}