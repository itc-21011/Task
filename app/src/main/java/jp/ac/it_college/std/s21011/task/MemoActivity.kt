package jp.ac.it_college.std.s21011.task

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import jp.ac.it_college.std.s21011.task.databinding.MemoBinding

class MemoActivity: AppCompatActivity() {
    companion object{
        private const val TABLE_NAME="memos"
    }

    private lateinit var binding: MemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val helper = DBHelper(this)

        val textTitle = findViewById<EditText>(R.id.text_title)
        val textContent = findViewById<EditText>(R.id.text_content)
        val textCategory = findViewById<FragmentContainerView>(R.id.fragment)
        val memoId: Long = intent.getLongExtra("id",0)
        if (memoId != 0L) {
            helper.readableDatabase.let {
                    db -> db.query(TABLE_NAME, arrayOf("id", "title", "content", "expense_item_id"), "id = ?", arrayOf(memoId.toString()), null, null, null, "1")
                .let { cursor ->
                    if (cursor.moveToFirst()) {
                        textTitle.setText(cursor.getString(1))
                        textContent.setText(cursor.getString(2))
                        textCategory
                    }
                }
            }
        }

        findViewById<Button>(R.id.save_button).setOnClickListener{
            helper.writableDatabase.let {
                    db ->
                val values = ContentValues().apply {
                    put("title", textTitle.text.toString())
                    put("Content", textContent.text.toString())
                }
                if (memoId != 0L) {
                    db.update(TABLE_NAME, values,"id = ?", arrayOf(memoId.toString()))
                } else {
                    db.insert(TABLE_NAME,null, values)
                }
            }
            finish()
        }

        findViewById<Button>(R.id.delete_button).setOnClickListener {
            helper.writableDatabase.let {
                    db ->
                db.delete(TABLE_NAME, "id = ?", arrayOf(memoId.toString()))
            }
            finish()
        }

        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish()
        }
    }
}