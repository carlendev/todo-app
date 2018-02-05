package com.list.todo.todolist.sql;

import android.provider.BaseColumns;

/**
 * Created by carlendev on 04/02/18.
 */

public class ItemContract {

    public static final String DB_NAME = "com.list.todo.todolist.sql.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String NAME = "name";
    }

}
