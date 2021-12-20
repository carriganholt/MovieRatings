package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        this.title = "Enter Rating"

        val title = findViewById<TextView>(R.id.reviewViewTitle)
        val score = findViewById<EditText>(R.id.enterReview)
        val submit = findViewById<Button>(R.id.reviewSubmit)
        val back = findViewById<Button>(R.id.reviewCancel)

        title.text = intent.getStringExtra("info")

        submit.setOnClickListener {
            if(score.text.toString().toDouble() in (1.0..10.0)) {
                val db = FirebaseFirestore.getInstance()
                db.collection("movies").get().addOnSuccessListener { docs ->
                    for (i in docs) {
                        if("" + i.data["title"] + " (" + i.data["year"] + ")" == intent.getStringExtra("info")) {
                            val total = i.data["rating"].toString().toDouble() + score.text.toString().toDouble()
                            val count = i.data["count"].toString().toDouble() + 1.0

                            val movie: MutableMap<String, Any> = HashMap()
                            movie["rating"] = total
                            movie["count"] = count
                            db.collection("movies").document(i.id).update(movie)

                            AlertDialog.Builder(this)
                                    .setTitle("Rating Recorded")
                                    .setMessage("Thanks for your input!")
                                    .setPositiveButton("Ok") { _, _ ->
                                        startActivity(Intent(this, DashboardActivity::class.java))
                                    }.show()
                        }
                    }
                }

            } else {
                Toast.makeText(this, "Invalid Score!", Toast.LENGTH_SHORT).show()
            }
        }

        back.setOnClickListener {
            finish()
        }
    }
}