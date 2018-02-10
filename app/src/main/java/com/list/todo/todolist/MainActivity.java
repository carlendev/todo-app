package com.list.todo.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.list.todo.todolist.Adapter.TaskAdapter;
import com.list.todo.todolist.POJO.ETaskActive;
import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.SQL.DBHelper;
import com.list.todo.todolist.SQL.TaskDBHelper;
import com.list.todo.todolist.Utils.ViewUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String ID_TASK = "idTask";

    private DBHelper dbHelper;
    private ListView taskListView;
    private TaskAdapter taskAdapter;
    private ArrayList<TaskDB> tasksDB;
    private ETaskActive state = ETaskActive.ACTIVE;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskListView = findViewById(R.id.TodoList);
        dbHelper = new DBHelper(this);
        ViewUtils.setActiveSpinner(this);
        updateUI(ETaskActive.ACTIVE);
        final Spinner active_spinner = findViewById(R.id.category_active);
        active_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(final AdapterView<?> adapterView,
                                       final View view,
                                       final int position,
                                       final long l) {
                if (ETaskActive.ACTIVE.ordinal() == position) {
                    updateUI(ETaskActive.ACTIVE);
                    state = ETaskActive.ACTIVE;
                }
                else {
                    updateUI(ETaskActive.ARCHIVED);
                    state = ETaskActive.ARCHIVED;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /**
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_task_menu:
                Log.d("Menu Items", "Open Task Create Activity");
                startActivity(new Intent(this, TaskActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @param state
     */
    private void updateUI(final ETaskActive state) {
        if (state == ETaskActive.ACTIVE) tasksDB = TaskDBHelper.getActiveTasks(dbHelper);
        else tasksDB = TaskDBHelper.getArchivedTasks(dbHelper);
        taskAdapter = new TaskAdapter(this, tasksDB, state);
        taskListView.setAdapter(taskAdapter);
    }

    /**
     * @param v
     */
    public void onClickAction(final View v) {
        final View parentRow = (View) v.getParent();
        final ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        final RelativeLayout rl = (RelativeLayout) v.getParent();
        final TextView tv = rl.findViewById(R.id.task_name);
        if (state == ETaskActive.ACTIVE) onClickDone(tv, position);
        else onClickArchived(tv, position);
    }

    /**
     * @param tv
     * @param position
     */
    private void onClickArchived(final TextView tv, final int position) {
        ViewUtils.populateSnackBar(tv.getText().toString() + " - Done", this);
        final TaskDB taskDB = tasksDB.get(position);
        taskDB.setState(1);
        TaskDBHelper.updateTask(tasksDB.get(position), dbHelper);
        updateUI(ETaskActive.ARCHIVED);
    }

    /**
     * @param tv
     * @param position
     */
    private void onClickDone(final TextView tv, final int position) {
        ViewUtils.populateSnackBar(tv.getText().toString() + " - Done", this);
        final TaskDB taskDB = tasksDB.get(position);
        taskDB.setState(0);
        TaskDBHelper.updateTask(tasksDB.get(position), dbHelper);
        updateUI(ETaskActive.ACTIVE);
    }

    /**
     * @param v
     */
    public void onClickItem(final View v) {
        final View parentRow = (View) v.getParent();
        final ListView listView = (ListView) parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        final Intent intent =new Intent(this, TaskActivity.class);
        intent.putExtra(ID_TASK, String.valueOf(tasksDB.get(position).get_ID()));
        startActivity(intent);
    }

}
