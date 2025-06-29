package akira.erick.com.personaltasks.model

import akira.erick.com.personaltasks.R
import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
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
        private const val IS_DELETED_COLUMN = "isDeleted"
        private const val PRIORITY_COLUMN = "priority"
        private const val MAKEIT_COLUMN = "makeit"

        const val CREATE_TASK_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS $TASK_TABLE (" +
                "$ID_COLUMN INTEGER NOT NULL PRIMARY KEY, " +
                "$TITLE_COLUMN TEXT NOT NULL, " +
                "$DESCRIPTION_COLUMN TEXT NOT NULL, " +
                "$DEADLINE_COLUMN TEXT NOT NULL," +
                "$IS_DELETED_COLUMN INTEGER NOT NULL" +
                "$PRIORITY_COLUMN TEXT NOT NULL" +
                "$MAKEIT_COLUMN INTEGER NOT NULL);"
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

    override fun createTask(task: Task) =
        taskDatabase.insert(
            TASK_TABLE,
            null,
            task.toContentValues()
        )

    override fun retrieveTask(id: Int): Task {
        val cursor = taskDatabase.query(
            true,
            TASK_TABLE,
            null,
            "$ID_COLUMN = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            cursor.toTask()
        }
        else{
            Task()
        }
    }

    override fun retrieveTasks(): MutableList<Task> {
        val taskList: MutableList<Task> = mutableListOf()
        val cursor = taskDatabase.rawQuery("SELECT * FROM $TASK_TABLE;", null)

        while (cursor.moveToNext()){
            taskList.add(cursor.toTask())
        }

        return taskList
    }

    override fun updateTask(task: Task) = taskDatabase.update(
        TASK_TABLE,
        task.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(task.id.toString())
    )

    override fun deleteTask(task: Task) = taskDatabase.delete(
        TASK_TABLE,
        "$ID_COLUMN = ?",
        arrayOf(task.id.toString())
    )

    private fun Task.toContentValues() = ContentValues().apply {
        put(ID_COLUMN, id)
        put(TITLE_COLUMN, title)
        put(DESCRIPTION_COLUMN, description)
        put(DEADLINE_COLUMN, deadline)
        put(IS_DELETED_COLUMN, is_deleted)
        put(PRIORITY_COLUMN, priority)
        put(MAKEIT_COLUMN, makeit)
    }

    private fun Cursor.toTask() = Task(
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(TITLE_COLUMN)),
        getString(getColumnIndexOrThrow(DESCRIPTION_COLUMN)),
        getString(getColumnIndexOrThrow(DEADLINE_COLUMN)),
        getInt(getColumnIndexOrThrow(IS_DELETED_COLUMN)) == 1,
        getString(getColumnIndexOrThrow(PRIORITY_COLUMN)),
        getInt(getColumnIndexOrThrow(MAKEIT_COLUMN)),
    )

}