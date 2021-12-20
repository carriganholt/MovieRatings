package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        auth = FirebaseAuth.getInstance()

        this.title = "Movie Rater"

        val message = findViewById<TextView>(R.id.message)
        val findMovie = findViewById<Button>(R.id.findMovie)
        val viewAll = findViewById<Button>(R.id.viewAll)
        val addMovie = findViewById<Button>(R.id.addMovie)
        val logOut = findViewById<Button>(R.id.logOut)

        if(intent.hasExtra("name")) {
            message.text = "Welcome,\n" + intent.getStringExtra("name")
        } else {
            message.text = "Welcome,\n" + auth.currentUser.displayName
        }

        findMovie.setOnClickListener {
            startActivity(Intent(this, FindMovieActivity::class.java))
        }

        viewAll.setOnClickListener {
            startActivity(Intent(this, ViewAllActivity::class.java))
        }

        addMovie.setOnClickListener {
            startActivity(Intent(this, AddMovieActivity::class.java))
        }

        logOut.setOnClickListener {
            AlertDialog.Builder(this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes") { _, _ ->
                        auth.signOut()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    .setNegativeButton("Cancel", null).show()
        }
    }
    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    auth.signOut()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .setNegativeButton("Cancel", null).show()
    }
}