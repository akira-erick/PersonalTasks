package akira.erick.com.personaltasks.ui

import akira.erick.com.personaltasks.R
import akira.erick.com.personaltasks.databinding.ActivityLoginBinding
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        alb.signInBt.setOnClickListener {
            signInCoroutine.launch {
                Firebase.auth.signInWithEmailAndPassword(
                    alb.emailLoginEt.text.toString(),
                    alb.passwordLoginEt.text.toString()
                ).addOnFailureListener {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login filed. Cause ${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnSuccessListener {
                    openMainActivity()
                }
            }
        }

        alb.resetPasswordBt.setOnClickListener {
            signInCoroutine.launch {
                val email = alb.emailLoginEt.text.toString()
                if (email.isNotEmpty()) {
                    Firebase.auth.sendPasswordResetEmail(email)
                }
            }
        }

        alb.googleSignInBt.setOnClickListener{
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build()

            var request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            var credentialManager = CredentialManager.create(this)
            signInCoroutine.launch {
                try {
                    val result = credentialManager.getCredential(this@LoginActivity, request)
                    handleSignIn(result.credential)
                } catch (e: GetCredentialException){
                    runOnUiThread {
                        Toast.makeText(
                            this@LoginActivity,
                            "Authentication failed. Cause: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    private fun handleSignIn(credential: Credential){
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val credential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
            Firebase.auth.signInWithCredential(credential).addOnFailureListener {
                Toast.makeText(
                    this@LoginActivity,
                    "Login failed. Cause ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnSuccessListener {
                openMainActivity()
            }
        } else {
            Toast.makeText(
                this,
                "Credential is not Google ID!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            openMainActivity()
        }
    }

    private fun openMainActivity() {
        startActivity(
            Intent(this@LoginActivity, MainActivity::class.java)
        )
        finish()
    }
}