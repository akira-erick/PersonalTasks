package akira.erick.com.personaltasks.ui

import akira.erick.com.personaltasks.R
import akira.erick.com.personaltasks.adapter.TaskRvAdapter
import akira.erick.com.personaltasks.controller.MainController
import akira.erick.com.personaltasks.databinding.ActivityMainBinding
import akira.erick.com.personaltasks.model.Constant.EXTRA_TASK
import akira.erick.com.personaltasks.model.Constant.EXTRA_TASK_ARRAY
import akira.erick.com.personaltasks.model.Constant.EXTRA_VIEW_TASK
import akira.erick.com.personaltasks.model.Task
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), OnTaskClickListener {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data Source
    private val taskList: MutableList<Task> = mutableListOf()

    // Adapter
    private val taskAdapter: TaskRvAdapter by lazy {
        TaskRvAdapter(taskList, this)
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    private val mainController: MainController by lazy {
        MainController(this)
    }

    companion object {
        const val GET_TASKS_MESSAGE = 1
        const val GET_TASKS_INTERVAL = 2000L
    }
    val getTasksHandler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == GET_TASKS_MESSAGE) {
                mainController.getTasks()
                sendMessageDelayed(
                    obtainMessage().apply { what = GET_TASKS_MESSAGE },
                    GET_TASKS_INTERVAL
                )
            } else {
                val taskArray = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    msg.data?.getParcelableArray(EXTRA_TASK_ARRAY, Task::class.java)
                } else {
                    msg.data?.getParcelableArray(EXTRA_TASK_ARRAY)
                }
                taskList.clear()
                taskArray?.forEach { taskList.add(it as Task) }
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(amb.root)
        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.task_list)

        carl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> if (result.resultCode == RESULT_OK){
                val task = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    result.data?.getParcelableExtra(EXTRA_TASK, Task::class.java)
                } else {
                    result.data?.getParcelableExtra<Task>(EXTRA_TASK)
                }
                task?.let{ receivedTask ->
                    //new task
                    val position = taskList.indexOfFirst { it.id == receivedTask.id}
                    if (position == -1) {
                        taskList.add(receivedTask)
                        taskAdapter.notifyItemInserted(taskList.lastIndex)
                        mainController.insertTask(receivedTask)
                    //edited
                    } else {
                        taskList[position] = receivedTask
                        taskAdapter.notifyItemChanged(position)
                        mainController.modifyTask(receivedTask)
                    }
                }
            }
        }
        amb.taskRv.adapter = taskAdapter
        amb.taskRv.layoutManager = LinearLayoutManager(this)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.add_task_mi -> {
                carl.launch(Intent(this, TaskActivity::class.java))
                true
            }
            R.id.sign_out_mi -> {
                Firebase.auth.signOut()
                finish()
                true
            }
            else -> { false }
        }
    }

    override fun onTaskClick(position: Int) {
        Intent(this, TaskActivity::class.java).apply {
            putExtra(EXTRA_TASK, taskList[position])
            putExtra(EXTRA_VIEW_TASK, true)
            startActivity(this)
        }
    }

    override fun onRemoveTaskMenuItemClick(position: Int) {
        mainController.removeTask(taskList[position])
        taskList.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
        Toast.makeText(this, "Task removed!", Toast.LENGTH_SHORT).show()
    }

    override fun onEditTaskMenuItemClick(position: Int) {
        Intent(this, TaskActivity::class.java).apply {
            putExtra(EXTRA_TASK, taskList[position])
            carl.launch(this)
        }
    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser == null) finish()
    }
}