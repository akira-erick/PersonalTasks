package akira.erick.com.personaltasks.ui

import akira.erick.com.personaltasks.R
import akira.erick.com.personaltasks.databinding.ActivityTaskBinding
import akira.erick.com.personaltasks.model.Constant.EXTRA_TASK
import akira.erick.com.personaltasks.model.Constant.EXTRA_VIEW_TASK
import akira.erick.com.personaltasks.model.Task
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskActivity : AppCompatActivity() {
    private val acb: ActivityTaskBinding by lazy {
        ActivityTaskBinding.inflate(layoutInflater)
    }

    private val calendar = Calendar.getInstance()

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

        acb.deadlineTv.setOnClickListener {
            showDatePickerDialog()
        }

        //if it is edit
        receivedTask?.let{
            supportActionBar?.subtitle = "Edit task"
            with(acb) {
                titleEt.setText(it.title)
                descriptionEt.setText(it.description)
                deadlineTv.text = it.deadline
                makeitCb.isChecked = it.makeit != 0

                //if its view task
                val viewTask = intent.getBooleanExtra(EXTRA_VIEW_TASK, false)
                if(viewTask) {
                    supportActionBar?.subtitle = "View task"
                    titleEt.isEnabled = false
                    descriptionEt.isEnabled = false
                    deadlineTv.isEnabled = false

                    makeitCb.isEnabled = false
                    if(it.makeit == 1){
                        makeitCb.text = "Done"
                    } else {
                        makeitCb.text = "To do"
                    }

                    //change button text if is view
                    cancelBt.setText(R.string.back)
                    saveBt.visibility = View.GONE
                }
            }
        }

        // setting button
        with(acb){
            saveBt.setOnClickListener {
                val value = if(makeitCb.isChecked){
                    1
                } else {
                    0
                }
                Task(
                    receivedTask?.id?:hashCode(),
                    titleEt.text.toString(),
                    descriptionEt.text.toString(),
                    deadlineTv.text.toString(),
                    value
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

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                updateDateInView()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun updateDateInView() {
        val dateFormat = "dd/MM/yyyy" // Define your desired date format
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        acb.deadlineTv.text = sdf.format(calendar.time)
    }


}