package akira.erick.com.personaltasks.model

import akira.erick.com.personaltasks.R
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.sql.SQLException

class TaskSqlite(context: Context): TaskDao {
    companion object{
        private const val TASK_DATABASE_FILE = "taskList"
        private const val TASK_TABLE = "task"
        private const val ID_COLUMN = "id"
        private const val TITLE_COLUMN = "title"
        private const val DESCRIPTION_COLUMN = "description"
        private const val DEADLINE_COLUMN = "deadline"

        const val CREATE_TASK_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS $TASK_TABLE (" +
                "$ID_COLUMN INTEGER NOT NULL PRIMARY KEY, " +
                "$TITLE_COLUMN TEXT NOT NULL, " +
                "$DESCRIPTION_COLUMN TEXT NOT NULL, " +
                "$DEADLINE_COLUMN TEXT NOT NULL );"
    }

    private val taskDatabase: SQLiteDatabase = context.openOrCreateDatabase(
        TASK_DATABASE_FILE,
        MODE_PRIVATE,
        null
    )

    init{
        try{
            taskDatabase.execSQL(CREATE_TASK_TABLE_STATEMENT)
        }
        catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.message.toString())
        }
    }

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