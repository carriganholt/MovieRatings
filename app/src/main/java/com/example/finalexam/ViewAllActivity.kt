package com.example.finalexam

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class ViewAllActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)

        this.title = "All Movies"

        val list = findViewById<ListView>(R.id.allList)
        val back = findViewById<Button>(R.id.viewAllBack)
        val db = FirebaseFirestore.getInstance()
        val elements = ArrayList<String>()
        var array = arrayOf("")

        db.collection("movies").get().addOnSuccessListener { docs ->
            for (i in docs) {
                elements.add("" + i.data["title"] + " (" + i.data["year"] + ")")
            }

            list.adapter = MyAdapter(elements, this)
            array = elements.toTypedArray()
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