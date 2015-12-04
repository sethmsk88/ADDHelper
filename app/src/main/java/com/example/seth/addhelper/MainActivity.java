package com.example.seth.addhelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String days = "0000000"; // "SMTWTFS"
    public static DatabaseHandler db = null;

    public static final String DB_NAME = "ADHD_DB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);

        /*// Inserting test tasks
        Log.d("Insert: ", "Inserting...");
        db.addTask(new Task("Android Project", 90, "0101010"));
        db.addTask(new Task("Android Project", 90, "0010101"));

        // Reading all tasks
        Log.d("Reading: ", "Reading all tasks...");
        List<Task> tasks = db.getAllTasks();

        for (Task t : tasks) {
            String log = "Id: " + t.getID() + " ,Task Name: " + t.getName() + " ,Length: " +
                    t.getLength() + " ,Days: " + t.getDays();
            Log.d("Name: ", log);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
        Insert task into database
     */
    public void addTask(View view) {

        /* Get task attributes */
        EditText taskNameEditText = (EditText) findViewById(R.id.taskNameEditText);
        String taskName = taskNameEditText.getText().toString();

        EditText lengthTimeHrEditText = (EditText) findViewById(R.id.lengthTimeHrEditText);
        int lengthTimeHr = Integer.parseInt(lengthTimeHrEditText.getText().toString());

        EditText lengthTimeMinEditText = (EditText) findViewById(R.id.lengthTimeMinEditText);
        int lengthTimeMin = Integer.parseInt(lengthTimeMinEditText.getText().toString());

        // Calculate total length of task in minutes
        int lengthTotalMin = lengthTimeHr * 60 + lengthTimeMin;

        Task task = new Task(taskName, lengthTotalMin, days);
        db.addTask(task); // Insert task into database

        dumpTasksToLog(); // DEBUGGING
    }


    /*
        Modify the days String to represent the days that are toggled on/off
     */
    public void changeDays(View view) {
        ToggleButton dayToggleButton = (ToggleButton) findViewById(view.getId());
        boolean buttonState = dayToggleButton.isChecked();
        char buttonStateChar;

        // Create char representation of buttonState
        if (buttonState)
            buttonStateChar = '1';
        else
            buttonStateChar = '0';

        // Replace char for day of week that was clicked
        switch (view.getId()) {
            case R.id.sundayToggleButton:
                days = replaceCharAtIndex(days, 0, buttonStateChar);
                break;
            case R.id.mondayToggleButton:
                days = replaceCharAtIndex(days, 1, buttonStateChar);
                break;
            case R.id.tuesdayToggleButton:
                days = replaceCharAtIndex(days, 2, buttonStateChar);
                break;
            case R.id.wednesdayToggleButton:
                days = replaceCharAtIndex(days, 3, buttonStateChar);
                break;
            case R.id.thursdayToggleButton:
                days = replaceCharAtIndex(days, 4, buttonStateChar);
                break;
            case R.id.fridayToggleButton:
                days = replaceCharAtIndex(days, 5, buttonStateChar);
                break;
            case R.id.saturdayToggleButton:
                days = replaceCharAtIndex(days, 6, buttonStateChar);
                break;
        }
    }


    /*
        Change a char in a String at a given index.
        Return new String
     */
    private String replaceCharAtIndex(String s, int i, char c) {
        char[] charArray = s.toCharArray();
        charArray[i] = c;

        return String.valueOf(charArray);
    }

    /*
        Output contents of table to log
     */
    public void showTable(String tableName) {
        SQLiteDatabase myDB = null;

        try {
            myDB = SQLiteDatabase.openDatabase(DB_NAME, null, SQLiteDatabase.OPEN_READONLY);

            String tableString = String.format("Table %s:\n", tableName);
            Cursor allRows = myDB.rawQuery("SELECT * FROM " + tableName, null);

            // Build tableString
            if (allRows.moveToFirst()) {
                String[] colNames = allRows.getColumnNames();
                do {
                    for (String name: colNames) {
                        tableString += String.format("%s: %s\n", name,
                                allRows.getString(allRows.getColumnIndex(name)));
                    }
                    tableString += "\n";
                } while (allRows.moveToNext());
            }
            Log.v("MyActivity", tableString);

        }
        catch (Exception e) {
            Log.e("Error", "Error", e);
        }
        finally {
            if (myDB != null) {
                myDB.close();
            }
        }
    }


    /**
     * DEBUGGING
     */
    public void dumpTasksToLog() {
        Log.d("Reading: ", "Reading all tasks...");
        List<Task> tasks = db.getAllTasks();

        for (Task t : tasks) {
            String log = "Id: " + t.getID() + " ,Task Name: " + t.getName() + " ,Length: " +
                    t.getLength() + " ,Days: " + t.getDays();
            Log.d("TableRow ", log);
        }
    }

}
