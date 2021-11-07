package com.tech.tracktask.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*


@Entity(tableName = "tbl_task")
data class Task(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "tskID") val id:Int = 0,
    @ColumnInfo(name = "tskName") var tskName:String,
    @ColumnInfo(name = "tskDescription") var tskDescription:String,
    @ColumnInfo(name = "tskSpent") var tskSpent: String,
    @ColumnInfo(name = "tskCreate",defaultValue = "CURRENT_TIMESTAMP") val t: String // updated to add date
)