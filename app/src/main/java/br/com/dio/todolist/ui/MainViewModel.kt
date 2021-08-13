package br.com.dio.todolist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.dio.todolist.data.Task
import br.com.dio.todolist.data.TaskRepository
import kotlinx.coroutines.launch


class MainViewModel(private val taskRepository: TaskRepository) : ViewModel() {


    val allTasks: LiveData<List<Task>> = taskRepository.allTask

    fun insert(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }

    }

    private fun getNewTaskEntry(
        id: Int,
        title: String,
        description: String,
        date: String,
        time: String
    ): Task {
        return Task(id, title, description, date, time)
    }

    fun addTask(id: Int, title: String, description: String, date: String, hour: String) {
        val newTask = getNewTaskEntry(id, title, description, date, hour)
        insert(newTask)
    }

    fun updateItemTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun update(task: Task, title: String, description: String, date: String, hour: String) {
        val newTask = task.copy(title = title, description = description, date = date, hour = hour)
        updateItemTask(newTask)
    }

    fun delete(task: Task) {
        taskRepository.deleteTask(task)
    }

    fun loadTaskById(id: Int): LiveData<Task?> {
        return taskRepository.findId(id)

    }

}

class MainViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }

}