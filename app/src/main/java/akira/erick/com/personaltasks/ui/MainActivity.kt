package akira.erick.com.personaltasks.ui

import akira.erick.com.personaltasks.R
import akira.erick.com.personaltasks.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), OnTaskClickListener {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(amb.root)
        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.task_list)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onTaskClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onRemoveTaskMenuItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onEditTaskMenuItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}