package akira.erick.com.personaltasks.ui

import akira.erick.com.personaltasks.R
import akira.erick.com.personaltasks.databinding.ActivityTaskBinding
import akira.erick.com.personaltasks.model.Constant.EXTRA_TASK
import akira.erick.com.personaltasks.model.Constant.EXTRA_VIEW_TASK
import akira.erick.com.personaltasks.model.Task
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class TaskActivity : AppCompatActivity() {
    private val acb: ActivityTaskBinding by lazy {
        ActivityTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstaceState: Bundle?){
        super.onCreate(savedInstaceState)
        setContentView(acb.root)

        setSupportActionBar(acb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "New task"

        val receivedTask = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TASK, Task::class.java)
        } else {
            intent.getParcelableExtra<Task>(EXTRA_TASK)
        }

        //if it is edit
        receivedTask?.let{
            supportActionBar?.subtitle = "Edit task"
            with(acb) {
                titleEt.setText(it.title)
                descriptionEt.setText(it.description)
                deadlineEt.setText(it.deadline)

                //if its view task
                val viewTask = intent.getBooleanExtra(EXTRA_VIEW_TASK, false)
                if(viewTask) {
                    supportActionBar?.subtitle = "View task"
                    titleEt.isEnabled = false
                    descriptionEt.isEnabled = false
                    deadlineEt.isEnabled = false

                    //change button text if is view
                    cancelBt.setText(R.string.back)
                    saveBt.visibility = View.GONE
                }
            }
        }

        // setting button
        with(acb){
            saveBt.setOnClickListener {
                Task(
                    receivedTask?.id?:hashCode(),
                    titleEt.text.toString(),
                    descriptionEt.text.toString(),
                    deadlineEt.text.toString()
                ).let { task ->
                    Intent().apply {
                        putExtra(EXTRA_TASK, task)
                        setResult(RESULT_OK, this)
                    }
                }
                finish()
            }
            cancelBt.setOnClickListener {
                finish()
            }
        }
    }

}