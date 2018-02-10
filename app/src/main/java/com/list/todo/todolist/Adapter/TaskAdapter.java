package com.list.todo.todolist.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.list.todo.todolist.POJO.ETaskCategory;
import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by carlendev on 07/02/18.
 */

public class TaskAdapter extends ArrayAdapter<TaskDB> {

    private static final int MAX_NAME_LENGTH = 17;
    private static final String URGENT_COLOR = "#ffff4444";
    private static final String NORMAL_COLOR = "#ff000000";
    private static final String WARNING_COLOR = "#ffcc0000";

    public TaskAdapter(final Context context, final ArrayList<TaskDB> tasks) {
        super(context, 0, tasks);
    }

    private void setTextNameColor(final TextView name, final TaskDB task) {
        if (task.getCategory() == ETaskCategory.URGENT.ordinal())
            name.setTextColor(Color.parseColor(URGENT_COLOR));
        else name.setTextColor(Color.parseColor(NORMAL_COLOR));
    }

    private void setTextNameColorByDate(final TextView name, final TaskDB task) {
        final String[] dateArray = task.getDate().split("-");
        final Calendar now = Calendar.getInstance();
        final int yearCurrent = now.get(Calendar.YEAR);
        final int monthCurrent = now.get(Calendar.MONTH);
        final int dayCurrent = now.get(Calendar.DAY_OF_MONTH);
        final int year = Integer.parseInt(dateArray[2]);
        final int month = Integer.parseInt(dateArray[1]);
        final int day = Integer.parseInt(dateArray[0]);
        if (year <= yearCurrent && month <= monthCurrent && day <= dayCurrent)
            name.setTextColor(Color.parseColor(WARNING_COLOR));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final TaskDB task = getItem(position);
        if (convertView == null) convertView = LayoutInflater.from(getContext())
                .inflate(R.layout.items,
                        parent,
                        false);
        final TextView name = convertView.findViewById(R.id.task_name);
        final TextView date = convertView.findViewById(R.id.task_date);
        final TextView time = convertView.findViewById(R.id.task_hour);
        if (task == null) return convertView;
        name.setText(task.getName().length() > MAX_NAME_LENGTH ?
                task.getName().substring(0, 14) + "..." : task.getName());
        date.setText(task.getDate());
        time.setText(task.getTime());
        setTextNameColor(name, task);
        setTextNameColorByDate(name, task);
        return convertView;
    }

}
