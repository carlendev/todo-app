package com.list.todo.todolist;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.list.todo.todolist.POJO.ETaskCategory;
import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.Utils.ViewUtils;
import com.list.todo.todolist.sql.DBHelper;
import com.list.todo.todolist.sql.TaskDBHelper;
import com.list.todo.todolist.validation.TaskValidation;
import com.list.todo.todolist.validation.Validation;

public class TaskActivity extends AppCompatActivity {

    private final String ID_TASK = "idTask";
    private final String ADD_MODE = "Add";
    private final String EDIT_MODE = "Edit";

    private DBHelper dbHelper;
    private int taskId;
    private TaskDB task;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DBHelper(this);
        taskId = Integer.parseInt(getIntent().getStringExtra(ID_TASK));
        task = TaskDBHelper.getTaskById(taskId, dbHelper);
        ViewUtils.populateSnackBar(task.getName(), this);
        setTitle(task.getName());
        initTaskFields(task);
        ViewUtils.setCategorySpinner(this, false);
    }

    @SuppressLint("SetTextI18n")
    private void initTaskFields(final TaskDB task) {
        final TextView name = findViewById(R.id.name_task);
        final TextView date = findViewById(R.id.date_task);
        final TextView hour = findViewById(R.id.hour_task);
        final TextView description = findViewById(R.id.description_task);
        final Spinner spinner = findViewById(R.id.category_name);
        name.setText(task.getName());
        date.setText(task.getDate());
        final String[] timeArray = task.getTime().split(":");
        hour.setText(timeArray[0] + ":" +
                (timeArray[1].length() == 1 ? "0" + timeArray[1] : timeArray[1]));
        description.setText(task.getDescription());
        spinner.setSelection(task.getCategory());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void enableInput(final boolean state) {
        final TextView name = findViewById(R.id.name_task);
        final TextView date = findViewById(R.id.date_task);
        final TextView hour = findViewById(R.id.hour_task);
        final TextView description = findViewById(R.id.description_task);
        final Spinner spinner = findViewById(R.id.category_name);
        final Button button = findViewById(R.id.edit_button);
        name.setEnabled(state);
        date.setEnabled(state);
        hour.setEnabled(state);
        description.setEnabled(state);
        spinner.setEnabled(state);
        if (state) button.setText(ADD_MODE);
        else button.setText(EDIT_MODE);
    }

    private String isMode() {
        final Button button = findViewById(R.id.edit_button);
        return button.getText().toString();
    }

    private void add() {
        final String name = ViewUtils.getTextViewObj(R.id.name_task,
                TaskActivity.this);
        final String description = ViewUtils.getTextViewObj(R.id.description_task,
                TaskActivity.this);
        final String date = ViewUtils.getTextViewObj(R.id.date_task,
                TaskActivity.this);
        final String time = ViewUtils.getTextViewObj(R.id.hour_task,
                TaskActivity.this);
        final String category = ((Spinner) findViewById(R.id.category_name))
                .getSelectedItem()
                .toString();
        task.setName(name);
        task.setCategory(ETaskCategory.valueOf(category.toUpperCase()).ordinal());
        task.setDescription(description);
        task.setDate(date);
        task.setTime(time);
        final Validation validation = new TaskValidation(task).validation();
        if (!validation.isValue()) ViewUtils.populateSnackBar(validation.getMsg(),
                TaskActivity.this);
        else {
            TaskDBHelper.updateTask(task, new DBHelper(TaskActivity.this));
            Log.d("Update Task", "Task to add: " + task.getName());
            ViewUtils.populateSnackBar("Update " + task.getName(), TaskActivity.this);
            setTitle(task.getName());
        }
    }

    public void onClickHour(final View v) {
        final TextView time = findViewById(R.id.hour_task);
        final String[] timeArray = task.getTime().split(":");
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(final TimePicker timePicker,
                                          final int selectedHour,
                                          final int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]), true);
        timePickerDialog.show();
    }

    public void onClickDate(final View v) {
        final TextView date = findViewById(R.id.date_task);
        final String[] dateArray = task.getDate().split("-");
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(final DatePicker datePicker,
                                          final int year,
                                          final int monthOfYear,
                                          final int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, Integer.parseInt(dateArray[2]),
                Integer.parseInt(dateArray[1]),
                Integer.parseInt(dateArray[0]));
        datePickerDialog.show();
    }

    public void onClickEdit(final View v) {
        if (isMode().equals(EDIT_MODE)) enableInput(true);
        else {
            add();
            enableInput(false);
        }
    }
}
