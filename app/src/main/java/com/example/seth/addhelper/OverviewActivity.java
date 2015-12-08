package com.example.seth.addhelper;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Seth Kerr on 12/8/2015.
 */
public class OverviewActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        populateTodayTextViews();
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

        if (id == R.id.menu_overview) {
            Intent intent = new Intent(this, OverviewActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_view_tasks) {
            Intent intent = new Intent(this, ViewTasksActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_add_tasks) {
            Intent intent = new Intent(this, AddTasksActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Populate text views in the Today category
     */
    public void populateTodayTextViews() {

        int totalCompletedSeconds = 0;
        int totalRemainingSeconds = 0;
        int totalCompletedTasks = 0;
        int totalRemainingTasks = 0;

        int length, lengthComplete, hrs, mins;

        // Get tasks for current day
        DatabaseHandler dbHandler = new DatabaseHandler(this);
        Cursor cursor = dbHandler.getCursor(getCurrentDay());

        // Loop through all rows and total the time and task count
        if (cursor.moveToFirst()) {
            do {
                length = Integer.parseInt(cursor.getString(2));
                lengthComplete = Integer.parseInt(cursor.getString(3));

                totalCompletedSeconds += lengthComplete;

                // If task is not complete
                if (lengthComplete < length) {
                    totalRemainingSeconds += length - lengthComplete;
                    totalRemainingTasks++;
                }
                else { // task is complete
                    totalCompletedTasks++;
                }
            } while (cursor.moveToNext());
        }

        TextView timeCompletedTextView = (TextView) findViewById(R.id.time_completed_textview);
        TextView timeRemainingTextView = (TextView) findViewById(R.id.time_remaining_textview);
        TextView tasksCompletedTextView = (TextView) findViewById(R.id.tasks_completed_textview);
        TextView tasksRemainingTextView = (TextView) findViewById(R.id.tasks_remaining_textview);

        timeCompletedTextView.setText((totalCompletedSeconds / 60) + "h " +
                (totalCompletedSeconds % 60) + "m");
        timeRemainingTextView.setText((totalRemainingSeconds / 60) + "h " +
                (totalRemainingSeconds % 60) + "m");
        tasksCompletedTextView.setText(String.valueOf(totalCompletedTasks));
        tasksRemainingTextView.setText(String.valueOf(totalRemainingTasks));
    }

    /**
     * Get the index of the current day of the week
     * @return day of week
     */
    public int getCurrentDay() {
        /* Get current day of week */
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.DAY_OF_WEEK);
    }
}
