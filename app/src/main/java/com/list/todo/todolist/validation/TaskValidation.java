package com.list.todo.todolist.validation;

import com.list.todo.todolist.POJO.Task;

/**
 * Created by carlendev on 06/02/18.
 */

public class TaskValidation implements IValidation {

    private Task task;

    public TaskValidation(final Task task) {
        this.task = task;
    }

    public Validation validation() {
        final Validation validation = new Validation();
        if (task.getName().length() < 1) validation.setMsg("Name must have at least one character");
        return validation;
    }

}
