package com.list.todo.todolist.SQL;

/**
 * Created by carlendev on 04/02/18.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    /**
     * Create a new connection on the database
     * @param context
     */
    public DBHelper(final Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    /**
     * Create tables for the database
     * @param db
     */
    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.NAME + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.DESCRIPTION + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.DATE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.TIME + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.CATEGORY + " INTEGER NOT NULL, " +
                TaskContract.TaskEntry.STATE + " INTEGER NOT NULL);");
    }

    /**
     * Upgrade tables by dropping it
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }

}
