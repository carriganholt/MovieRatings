package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        this.title = "Log In"

        if(auth.currentUser == null) {
            val logIn = findViewById<Button>(R.id.logIn)
            val signUp = findViewById<Button>(R.id.signUp)
            val email = findViewById<EditText>(R.id.email)
            val password = findViewById<EditText>(R.id.password)

            logIn.setOnClickListener {
                if(email.text.toString().trim() != "" && email.text.toString().trim() != "") {
                    auth.signInWithEmailAndPassword(email.text.toString().trim(), password.text.toString().trim()).addOnCompleteListener {
                        if(it.isSuccessful) {
                            startActivity(Intent(this, DashboardActivity::class.java))
                        } else {
                            Toast.makeText(this, "Incorrct Login Information!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else if(email.text.toString().trim() == "") {
                    Toast.makeText(this, "Enter an email!", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Enter a password!", Toast.LENGTH_SHORT).show()
                }
            }

            signUp.setOnClickListener {
                startActivity(Intent(this, SignUpActivity::class.java))
            }

        } else {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}