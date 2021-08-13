package br.com.dio.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val hour: String,
    val date: String,
    val description: String

)