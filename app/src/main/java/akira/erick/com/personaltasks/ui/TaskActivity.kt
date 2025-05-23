package akira.erick.com.personaltasks.ui

import akira.erick.com.personaltasks.databinding.ActivityTaskBinding
import android.os.Bundle
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
    }

}