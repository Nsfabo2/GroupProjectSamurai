package com.tech.tracktask


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.tech.tracktask.data.DataDatabase
import com.tech.tracktask.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class myMainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: DataRepository
    private val tasks: LiveData<List<Task>>

    init {
        val ob= DataDatabase.getinstant(application).DataDao()
        repository = DataRepository(ob)
        tasks = repository.getTasks
    }

    fun getTask(): LiveData<List<Task>>{
        return tasks
    }


    fun addTask(x1: String,x2:String){
        var x3 ="00:00:00"

        // get current date
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())

        CoroutineScope(Dispatchers.IO).launch {
            repository.addTask(Task(0, x1,x2,x3,currentDate)) // added current date
        }
    }

//    fun editTask(ID: Int,x3:String){
//
//        CoroutineScope(Dispatchers.IO).launch {
//            repository.updateTask(ID,x3)
//        }
//    }

    fun taskTimer(updatedTask: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateTask(updatedTask)
        }
    }

//    fun deleteTask(tskID: Int){
//        CoroutineScope(Dispatchers.IO).launch {
//            repository.deleteTask(Task(tskID,"","",""))
//        }
//    }
}