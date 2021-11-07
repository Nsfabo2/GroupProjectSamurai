package com.tech.tracktask.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Task::class],version = 1,exportSchema = false)

abstract class DataDatabase: RoomDatabase() {

    companion object{
        var instant: DataDatabase?=null
        fun getinstant(context: Context): DataDatabase {
            if(instant !=null)
            {
                return instant as DataDatabase
            }
            instant = Room.databaseBuilder(context, DataDatabase::class.java,"dbSamurai4").run{
                allowMainThreadQueries() }.build()
            return instant as DataDatabase
        }
    }
    abstract fun DataDao(): TaskDao
}
