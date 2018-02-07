package com.list.todo.todolist;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.Utils.ViewUtils;
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
        ViewUtils.populateSnackBar(task.getName(), this);
        setTitle(task.getName());
        initTaskFields(task);
    }

    private void initTaskFields(final TaskDB task) {
        final TextView name = findViewById(R.id.name_task);
        final TextView date = findViewById(R.id.date_task);
        final TextView hour = findViewById(R.id.hour_task);
        final TextView description = findViewById(R.id.description_task);
        name.setText(task.getName());
        date.setText(task.getDate());
        hour.setText(task.getTime());
        description.setText(task.getDescription());
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
