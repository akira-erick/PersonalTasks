package akira.erick.com.personaltasks.model

import android.content.Context

class TaskSqlite(context: Context): TaskDao {
    override fun createTask(task: Task): Long {
        TODO("Not yet implemented")
    }

    override fun retrieveContact(id: Int): Task {
        TODO("Not yet implemented")
    }

    override fun retrieveTasks(): MutableList<Task> {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: Task): Int {
        TODO("Not yet implemented")
    }

    override fun deleteTask(id: Int): Int {
        TODO("Not yet implemented")
    }
}