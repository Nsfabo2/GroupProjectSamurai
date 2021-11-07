package com.tech.tracktask.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM tbl_task ORDER BY tskID DESC")
    fun getAllTask(): LiveData<List<Task>>

//    @Query("SELECT SEC_TO_TIME(SUM(TIME_TO_SEC(tskSpent))) as T_Task from tbl_task")
//    fun getCounterTask(): LiveData<List<Task>>

    @Insert
    fun insert_task(t: Task)
    @Update
    fun update_task(task:Task)
//    @Delete
//    fun delete_task(task: Task)

}
