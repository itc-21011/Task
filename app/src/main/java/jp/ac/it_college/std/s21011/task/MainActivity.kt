package jp.ac.it_college.std.s21011.task

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val add = findViewById<Button>(R.id.add)
        val listView = findViewById<ListView>(R.id.listView)
        setListViewAdapter(listView)

        add.setOnClickListener {
            val intent = Intent(this, MemoActivity::class.java)
            startActivity(intent)
        }

        listView.setOnItemClickListener { av, view, position, id ->
            val intent = Intent(this, MemoActivity::class.java)
            val itemId = listView.adapter.getItemId(position)
            intent.putExtra("id", itemId)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val helper = DBHelper(this)
        val listView = findViewById<ListView>(R.id.listView)
        setListViewAdapter(listView)
    }

    fun setListViewAdapter(listView: ListView)
    {
        val helper = DBHelper(this)
        helper.readableDatabase.use {
                db -> db.query("memos", arrayOf("id", "title", "content"),null,null,null,null,null,null)
            .use { cursor ->
                val memoList = mutableListOf<ListItem>()
                if (cursor.moveToFirst()) {
                    for (i in 1..cursor.count) {
                        val memoId = cursor.getInt(0)
                        val title = cursor.getString(1)
                        memoList.add(ListItem(memoId.toLong(), title))
                        cursor.moveToNext()
                    }
                }
                listView.adapter = CustomListAdapter(this, memoList, R.layout.list_item)
            }
        }
    }
}