package com.list.todo.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.list.todo.todolist.POJO.Task;
import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.factory.TaskFactory;
import com.list.todo.todolist.sql.DBHelper;
import com.list.todo.todolist.sql.TaskDBHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ListView taskListView;
    private ArrayAdapter<String> mAdapter;
    private List<TaskDB> tasksDB;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskListView = findViewById(R.id.TodoList);
        dbHelper = new DBHelper(this);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_task_menu:
                Log.d("Menu Items", "Open Task Create Activity");
                startActivity(new Intent(this, CreateTask.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        tasksDB = TaskDBHelper.getActiveTasks(dbHelper);
        final List <String> tasksNamesList = TaskFactory.listNames((List<Task>)(List<?>)tasksDB);
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.items,
                    R.id.task_name,
                    tasksNamesList);
            taskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(tasksNamesList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void populateSnackBar(final String msg) {
        Snackbar.make(findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_LONG)
                .show();
    }

    public void onClickDone(final View v) {
        final View parentRow = (View) v.getParent();
        final ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        final RelativeLayout rl = (RelativeLayout)v.getParent();
        final TextView tv = rl.findViewById(R.id.task_name);
        populateSnackBar(tv.getText().toString() + " - Done");
        final TaskDB taskDB = tasksDB.get(position);
        taskDB.setState(0);
        TaskDBHelper.updateTask(tasksDB.get(position), dbHelper);
        updateUI();
    }

}
