package com.tech.tracktask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tech.tracktask.data.Task
import kotlinx.android.synthetic.main.task_list_item.view.*
import android.util.Log
import com.tech.tracktask.Utility.getFormattedStopWatch

class TaskListAdapter(private val activity: ViewTaskActivity, private var taskList: List<Task>) :
RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.task_list_item, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val tasks = taskList[position]


        viewHolder.itemView.apply {
            tvTask.text = tasks.tskName
            tvTaskDesc.text = tasks.tskDescription
            tvTimer.text = tasks.tskSpent
            noteCard.setOnClickListener { activity.taskTimer(tasks) }
        }
    }


    private fun updateStopWatchView(timeInSeconds: Long) {
        val formattedTime = getFormattedStopWatch((timeInSeconds * 1000))
        Log.e("formattedTime", formattedTime)
        Log.d("LoggerMq", "Here $formattedTime")
    }


    override fun getItemCount(): Int {
        return taskList.size
    }
    fun update(task: List<Task>){
        println("UPDATING DATA")
        this.taskList = task
        notifyDataSetChanged()
    }

}
