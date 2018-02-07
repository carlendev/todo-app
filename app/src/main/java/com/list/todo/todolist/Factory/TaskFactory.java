package com.list.todo.todolist.Factory;

import com.list.todo.todolist.POJO.Task;
import com.list.todo.todolist.POJO.TaskDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlendev on 05/02/18.
 */

public class TaskFactory {

    public static Task createTask(final String name,
                                  final Integer state,
                                  final Integer category,
                                  final String description,
                                  final String date,
                                  final String time) {
        final Task task = new Task();
        task.setName(name);
        task.setState(state);
        task.setCategory(category);
        task.setDescription(description);
        task.setDate(date);
        task.setTime(time);
        return task;
    }

    public static TaskDB createTaskDB(final String name,
                                      final Integer state,
                                      final Integer category,
                                      final String description,
                                      final String date,
                                      final String time,
                                      final int _ID) {
        final TaskDB task = new TaskDB();
        task.setName(name);
        task.setState(state);
        task.setCategory(category);
        task.setDescription(description);
        task.setDate(date);
        task.setTime(time);
        task.set_ID(_ID);
        return task;
    }


    public static List<String> listNames(final List<Task> tasks) {
        final List <String> tasksName = new ArrayList<>();
        for (final Task task : tasks) tasksName.add(task.getName());
        return tasksName;
    }

}
