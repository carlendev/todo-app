package com.list.todo.todolist.POJO;

/**
 * Created by carlendev on 11/02/18.
 */

public enum ETaskActive {
    ACTIVE("active"),
    ARCHIVED("archived")
    ;

    private final String stringValue;

    ETaskActive(final String s) { stringValue = s; }

    public String toString() { return stringValue; }

}
