package com.list.todo.todolist.Validation;

import com.list.todo.todolist.POJO.Task;

/**
 * Created by carlendev on 06/02/18.
 */

public class TaskValidation implements IValidation {

    private Task task;


    /**
     * Create a new task validation
     * @param task
     */
    public TaskValidation(final Task task) {
        this.task = task;
    }

    /**
     * Return a validation object with a msg and a state
     * @return Validation instance provide by the validation informations
     */
    public final Validation validation() {
        final Validation validation = new Validation();
        if (task.getName().isEmpty()) validation.setMsg("Name must have at least one character");
        if (task.getDate().isEmpty()) validation.setMsg("You should pick up a date");
        if (task.getTime().isEmpty()) validation.setMsg("You should pick up a time");
        if (validation.getMsg() == null || validation.getMsg().isEmpty()) validation.setValue(true);
        else validation.setValue(false);
        return validation;
    }

}
