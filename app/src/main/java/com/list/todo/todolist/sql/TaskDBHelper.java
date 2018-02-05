package com.list.todo.todolist.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.list.todo.todolist.POJO.Task;
import com.list.todo.todolist.factory.TaskFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlendev on 05/02/18.
 */

public class TaskDBHelper {

    public static void insertTask(final Task task, final DBHelper dbHelper) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put(ItemContract.TaskEntry.NAME, task.getName());
        db.insertWithOnConflict(ItemContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public static List<Task> getTasks(final DBHelper dbHelper) {
        final List<Task> tasks = new ArrayList<>();
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final Cursor cursor = db.query(ItemContract.TaskEntry.TABLE,
                new String[]{ItemContract.TaskEntry._ID, ItemContract.TaskEntry.NAME},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            final int idx = cursor.getColumnIndex(ItemContract.TaskEntry.NAME);
            tasks.add(TaskFactory.createTask(cursor.getString(idx)));
        }
        cursor.close();
        db.close();
        return tasks;
    }
}
