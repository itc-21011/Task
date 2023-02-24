package jp.ac.it_college.std.s21011.task

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?): SQLiteOpenHelper(context, DBNAME, null, version) {
    companion object {
        private const val DBNAME = "DBSample.sqlite"
        private const val version = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            it.execSQL("create table items (" + "Item_id integer primary key, Item_name text)")
            it.execSQL("create table memos (" + "id integer primary key, title text, content text, category_item_id INTEGER DEFAULT '1' NOT NULL, FOREIGN KEY (category_item_id) REFERENCES items(Item_id))")
            it.execSQL("INSERT INTO items (Item_id, Item_name)" + " VALUES('1', '買い物')")
            it.execSQL("INSERT INTO items (Item_id, Item_name)" + " VALUES('2', '勉強')")
            it.execSQL("INSERT INTO items (Item_id, Item_name)" + " VALUES('3', '仕事')")
            it.execSQL("INSERT INTO items (Item_id, Item_name)" + " VALUES('4', 'その他')")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let {
            it.execSQL("DROP TABLE IF EXISTS memos")
            onCreate(it)
        }
    }


    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }
}