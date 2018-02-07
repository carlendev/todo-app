package com.list.todo.todolist;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.sql.DBHelper;
import com.list.todo.todolist.sql.TaskDBHelper;

public class TaskActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private int taskId;
    private TaskDB task;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DBHelper(this);
        taskId = Integer.parseInt(getIntent().getStringExtra("idTask"));
        task = TaskDBHelper.getTaskById(taskId, dbHelper);
        populateSnackBar(task.getName());
    }


    private void populateSnackBar(final String msg) {
        Snackbar.make(findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
