package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        this.title = "Information"

        val title = findViewById<TextView>(R.id.viewTitle)
        val year = findViewById<TextView>(R.id.viewYear)
        val rating = findViewById<TextView>(R.id.viewRating)
        val summary = findViewById<TextView>(R.id.viewSummary)
        val review = findViewById<Button>(R.id.review)
        val back = findViewById<Button>(R.id.infoBack)
        val db = FirebaseFirestore.getInstance()

        db.collection("movies").get().addOnSuccessListener { docs ->
            for (i in docs) {
                if("" + i.data["title"] + " (" + i.data["year"] + ")" == intent.getStringExtra("info")) {
                    val df = "%.1f"
                    title.text = i.data["title"].toString()
                    year.text = i.data["year"].toString()
                    rating.text = df.format(i.data["rating"].toString().toDouble() / i.data["count"].toString().toDouble()).toString() + " / 10"
                    summary.text = i.data["summary"].toString()
                    break
                }
            }
        }

        review.setOnClickListener {
            startActivity(Intent(this, ReviewActivity::class.java).putExtra("info", intent.getStringExtra("info")))
        }

        back.setOnClickListener {
            finish()
        }
    }
}