package akira.erick.com.personaltasks.ui

import akira.erick.com.personaltasks.databinding.ActivityDeletedTaskBinding
import akira.erick.com.personaltasks.model.Constant.EXTRA_TASK
import akira.erick.com.personaltasks.model.Task
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DeletedTaskActivity : AppCompatActivity() {
    private val dtb : ActivityDeletedTaskBinding by lazy {
        ActivityDeletedTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(dtb.root)

        setSupportActionBar(dtb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Deleted Task"

        val receivedTask = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TASK, Task::class.java)
        } else {
            intent.getParcelableExtra<Task>(EXTRA_TASK)
        }

        receivedTask.let{
            with(dtb){
                if (it != null) {
                    titleTv.text = it.title
                    descriptionTv.text = it.description
                    deadlineTv.text = it.deadline
                }
                backBt.setOnClickListener {
                    finish()
                }

            }
        }

    }
}