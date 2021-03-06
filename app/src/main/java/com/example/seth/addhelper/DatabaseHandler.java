package com.example.seth.addhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seth on 12/3/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ADD.db";
    public static final String TABLE_TASKS = "tasks";

    public static ArrayList<Integer> task_ids;

    // Table column names
    public static final String ID = "_id";
    public static final String TASK_NAME = "TaskName";
    public static final String LENGTH_MIN = "LengthMinutes";
    public static final String LENGTH_MIN_COMPLETE = "LengthMinutesComplete";
    public static final String DAYS = "Days";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TASK_NAME + " VARCHAR(64)," +
                LENGTH_MIN + " INTEGER," +
                LENGTH_MIN_COMPLETE + " INTEGER," +
                DAYS + " VARCHAR(8))";
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
        values.put(LENGTH_MIN_COMPLETE, 0);
        values.put(DAYS, task.getDays());

        // Inserting row
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // Getting a single task
    public Task getTask(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{ID, TASK_NAME, LENGTH_MIN,
                        LENGTH_MIN_COMPLETE, DAYS}, ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                cursor.getString(4));

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
                task.setLengthComplete(Integer.parseInt(cursor.getString(3)));
                task.setDays(cursor.getString(4));

                // Add task to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    // Get a cursor
    public Cursor getCursor(int day) {

        /* Create LIKE pattern for query to match day */
        String dayPattern;
        switch (day) {
            case 1:
                dayPattern = "1______";
                break;
            case 2:
                dayPattern = "_1_____";
                break;
            case 3:
                dayPattern = "__1____";
                break;
            case 4:
                dayPattern = "___1___";
                break;
            case 5:
                dayPattern = "____1__";
                break;
            case 6:
                dayPattern = "_____1_";
                break;
            case 7:
                dayPattern = "______1";
                break;
            default:
                dayPattern = "%";
                break;
        }

        String selectQuery = "SELECT * FROM " + TABLE_TASKS +
                " WHERE " + DAYS + " LIKE '" + dayPattern + "'" +
                " ORDER BY " + ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        task_ids = new ArrayList();

        // Loop through all rows and add the ids to the array
        if (cursor.moveToFirst()) {
            do {
                task_ids.add(Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        return cursor;
    }

    // Updating a single task
    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LENGTH_MIN_COMPLETE, task.getLengthComplete());

        String whereClause = DatabaseHandler.ID + "=" + task.getID();

        // Inserting row
        db.update(TABLE_TASKS, values, whereClause, null);
        db.close();
    }

    // Deleting a single task
    public void deleteTask(Task task) {

    }






}
