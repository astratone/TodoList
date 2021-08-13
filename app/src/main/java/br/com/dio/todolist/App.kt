package br.com.dio.todolist

import android.app.Application
import br.com.dio.todolist.data.AppDatabase
import br.com.dio.todolist.data.TaskRepository

class App : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }
}