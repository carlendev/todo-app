package com.list.todo.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.list.todo.todolist.Adapter.TaskAdapter;
import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.SQL.DBHelper;
import com.list.todo.todolist.SQL.TaskDBHelper;
import com.list.todo.todolist.Utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String ID_TASK = "idTask";

    private DBHelper dbHelper;
    private ListView taskListView;
    private TaskAdapter taskAdapter;
    private ArrayList<TaskDB> tasksDB;


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
                startActivity(new Intent(this, CreateTaskActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO(carlendev) better display of list item urgent and on close date and close hour
    private void updateUI() {
        tasksDB = TaskDBHelper.getActiveTasks(dbHelper);
        if (taskAdapter == null) {
            taskAdapter = new TaskAdapter(this, tasksDB);
            taskListView.setAdapter(taskAdapter);
        } else {
            taskAdapter.clear();
            taskAdapter.addAll(tasksDB);
            taskAdapter.notifyDataSetChanged();
        }
    }

    public void onClickDone(final View v) {
        final View parentRow = (View) v.getParent();
        final ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        final RelativeLayout rl = (RelativeLayout)v.getParent();
        final TextView tv = rl.findViewById(R.id.task_name);
        ViewUtils.populateSnackBar(tv.getText().toString() + " - Done", this);
        final TaskDB taskDB = tasksDB.get(position);
        taskDB.setState(0);
        TaskDBHelper.updateTask(tasksDB.get(position), dbHelper);
        updateUI();
    }

    public void onClickItem(final View v) {
        final View parentRow = (View) v.getParent();
        final ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        final Intent intent =new Intent(this, TaskActivity.class);
        intent.putExtra(ID_TASK, String.valueOf(tasksDB.get(position).get_ID()));
        startActivity(intent);
    }

}
