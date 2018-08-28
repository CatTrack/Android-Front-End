package com.thinkengineer.utc.cattrack

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var badEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        badEmail = emailfeild.text
        email = badEmail.trim()
        password = passwordfield.text

        registerButton.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener{
            auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(emailfeild.text.toString(), passwordfeild.text.toString()).
                    addOnCompleteListener { task: Task<AuthResult> ->
                        val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intentToMain)
                    }

        }
    }
}
