package akira.erick.com.personaltasks.controller

import akira.erick.com.personaltasks.model.Constant.EXTRA_TASK_ARRAY
import akira.erick.com.personaltasks.model.Task
import akira.erick.com.personaltasks.model.TaskDao
import akira.erick.com.personaltasks.model.TaskFirebaseDatabase
import akira.erick.com.personaltasks.model.TaskSqlite
import akira.erick.com.personaltasks.ui.MainActivity
import android.os.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainController(private val mainActivity: MainActivity) {
    private val taskDao: TaskDao = TaskFirebaseDatabase()
    private val databaseCoroutineScope = CoroutineScope(Dispatchers. IO)

    fun insertTask(task: Task) {
        databaseCoroutineScope.launch {
            taskDao.createTask(task)
        }
    }

    fun getTask(id: Int) = taskDao.retrieveTask(id)

    fun getTasks(){
        databaseCoroutineScope.launch {
            val taskList = taskDao.retrieveTasks().filter{ !it.is_deleted }
            mainActivity.getTasksHandler.sendMessage(Message().apply {
                data.putParcelableArray(EXTRA_TASK_ARRAY, taskList.toTypedArray())
            })
        }
    }

    fun getDeletedTasks(){
        databaseCoroutineScope.launch{
            val taskList = taskDao.retrieveTasks().filter { it.is_deleted }
        }
    }

    fun modifyTask(task: Task){
        databaseCoroutineScope.launch {
            taskDao.updateTask(task)
        }
    }

    fun activateTask(task: Task){
        task.is_deleted = false;
        databaseCoroutineScope.launch{
            taskDao.updateTask(task)
        }
    }

    fun removeTask(task: Task){
        task.is_deleted = true;
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