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

    /**
     * Insert a new Task in the database
     * @param task
     * @param dbHelper
     */
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

    /**
     * Create a new TaskDB by a cursor pointed on row which contain a task
     * @param cursor
     * @return a new TaskDB
     */
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

    /**
     * Return the list of tasks in the DB
     * @param dbHelper
     * @return List of tasks
     */
    public static List<TaskDB> getTasks(final DBHelper dbHelper) {
        final List<TaskDB> tasksDB = new ArrayList<>();
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final Cursor cursor = getCursor(db);
        while (cursor.moveToNext()) tasksDB.add(getTaskDBByCursor(cursor));
        cursor.close();
        db.close();
        return tasksDB;
    }

    /**
     * Return the list of active tasks in the DB
     * @param dbHelper
     * @return List of active tasks
     */
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

    /**
     * Return the list of archived tasks in the DB
     * @param dbHelper
     * @return List of archived tasks
     */
    public static ArrayList<TaskDB> getArchivedTasks(final DBHelper dbHelper) {
        final ArrayList<TaskDB> tasksDB = new ArrayList<>();
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final Cursor cursor = getCursor(db);
        while (cursor.moveToNext()) {
            final int idState = cursor.getColumnIndex(TaskContract.TaskEntry.STATE);
            final int state = cursor.getInt(idState);
            if (state == 0) tasksDB.add(getTaskDBByCursor(cursor));
        }
        cursor.close();
        db.close();
        return tasksDB;
    }

    /**
     * Return a cursor for select in the task table
     * @param db
     * @return Cursor for task
     */
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

    /**
     * Select a task in the DB by it's id
     * @param id
     * @param dbHelper
     * @return The selected Task
     */
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

    /**
     * Update a task row in the DB
     * @param task
     * @param dbHelper
     */
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

    /**
     * Return the count of task in the DB
     * @param dbHelper
     * @return The count of task
     */
    public static int getCountTask(final DBHelper dbHelper) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TaskContract.TaskEntry.TABLE,
                null);
        final int count = cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }

    /**
     * Delete a task in the DB by it's ID
     * @param id
     * @param dbHelper
     */
    public static void deleteTaskById(final int id, final DBHelper dbHelper) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE, TaskContract.TaskEntry._ID + " = ?",
                new String[]{Integer.toString(id)});
        db.close();
    }

}
