package akira.erick.com.personaltasks.controller

import akira.erick.com.personaltasks.model.Constant.EXTRA_TASK_ARRAY
import akira.erick.com.personaltasks.model.Task
import akira.erick.com.personaltasks.model.TaskDao
import akira.erick.com.personaltasks.model.TaskFirebaseDatabase
import akira.erick.com.personaltasks.ui.DeletedTaskListActivity
import android.os.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeletedTaskController(private val deletedTaskListActivity: DeletedTaskListActivity) {

    private val taskDao: TaskDao = TaskFirebaseDatabase()
    private val databaseCoroutineScope = CoroutineScope(Dispatchers. IO)

    fun getDeletedTasks(){
        databaseCoroutineScope.launch{
            val taskList = taskDao.retrieveTasks().filter { it.is_deleted }
            deletedTaskListActivity.getDeletedTaskHandler.sendMessage(Message().apply {
                data.putParcelableArray(EXTRA_TASK_ARRAY, taskList.toTypedArray())
            })
        }
    }

    fun activateTask(task: Task){
        task.is_deleted = false;
        databaseCoroutineScope.launch{
            taskDao.updateTask(task)
        }
    }

    fun deleteTask(task: Task){
        databaseCoroutineScope.launch{
            taskDao.deleteTask(task)
        }
    }

}