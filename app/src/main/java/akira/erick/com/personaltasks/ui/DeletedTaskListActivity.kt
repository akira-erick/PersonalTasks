package akira.erick.com.personaltasks.ui

import akira.erick.com.personaltasks.adapter.DeletedTaskRvAdapter
import akira.erick.com.personaltasks.controller.DeletedTaskController
import akira.erick.com.personaltasks.controller.MainController
import akira.erick.com.personaltasks.databinding.ActivityDeletedTaskListBinding
import akira.erick.com.personaltasks.model.Constant.EXTRA_TASK_ARRAY
import akira.erick.com.personaltasks.model.Task
import akira.erick.com.personaltasks.ui.MainActivity.Companion
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity

class DeletedTaskListActivity : AppCompatActivity(), OnDeletedTaskClickListener{
    private val alb: ActivityDeletedTaskListBinding by lazy {
        ActivityDeletedTaskListBinding.inflate(layoutInflater)
    }

    private val taskList: MutableList<Task> = mutableListOf()

    private val taskAdapter: DeletedTaskRvAdapter by lazy {
        DeletedTaskRvAdapter(taskList, this)
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    private val deletedTaskController: DeletedTaskController by lazy {
        DeletedTaskController(this)
    }

    companion object {
        const val GET_TASKS_MESSAGE = 1
        const val GET_TASKS_INTERVAL = 2000L
    }
    val getDeletedTaskHandler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message){
            super.handleMessage(msg)
            if (msg.what == GET_TASKS_MESSAGE) {
                deletedTaskController.getDeletedTasks()
                sendMessageDelayed(
                    obtainMessage().apply { what = MainActivity.GET_TASKS_MESSAGE },
                    MainActivity.GET_TASKS_INTERVAL
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

    override fun onTaskClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onActivateTaskMenuItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onVisualizationMenuItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}