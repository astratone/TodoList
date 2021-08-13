package br.com.dio.todolist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TaskRepository(private val dao: TaskDao) {

    val allTask: LiveData<List<Task>> = dao.getAll().asLiveData()

    fun insertTask(task: Task) = runBlocking {
        launch(Dispatchers.IO) {
            dao.insert(task)
        }
    }

    fun updateTask(task: Task) = runBlocking {
        launch(Dispatchers.IO) {
            dao.update(task)
        }

    }

    fun deleteTask(task: Task) = runBlocking {
        launch(Dispatchers.IO) {
            dao.delete(task)
        }

    }

    fun findId(taskId: Int): LiveData<Task?> {
        return dao.loadTaskById(taskId).asLiveData()
    }

}