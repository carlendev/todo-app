package com.list.todo.todolist;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.list.todo.todolist.POJO.Task;
import com.list.todo.todolist.POJO.ETaskCategory;
import com.list.todo.todolist.Utils.ViewUtils;
import com.list.todo.todolist.factory.TaskFactory;
import com.list.todo.todolist.sql.DBHelper;
import com.list.todo.todolist.sql.TaskDBHelper;
import com.list.todo.todolist.validation.TaskValidation;
import com.list.todo.todolist.validation.Validation;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private final String TITLE = "Add Task";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(TITLE);
        ViewUtils.setCategorySpinner(this);

        final Button addBtn = findViewById(R.id.create_task);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = ViewUtils.getTextViewObj(R.id.name_task,
                        CreateTaskActivity.this);
                final String description = ViewUtils.getTextViewObj(R.id.description_task,
                        CreateTaskActivity.this);
                final String date = ViewUtils.getTextViewObj(R.id.date_task,
                        CreateTaskActivity.this);
                final String time = ViewUtils.getTextViewObj(R.id.hour_task,
                        CreateTaskActivity.this);
                final String category = ((Spinner) findViewById(R.id.category_name))
                        .getSelectedItem()
                        .toString();
                final Task task = TaskFactory.createTask(name,
                        1,
                        ETaskCategory.valueOf(category.toUpperCase()).ordinal(),
                        description,
                        date,
                        time);
                final Validation validation = new TaskValidation(task).validation();
                if (!validation.isValue()) ViewUtils.populateSnackBar(validation.getMsg(),
                        CreateTaskActivity.this);
                else {
                    TaskDBHelper.insertTask(task, new DBHelper(CreateTaskActivity.this));
                    Log.d("Add Task", "Task to add: " + task.getName());
                    startActivity(new Intent(CreateTaskActivity.this, MainActivity.class));
                }
            }
        });
    }

    public void onClickDate(final View v) {
        final TextView date = findViewById(R.id.date_task);
        final Calendar now = Calendar.getInstance();
        final int year = now.get(Calendar.YEAR);
        final int month = now.get(Calendar.MONTH);
        final int day= now.get(Calendar.DAY_OF_MONTH);
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
                }, year, month, day);
        datePickerDialog.show();
    }

    // TODO(carlendev) warning when date pass
    public void onClickHour(final View v) {
        final TextView time = findViewById(R.id.hour_task);
        final Calendar mCurrentTime = Calendar.getInstance();
        final int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mCurrentTime.get(Calendar.MINUTE);
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(final TimePicker timePicker,
                                          final int selectedHour,
                                          final int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
        }, hour, minute, true);
        timePickerDialog.show();
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
