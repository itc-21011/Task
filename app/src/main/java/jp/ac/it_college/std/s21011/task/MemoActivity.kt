package jp.ac.it_college.std.s21011.task

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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


        val memoId: Long = intent.getLongExtra("id",0)
        if (memoId != 0L) {
            helper.readableDatabase.let {
                    db -> db.query(TABLE_NAME, arrayOf("id", "title", "content", "expense_item_id"), "id = ?", arrayOf(memoId.toString()), null, null, null, "1")
                .let { cursor ->
                    if (cursor.moveToFirst()) {
                        binding.textTitle.setText(cursor.getString(1))
                        binding.textContent.setText(cursor.getString(2))
                    }
                }
            }
        }

        binding.saveButton.setOnClickListener{
            helper.writableDatabase.let {
                    db ->
                val values = ContentValues().apply {
                    put("title", binding.textTitle.text.toString())
                    put("Content", binding.textContent.text.toString())

                }
                if (memoId != 0L) {
                    db.update(TABLE_NAME, values,"id = ?", arrayOf(memoId.toString()))
                } else {
                    db.insert(TABLE_NAME,null, values)
                }
            }
            finish()
        }

        binding.deleteButton.setOnClickListener {
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