package br.com.dio.todolist.ui


import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dio.todolist.App
import br.com.dio.todolist.databinding.ActivityAddTaskBinding
import br.com.dio.todolist.extensions.format
import br.com.dio.todolist.extensions.text
import br.com.dio.todolist.data.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {


    private val binding by lazy { ActivityAddTaskBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    private lateinit var task: Task
    private var createTask = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fun editingTask(task: Task) {
            binding.apply {
                binding.tilTitle.text = task.title
                binding.tilDate.text = task.date
                binding.tilHour.text = task.hour
                binding.tilDescription.text = task.description
            }
        }

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            mainViewModel.loadTaskById(taskId).observe(this, { selectedItem ->
                task = selectedItem!!
                editingTask(task)


            })

        } else {
            createTask = true
        }


        insertListeners()
        iconNavigate()
    }

    private fun iconNavigate() {
        binding.toolbar.setOnClickListener {
            finish()
        }
    }


    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {

            if (createTask) {
                mainViewModel.addTask(
                    title = binding.tilTitle.text,
                    date = binding.tilDate.text,
                    description = binding.tilHour.text,
                    hour = binding.tilDescription.text,
                    id = intent.getIntExtra(TASK_ID, 0)


                    )

               // mainViewModel.insert(task)
                setResult(Activity.RESULT_OK)

            } else {
                mainViewModel.update(task,
                    binding.tilTitle.text,
                    binding.tilDescription.text,
                    binding.tilDate.text,
                    binding.tilHour.text,

                )

               // mainViewModel.updateItemTask(task)
                setResult(Activity.RESULT_OK)
            }

            finish()
        }
    }


    companion object {
        const val TASK_ID = "task_id"

    }

}



