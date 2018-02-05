package com.list.todo.todolist.sql;

/**
 * Created by carlendev on 04/02/18.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(final Context context) {
        super(context, ItemContract.DB_NAME, null, ItemContract.DB_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + ItemContract.TaskEntry.TABLE + " ( " +
                ItemContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.TaskEntry.NAME + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemContract.TaskEntry.TABLE);
        onCreate(db);
    }

}
