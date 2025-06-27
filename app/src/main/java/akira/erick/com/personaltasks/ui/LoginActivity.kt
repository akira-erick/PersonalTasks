package akira.erick.com.personaltasks.ui

import akira.erick.com.personaltasks.R
import akira.erick.com.personaltasks.databinding.ActivityLoginBinding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginActivity : AppCompatActivity() {
    private val alb: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val signInCoroutine = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(alb.root)

        setSupportActionBar(alb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.login)

        alb.signUpBt.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        
    }

}