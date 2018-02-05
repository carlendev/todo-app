package com.list.todo.todolist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.list.todo.todolist.sql.DBHelper;
import com.list.todo.todolist.sql.ItemContract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper mHelper;
    private ListView taskListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskListView = findViewById(R.id.TodoList);
        mHelper = new DBHelper(this);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_task_menu:
                Log.d("Menu Items", "Add a new task");
                createAddDialog(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createAddDialog(final MainActivity ctx) {
        final EditText taskEditText = new EditText(ctx);
        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setTitle("Add a new task")
                .setMessage("Next Task")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String task = String.valueOf(taskEditText.getText());
                        Log.d("Add Dialog", "Task to add: " + task);
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(ItemContract.TaskEntry.NAME, task);
                        db.insertWithOnConflict(ItemContract.TaskEntry.TABLE,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_REPLACE);
                        db.close();
                        updateUI();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }

    private void updateUI() {
        final ArrayList<String> taskList = new ArrayList<>();
        final SQLiteDatabase db = mHelper.getReadableDatabase();
        final Cursor cursor = db.query(ItemContract.TaskEntry.TABLE,
                new String[]{ItemContract.TaskEntry._ID, ItemContract.TaskEntry.NAME},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            final int idx = cursor.getColumnIndex(ItemContract.TaskEntry.NAME);
            taskList.add(cursor.getString(idx));
        }
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.items,
                    R.id.task_name,
                    taskList);
            taskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
    }

}
