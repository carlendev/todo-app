package com.list.todo.todolist.SQL;

import android.provider.BaseColumns;

/**
 * Created by carlendev on 04/02/18.
 */

public class TaskContract {

    public static final String DB_NAME = "com.list.todo.db";

    public static final int DB_VERSION = 8;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";
        public static final String NAME = "name";
        public static final String STATE = "state";
        public static final String CATEGORY = "category";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
        public static final String TIME = "time";
    }

}
