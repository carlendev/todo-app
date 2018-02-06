package com.list.todo.todolist.POJO;

/**
 * Created by carlendev on 05/02/18.
 */

public class Task {

    protected String name;

    protected Integer state;

    protected Integer category;

    protected String description;

    protected String date;

    protected String time;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(final Integer state) {
        this.state = state;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(final Integer category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }
}
