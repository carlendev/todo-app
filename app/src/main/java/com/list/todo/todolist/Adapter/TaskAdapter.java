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
import com.list.todo.todolist.POJO.Task;
import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.R;

import java.util.ArrayList;

/**
 * Created by carlendev on 07/02/18.
 */

public class TaskAdapter extends ArrayAdapter<TaskDB> {

    private static final int MAX_NAME_LENGTH = 17;
    private static final String URGENT_COLOR = "#ffff4444";
    private static final String NORMAL_COLOR = "#ff000000";


    public TaskAdapter(final Context context, final ArrayList<TaskDB> tasks) {
        super(context, 0, tasks);
    }

    // TODO(carlendev) bug when trying to edit category
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        if (task.getCategory() == ETaskCategory.URGENT.ordinal())
            name.setTextColor(Color.parseColor(URGENT_COLOR));
        else name.setTextColor(Color.parseColor(NORMAL_COLOR));
        return convertView;
    }

}
