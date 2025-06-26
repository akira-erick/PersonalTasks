package akira.erick.com.personaltasks.controller

import akira.erick.com.personaltasks.model.Task
import akira.erick.com.personaltasks.model.TaskDao
import akira.erick.com.personaltasks.model.TaskSqlite
import akira.erick.com.personaltasks.ui.MainActivity

class MainController(mainActivity: MainActivity) {
    private val taskDao: TaskDao = TaskSqlite(mainActivity)

    fun insertTask(task: Task) = taskDao.createTask(task)
    fun getTask(id: Int) = taskDao.retrieveTask(id)
    fun getTasks() = taskDao.retrieveTasks()
    fun modifyTask(task: Task) = taskDao.updateTask(task)
    fun removeTask(task: Task) = taskDao.deleteTask(task)
}