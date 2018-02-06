package com.list.todo.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.list.todo.todolist.POJO.Task;
import com.list.todo.todolist.factory.TaskFactory;
import com.list.todo.todolist.sql.DBHelper;
import com.list.todo.todolist.sql.TaskDBHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ListView taskListView;
    private ArrayAdapter<String> mAdapter;

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

    /* private void createAddDialog(final MainActivity ctx) {
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
                        TaskDBHelper.
                                insertTask(TaskFactory.
                                                createTask(String.
                                                        valueOf(taskEditText.getText()),
                                                        1,
                                                        0),
                                        dbHelper);
                        updateUI();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }*/

    private void updateUI() {
        final List<Task> tasksList = TaskDBHelper.getActiveTasks(dbHelper);
        final List <String> tasksNamesList = TaskFactory.listNames(tasksList);
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

}
