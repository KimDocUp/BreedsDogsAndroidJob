package test.job.serverconnect

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import test.job.testparants.DImage


class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " VARCHAR(32)," +
                KEY_URL + " VARCHAR(64)" +
                ");")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addLike(name: String,url: String) {
        val values = ContentValues()
        values.put(KEY_NAME, name)
        values.put(KEY_URL, url)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun delLike(url : String) {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, "$KEY_URL=?", arrayOf(url))
        db.close()
    }

    fun getAllName() : ArrayList<SQLDogBreeds> {

        var dataList = ArrayList<SQLDogBreeds>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT $KEY_NAME FROM $TABLE_NAME", null)
        if (cursor != null) {
            Log.d("KeyMe", "not null ")
            if (cursor!!.moveToFirst()) {

                val sqlDogBreeds = SQLDogBreeds()
                sqlDogBreeds.setBreeds(cursor.getString(cursor.getColumnIndex(KEY_NAME)))
                dataList.add(sqlDogBreeds)
                while (cursor.moveToNext()) {
                    val sqlDogBreeds2 = SQLDogBreeds()
                    sqlDogBreeds2.setBreeds(cursor.getString(cursor.getColumnIndex(KEY_NAME)))
                    dataList.add(sqlDogBreeds2)
                }
                cursor.close()
            }
        }
        for(dataSQL in dataList) {
            val cursor2 = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $KEY_NAME = ?", arrayOf(dataSQL.getName()), null)
            if (cursor2 != null) {
                if (cursor2!!.moveToFirst()) {
                    val dI = DImage()
                    dI.setUrl(cursor2.getString(cursor2.getColumnIndex(KEY_URL)))
                    dI.setLike(true)
                    dataSQL.addUrls(dI)
                    while (cursor2.moveToNext()) {
                        val dI2 = DImage()
                        dI2.setUrl(cursor2.getString(cursor2.getColumnIndex(KEY_URL)))
                        dI2.setLike(true)
                        dataSQL.addUrls(dI2)
                    }
                    cursor2.close()
                }
            }
        }
        return dataList
    }

    companion object {
        const val DATABASE_VERSION = 4
        const val DATABASE_NAME = "likeDb"
        const val TABLE_NAME= "likes"
        const val KEY_ID = "_id"
        const val KEY_NAME = "name"
        const val KEY_URL = "url"
    }
}