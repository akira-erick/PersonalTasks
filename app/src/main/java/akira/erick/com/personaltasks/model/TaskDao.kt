package akira.erick.com.personaltasks.model

interface TaskDao {
    fun createTask(task: Task): Long
    fun retrieveContact(id: Int): Task
    fun retrieveTasks(): MutableList<Task>
    fun updateTask(task: Task): Int
    fun deleteTask(id: Int): Int
}