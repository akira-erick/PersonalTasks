package akira.erick.com.personaltasks.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeletedTaskDao {
    @Insert
    fun createTask(task: Task): Long
    @Query("SELECT * FROM Task WHERE id = :id")
    fun retrieveTask(id: Int): Task
    @Query("SELECT * FROM Task")
    fun retrieveTasks(): MutableList<Task>
    @Delete
    fun deleteTask(task: Task): Int
}