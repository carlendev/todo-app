package com.list.todo.todolist.POJO;

/**
 * Created by carlendev on 06/02/18.
 */

public enum ETaskCategory {
    URGENT("urgent"),
    NORMAL("normal")
    ;

    private final String stringValue;

    ETaskCategory(final String s) { stringValue = s; }

    public String toString() { return stringValue; }
}
