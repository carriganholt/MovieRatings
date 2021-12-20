package com.example.finalexam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        this.title = "Sign Up"

        val email = findViewById<EditText>(R.id.newEmail)
        val password = findViewById<EditText>(R.id.newPassword)
        val password2 = findViewById<EditText>(R.id.retypePassword)
        val display = findViewById<EditText>(R.id.display)
        val submit = findViewById<Button>(R.id.submit)
        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,64}$")

        submit.setOnClickListener {
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
                if(password.text.toString().trim().matches(passwordRegex)) {
                    if (password.text.toString().trim() == password2.text.toString().trim()) {
                        auth.createUserWithEmailAndPassword(email.text.toString().trim(), password.text.toString().trim()).addOnCompleteListener {

                            val user = FirebaseAuth.getInstance().currentUser
                            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(display.text.toString()).build()
                            user.updateProfile(profileUpdates)

                            startActivity(Intent(this, DashboardActivity::class.java).putExtra("name", display.text.toString()))
                        }
                    } else {
                        Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Invalid password!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Invalid email!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}