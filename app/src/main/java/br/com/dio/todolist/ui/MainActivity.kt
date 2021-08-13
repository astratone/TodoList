package br.com.dio.todolist.ui


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import br.com.dio.todolist.App


import br.com.dio.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter

        insertListeners()
        getAllTasks()
        updateTasks()


    }

    private fun getAllTasks() {
        mainViewModel.allTasks.observe(this, { tasks ->
            binding.includeEmpty.emptyState.visibility = if (tasks.isEmpty()) View.VISIBLE
            else View.GONE
            adapter.submitList(tasks)
        })
    }

    private fun updateTasks() {
        mainViewModel.allTasks.observe(this, { tasks ->
            if (tasks.isEmpty()) {
                binding.includeEmpty.emptyState.visibility = View.VISIBLE
                binding.tvTitle.visibility = View.GONE
            } else {
                binding.includeEmpty.emptyState.visibility = View.GONE
                binding.tvTitle.visibility = View.VISIBLE
            }
            adapter.submitList(tasks)
        })
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, UPDATE_TASK)

        }

        adapter.listenerDelete = {
            mainViewModel.delete(it)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) getAllTasks()
        if (requestCode == UPDATE_TASK && requestCode == Activity.RESULT_OK) updateTasks()
    }


    companion object {
        private const val CREATE_NEW_TASK = 1000
        private const val UPDATE_TASK = 1001
    }

}


