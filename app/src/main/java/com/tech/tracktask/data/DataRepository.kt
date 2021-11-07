package com.tech.tracktask.data


import androidx.lifecycle.LiveData

class DataRepository(private val tskDao: TaskDao) {

    val getTasks: LiveData<List<Task>> = tskDao.getAllTask()

    suspend fun addTask(tsk: Task){
        tskDao.insert_task(tsk)
    }

    suspend fun updateTask(task:Task){
        tskDao.update_task(task)
    }


//    suspend fun deleteTask(tsk: Task){
//        tskDao.delete_task(tsk)
//    }

}