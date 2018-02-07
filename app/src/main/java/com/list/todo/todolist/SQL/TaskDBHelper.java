package com.list.todo.todolist.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.list.todo.todolist.POJO.Task;
import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.Factory.TaskFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlendev on 05/02/18.
 */

public class TaskDBHelper {

    public static void insertTask(final Task task, final DBHelper dbHelper) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.NAME, task.getName());
        values.put(TaskContract.TaskEntry.STATE, task.getState());
        values.put(TaskContract.TaskEntry.CATEGORY, task.getCategory());
        values.put(TaskContract.TaskEntry.DESCRIPTION, task.getDescription());
        values.put(TaskContract.TaskEntry.DATE, task.getDate());
        values.put(TaskContract.TaskEntry.TIME, task.getTime());
        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    private static TaskDB getTaskDBByCursor(final Cursor cursor) {
        final int id = cursor.getColumnIndex(TaskContract.TaskEntry._ID);
        final int idName = cursor.getColumnIndex(TaskContract.TaskEntry.NAME);
        final int idState = cursor.getColumnIndex(TaskContract.TaskEntry.STATE);
        final int idCategory = cursor.getColumnIndex(TaskContract.TaskEntry.CATEGORY);
        final int idDescription = cursor.getColumnIndex(TaskContract.TaskEntry.DESCRIPTION);
        final int idDate = cursor.getColumnIndex(TaskContract.TaskEntry.DATE);
        final int idTime = cursor.getColumnIndex(TaskContract.TaskEntry.TIME);
        return TaskFactory.createTaskDB(cursor.getString(idName),
                cursor.getInt(idState),
                cursor.getInt(idCategory),
                cursor.getString(idDescription),
                cursor.getString(idDate),
                cursor.getString(idTime),
                cursor.getInt(id));
    }

    public static List<TaskDB> getTasks(final DBHelper dbHelper) {
        final List<TaskDB> tasksDB = new ArrayList<>();
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final Cursor cursor = getCursor(db);
        while (cursor.moveToNext()) tasksDB.add(getTaskDBByCursor(cursor));
        cursor.close();
        db.close();
        return tasksDB;
    }

    public static ArrayList<TaskDB> getActiveTasks(final DBHelper dbHelper) {
        final ArrayList<TaskDB> tasksDB = new ArrayList<>();
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final Cursor cursor = getCursor(db);
        while (cursor.moveToNext()) {
            final int idState = cursor.getColumnIndex(TaskContract.TaskEntry.STATE);
            final int state = cursor.getInt(idState);
            if (state == 1) tasksDB.add(getTaskDBByCursor(cursor));
        }
        cursor.close();
        db.close();
        return tasksDB;
    }

    private static Cursor getCursor(final SQLiteDatabase db) {
        return db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.NAME,
                        TaskContract.TaskEntry.STATE,
                        TaskContract.TaskEntry.CATEGORY,
                        TaskContract.TaskEntry.DESCRIPTION,
                        TaskContract.TaskEntry.DATE,
                        TaskContract.TaskEntry.TIME},
                null, null, null, null, null);
    }

    public static TaskDB getTaskById(final int id, final DBHelper dbHelper) {
        final SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        final String query = "SELECT * FROM " + TaskContract.TaskEntry.TABLE + " WHERE " +
                TaskContract.TaskEntry._ID + " = " + Integer.toString(id);
        final Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        final TaskDB taskDB = getTaskDBByCursor(cursor);
        sqLiteDatabase.close();
        cursor.close();
        return taskDB;
    }

    public static void updateTask(final TaskDB task, final DBHelper dbHelper) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.NAME, task.getName());
        values.put(TaskContract.TaskEntry.STATE, task.getState());
        values.put(TaskContract.TaskEntry.CATEGORY, task.getCategory());
        values.put(TaskContract.TaskEntry.DESCRIPTION, task.getDescription());
        values.put(TaskContract.TaskEntry.DATE, task.getDate());
        values.put(TaskContract.TaskEntry.TIME, task.getTime());
        db.update(TaskContract.TaskEntry.TABLE, values, "_id = ?",
                new String[]{String.valueOf(task.get_ID())});
    }

}