package com.example.seth.addhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seth on 12/3/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ADHD_DB";
    private static final String TABLE_TASKS = "tasks";

    // Table column names
    private static final String ID = "T_ID";
    private static final String TASK_NAME = "TaskName";
    private static final String LENGTH_MIN = "LengthMinutes";
    private static final String DAYS = "days";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TASK_NAME + " VARCHAR(64)," +
                LENGTH_MIN + " INTEGER," + DAYS + " VARCHAR(8))";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        // Create table again
        onCreate(db);
    }

    // Adding a new task
    public void addTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_NAME, task.getName());
        values.put(LENGTH_MIN, task.getLength());
        values.put(DAYS, task.getDays());

        // Inserting row
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // Getting a single task
    public Task getTask(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[] {ID, TASK_NAME, LENGTH_MIN, DAYS},
                ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                cursor.getString(3));

        return task;
    }

    // Getting all tasks
    public List<Task> getAllTasks() {

        List<Task> taskList = new ArrayList<Task>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add them to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setID(Integer.parseInt(cursor.getString(0)));
                task.setName(cursor.getString(1));
                task.setLength(Integer.parseInt(cursor.getString(2)));
                task.setDays(cursor.getString(3));

                // Add task to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    // Updating a single task
    public int updateTask(Task task) {
        return 0;
    }

    // Deleting a single task
    public void deleteTask(Task task) {

    }






}
