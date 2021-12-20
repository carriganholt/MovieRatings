package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddMovieActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        auth = FirebaseAuth.getInstance()

        this.title = "Add Movie"

        val title = findViewById<EditText>(R.id.movieTitle)
        val year = findViewById<EditText>(R.id.releaseYear)
        val rating = findViewById<EditText>(R.id.initialScore)
        val summary = findViewById<EditText>(R.id.summary)
        val submit = findViewById<Button>(R.id.addSubmit)
        val back = findViewById<Button>(R.id.addBack)

        submit.setOnClickListener {
            if(title.text.toString().trim() != "" && year.text.toString().trim() != "" && rating.text.toString().trim() != "") {
                if(rating.text.toString().toDouble() in (1.0..10.0)) {

                    val db = FirebaseFirestore.getInstance()
                    var inDatabase = false

                    db.collection("movies").get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (i in task.result!!) {
                                if (i["title"].toString().toLowerCase() == title.text.toString().toLowerCase().trim() && i["year"].toString() == year.text.toString().trim()) {
                                    inDatabase = true
                                }
                            }

                            if (!inDatabase) {
                                val movie: MutableMap<String, Any> = HashMap()
                                movie["title"] = title.text.toString().trim()
                                movie["year"] = year.text.toString().trim()
                                movie["rating"] = rating.text.toString().trim()
                                movie["count"] = "1"
                                movie["summary"] = summary.text.toString().trim()
                                db.collection("movies").add(movie)

                                Toast.makeText(this, "Movie added!", Toast.LENGTH_SHORT).show()
                                finish()

                            } else {
                                AlertDialog.Builder(this)
                                        .setTitle("Duplicate Record")
                                        .setMessage("This movie already exists in the database!")
                                        .setPositiveButton("Dashboard") { _, _ ->
                                            finish()
                                        }
                                        .setNegativeButton("Try Again", null).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Invalid score!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enter all information!", Toast.LENGTH_SHORT).show()
            }
        }

        back.setOnClickListener {
            finish()
        }
    }
}