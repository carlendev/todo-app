package com.list.todo.todolist.validation;

/**
 * Created by carlendev on 06/02/18.
 */

public class Validation {

    private String msg;

    private boolean value;

    public boolean isValue() {
        return value;
    }

    public void setValue(final boolean value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }
}
