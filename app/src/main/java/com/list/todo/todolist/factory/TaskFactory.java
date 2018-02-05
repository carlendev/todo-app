package com.list.todo.todolist.factory;

import com.list.todo.todolist.POJO.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by carlendev on 05/02/18.
 */

public class TaskFactory {

    public static Task createTask(final String name) {
        final Task task = new Task();
        task.setName(name);
        return task;
    }

    public static List<String> listNames(final List<Task> tasks) {
        final List <String> tasksName = new ArrayList<>();
        for (final Task task : tasks) tasksName.add(task.getName());
        return tasksName;
    }

}
