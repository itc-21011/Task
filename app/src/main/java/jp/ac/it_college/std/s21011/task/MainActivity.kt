package jp.ac.it_college.std.s21011.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)
        val data = listOf(
            ListItem(1, "メモタイトル1"),
            ListItem(2, "メモタイトル2"),
            ListItem(3, "メモタイトル3"),
        )
        listView.adapter = CustomListAdapter(this, data,R.layout.list_item)
    }
}