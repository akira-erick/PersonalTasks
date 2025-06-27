package akira.erick.com.personaltasks.ui

import akira.erick.com.personaltasks.databinding.ActivityDeletedTaskListBinding
import akira.erick.com.personaltasks.model.Task
import androidx.appcompat.app.AppCompatActivity

class DeletedTaskListActivity : AppCompatActivity(){
    private val alb: ActivityDeletedTaskListBinding by lazy {
        ActivityDeletedTaskListBinding.inflate(layoutInflater)
    }

    private val taskList: MutableList<Task> = mutableListOf()
}