package com.example.finalexam

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore

class FindMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_movie)

        this.title = "Find Movie"

        val searchBox = findViewById<EditText>(R.id.searchBox)
        val list = findViewById<ListView>(R.id.list)
        val search = findViewById<Button>(R.id.search)
        val back = findViewById<Button>(R.id.findMovieBack)
        val db = FirebaseFirestore.getInstance()
        val elements = ArrayList<String>()
        var array = arrayOf("")

        search.setOnClickListener {
            array = arrayOf("")
            elements.clear()

            if(searchBox.text.toString().trim() == "") {
                db.collection("movies").get().addOnSuccessListener { docs ->
                    for (i in docs) {
                        elements.add("" + i.data["title"] + " (" + i.data["year"] + ")")
                    }

                    list.adapter = ViewAllActivity.MyAdapter(elements, this)
                    array = elements.toTypedArray()
                }

            } else {
                db.collection("movies").get().addOnSuccessListener { docs ->
                    for (i in docs) {
                        if((i.data["title"].toString().toLowerCase() + i.data["year"]).contains(searchBox.text.toString().trim().toLowerCase())) {
                            elements.add("" + i.data["title"] + " (" + i.data["year"] + ")")
                        }
                    }

                    list.adapter = ViewAllActivity.MyAdapter(elements, this)
                    array = elements.toTypedArray()
                }
            }
        }

        list.setOnItemClickListener { _, _, position, _ ->
            startActivity(Intent(this, InfoActivity::class.java).putExtra("info", array[position]))
        }

        back.setOnClickListener {
            finish()
        }
    }

    class MyAdapter(private var data: ArrayList<String>, var context: Context) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val tv = TextView(context)
            tv.text = data[position]
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            tv.setPadding(10, 10, 10, 10)
            return tv
        }

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): Any {
            return 0
        }

        override fun getItemId(position: Int): Long {
            return 0
        }
    }
}