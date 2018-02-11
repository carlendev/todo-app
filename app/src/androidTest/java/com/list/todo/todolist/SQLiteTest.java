package com.list.todo.todolist;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import com.list.todo.todolist.Factory.TaskFactory;
import com.list.todo.todolist.POJO.TaskDB;
import com.list.todo.todolist.SQL.DBHelper;
import com.list.todo.todolist.SQL.TaskDBHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by carlendev on 11/02/18.
 */

@RunWith(AndroidJUnit4.class)
public class SQLiteTest {

    private DBHelper db;
    private static final String TITLE = "test";

    @Before
    public void setUp() {
        RenamingDelegatingContext context = new RenamingDelegatingContext(
                InstrumentationRegistry.getTargetContext(),
                "test_"
        );
        db = new DBHelper(context);
    }

    @After
    public void finish() {
        db.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(db);
    }

    @Test
    public void testPreCountIseZero() {
        assertEquals(0, TaskDBHelper.getCountTask(db));
    }

    @Test
    public void testInsertTask() {
        TaskDBHelper.insertTask(TaskFactory.createTask(TITLE,
                1,
                0,
                "test",
                "1-1-2018",
                "12:12"), db);
        assertEquals(1, TaskDBHelper.getCountTask(db));
    }

    @Test
    public void testGetTaskName() {
        TaskDBHelper.insertTask(TaskFactory.createTask(TITLE,
                1,
                0,
                "test",
                "1-1-2018",
                "12:12"), db);
        final List<TaskDB> taskDBS = TaskDBHelper.getTasks(db);
        final int id = taskDBS.get(0).get_ID();
        assertEquals(taskDBS.get(0).getName(), TITLE);
    }

    @Test
    public void testGetTaskById() {
        TaskDBHelper.insertTask(TaskFactory.createTask(TITLE,
                1,
                0,
                "test",
                "1-1-2018",
                "12:12"), db);
        final List<TaskDB> taskDBS = TaskDBHelper.getTasks(db);
        final int id = taskDBS.get(0).get_ID();
        final TaskDB task = TaskDBHelper.getTaskById(id, db);
        assertEquals(task.getName(), TITLE);
    }

    @Test
    public void testUpdateTaskById() {
        TaskDBHelper.insertTask(TaskFactory.createTask(TITLE,
                1,
                0,
                "test",
                "1-1-2018",
                "12:12"), db);
        final List<TaskDB> taskDBS = TaskDBHelper.getTasks(db);
        final int id = taskDBS.get(0).get_ID();
        final TaskDB task = TaskDBHelper.getTaskById(id, db);
        task.setName(TITLE + TITLE);
        TaskDBHelper.updateTask(task, db);
        final TaskDB taskUpdated = TaskDBHelper.getTaskById(id, db);
        assertEquals(taskUpdated.getName(), TITLE + TITLE);
    }

    @Test
    public void testUpdateStatusTaskById() {
        TaskDBHelper.insertTask(TaskFactory.createTask(TITLE,
                1,
                0,
                "test",
                "1-1-2018",
                "12:12"), db);
        final List<TaskDB> taskDBS = TaskDBHelper.getTasks(db);
        final int id = taskDBS.get(0).get_ID();
        final TaskDB task = TaskDBHelper.getTaskById(id, db);
        task.setState(0);
        TaskDBHelper.updateTask(task, db);
        final TaskDB taskUpdated = TaskDBHelper.getTaskById(id, db);
        final int currentState = taskUpdated.getState();
        assertEquals(currentState, 0);
    }

    @Test
    public void testAddTwoTask() {
        TaskDBHelper.insertTask(TaskFactory.createTask(TITLE,
                1,
                0,
                "test",
                "1-1-2018",
                "12:12"), db);
        TaskDBHelper.insertTask(TaskFactory.createTask(TITLE,
                1,
                0,
                "test",
                "1-1-2018",
                "12:12"), db);
        assertEquals(TaskDBHelper.getCountTask(db), 2);
    }

    @Test
    public void testDeleteTask() {
        TaskDBHelper.insertTask(TaskFactory.createTask(TITLE,
                1,
                0,
                "test",
                "1-1-2018",
                "12:12"), db);
        assertEquals(TaskDBHelper.getCountTask(db), 1);
        final List<TaskDB> taskDBS = TaskDBHelper.getTasks(db);
        final int id = taskDBS.get(0).get_ID();
        TaskDBHelper.deleteTaskById(id, db);
        assertEquals(TaskDBHelper.getCountTask(db), 0);
    }

}
