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

import com.list.todo.todolist.Factory.TaskFactory;
import com.list.todo.todolist.POJO.ETaskCategory;
import com.list.todo.todolist.POJO.Task;
import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.Utils.ViewUtils;
import com.list.todo.todolist.SQL.DBHelper;
import com.list.todo.todolist.SQL.TaskDBHelper;
import com.list.todo.todolist.validation.TaskValidation;
import com.list.todo.todolist.validation.Validation;

import java.util.Calendar;

public class TaskActivity extends AppCompatActivity {

    private final String ID_TASK = "idTask";
    private static final CharSequence TASK_CREATE = "Create Task";
    private static final CharSequence CREATE_MODE = "Create";
    private static final CharSequence ADD_MODE = "Add";
    private static final CharSequence EDIT_MODE = "Edit";

    private DBHelper dbHelper;
    private int taskId;
    private TaskDB task;
    private boolean inEdit;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new DBHelper(this);
        final String maybeID = getIntent().getStringExtra(ID_TASK);
        if (maybeID == null) {
            inEdit = false;
            ViewUtils.populateSnackBar("You can create a new Task", this);
            setTitle(TASK_CREATE);
            ViewUtils.setCategorySpinner(this, true);
            final Button button = findViewById(R.id.edit_button);
            button.setText(CREATE_MODE);
            enableInput(CREATE_MODE);
        } else {
            inEdit = true;
            taskId = Integer.parseInt(getIntent().getStringExtra(ID_TASK));
            task = TaskDBHelper.getTaskById(taskId, dbHelper);
            ViewUtils.populateSnackBar(task.getName(), this);
            ViewUtils.setCategorySpinner(this, false);
            setTitle(task.getName());
            initTaskFields(task);
        }
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
     * @param task
     */
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

    /**
     * @param item
     * @return
     */
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

    /**
     * @param mode Current mode of the activity
     */
    private void enableInput(final CharSequence mode) {
        final TextView name = findViewById(R.id.name_task);
        final TextView date = findViewById(R.id.date_task);
        final TextView hour = findViewById(R.id.hour_task);
        final TextView description = findViewById(R.id.description_task);
        final Spinner spinner = findViewById(R.id.category_name);
        final Button button = findViewById(R.id.edit_button);
        final boolean state = mode.equals(ADD_MODE) || mode.equals(CREATE_MODE);
        name.setEnabled(state);
        date.setEnabled(state);
        hour.setEnabled(state);
        description.setEnabled(state);
        spinner.setEnabled(state);
        button.setText(mode);
    }

    /**
     * @return
     */
    private String isMode() {
        final Button button = findViewById(R.id.edit_button);
        return button.getText().toString();
    }

    /**
     *
     */
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

    /**
     * @param v
     */
    public void onClickHour(final View v) {
        final TextView time = findViewById(R.id.hour_task);
        String[] timeArray = new String[2];
        if (inEdit) timeArray = task.getTime().split(":");
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
                }, inEdit ? Integer.parseInt(timeArray[0]) : hour,
                inEdit ? Integer.parseInt(timeArray[1]) : minute,
                true);
        timePickerDialog.show();
    }

    public void onClickDate(final View v) {
        final TextView date = findViewById(R.id.date_task);
        String[] dateArray = new String[3];
        if (inEdit) dateArray = task.getDate().split("-");
        final Calendar now = Calendar.getInstance();
        final int year = now.get(Calendar.YEAR);
        final int month = now.get(Calendar.MONTH);
        final int day = now.get(Calendar.DAY_OF_MONTH);
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
                }, inEdit ? Integer.parseInt(dateArray[2]) : year,
                inEdit ? Integer.parseInt(dateArray[1]) : month,
                inEdit ? Integer.parseInt(dateArray[0]) : day);
        datePickerDialog.show();
    }

    /**
     * 
     */
    private void create() {
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
        final Task newTask = TaskFactory.createTask(name,
                1,
                ETaskCategory.valueOf(category.toUpperCase()).ordinal(),
                description,
                date,
                time);
        final Validation validation = new TaskValidation(newTask).validation();
        if (!validation.isValue()) ViewUtils.populateSnackBar(validation.getMsg(),
                TaskActivity.this);
        else {
            TaskDBHelper.insertTask(newTask, new DBHelper(TaskActivity.this));
            Log.d("Create Task", "Task to create: " + newTask.getName());
            startActivity(new Intent(TaskActivity.this, MainActivity.class));
        }
    }

    /**
     * @param v
     */
    public void onClickEdit(final View v) {
        if (isMode().equals(EDIT_MODE)) enableInput(ADD_MODE);
        else if (isMode().equals(CREATE_MODE)) create();
        else {
            add();
            enableInput(EDIT_MODE);
        }
    }
}
