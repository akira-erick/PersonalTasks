package akira.erick.com.personaltasks.model

import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class TaskFirebaseDatabase: TaskDao {
    private val databaseReference = Firebase.database.getReference("personalTasks")
    private val taskList = mutableListOf<Task>()

    init {
        databaseReference.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val task = snapshot.getValue<Task>()
                task?.let { newTask ->
                    if (!taskList.any { it.id == newTask.id }) {
                        taskList.add(newTask)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val task = snapshot.getValue<Task>()
                task?.let { editedTask ->
                    taskList[taskList.indexOfFirst { it.id == editedTask.id }] = editedTask
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val task = snapshot.getValue<Task>()
                task?.let{
                    taskList.remove(it)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // NSA
            }

            override fun onCancelled(error: DatabaseError) {
                // NSA
            }

        })
    }

    override fun createTask(task: Task): Long {
        TODO("Not yet implemented")
    }

    override fun retrieveTask(id: Int): Task {
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