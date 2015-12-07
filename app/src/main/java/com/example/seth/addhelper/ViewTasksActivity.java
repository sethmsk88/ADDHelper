package com.example.seth.addhelper;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class ViewTasksActivity extends AppCompatActivity {

    private long taskStartTime, taskEndTime;
    private int currentTask_ID; // task currently being worked on
    private int currentTaskPos; // position of task in ListView
    private int savedRingerMode; // ringer mode before starting task
    private final DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        loadTasks();

        dumpTasksToLog(); // DEBUGGING

        /*
        // TESTING Intents
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout_0);
        Intent intent = getIntent();
        String message = intent.getStringExtra(AddTasksActivity.EXTRA_MESSAGE);

        TextView tv = new TextView(this);
        tv.setTextSize(40);
        tv.setText(message);
        layout.addView(tv);*/
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

    public void changeActivity(View view) {
        Intent intent = new Intent(this, AddTasksActivity.class);
        startActivity(intent);
    }

    /**
     * Query tasks from database and load them into a list
     */
    public void loadTasks() {
        String[] fromCols = {DatabaseHandler.TASK_NAME, DatabaseHandler.LENGTH_MIN_COMPLETE,
                DatabaseHandler.LENGTH_MIN};
        int[] toViews = {R.id.taskNameTextView, R.id.taskLengthCompleteTextView,
                R.id.taskLengthTextView};

        SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(this,
                R.layout.task_list_item, dbHandler.getCursor(), fromCols, toViews);
        ListView listView = (ListView) findViewById(R.id.tasks_list_view);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

                currentTaskPos = position;
                currentTask_ID = dbHandler.task_ids.get(position);
            }
        });
    }

    /**
     * Start/Stop task
     */
    public void startStopTask(View view) {
        Button startStopTaskButton = (Button) findViewById(R.id.start_stop_task_button);
        String buttonText = startStopTaskButton.getText().toString().toLowerCase();
        if(buttonText.contains("start")) {
            taskStartTime = (new Date()).getTime(); // start timer
            startStopTaskButton.setText("Stop Task");
            silencePhone(true);
        }
        else if (buttonText.contains("stop")) {
            taskEndTime = (new Date()).getTime(); // stop timer
            startStopTaskButton.setText("Start Task");

            long timeElapsedSec = (taskEndTime - taskStartTime) / 1000; // convert to seconds

            /* Update lengthComplete */
            Task task = dbHandler.getTask(currentTask_ID);

            int lengthComplete = task.getLengthComplete() + (int)timeElapsedSec;
            task.setLengthComplete(lengthComplete);

            dbHandler.updateTask(task);

            /* Update completed minutes value in ListView for selected task */
            ListView listView = (ListView) findViewById(R.id.tasks_list_view);
            LinearLayout linLay = (LinearLayout) listView.getChildAt(currentTaskPos);
            TextView textView = (TextView) linLay.getChildAt(1);
            textView.setText(String.valueOf(lengthComplete));

            silencePhone(false);
        }
    }

    /**
     * Silence the phone
     * @param silence
     */
    public void silencePhone(boolean silence) {
        AudioManager audioMan = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (silence) {
            // Set ringer mode to silent
            savedRingerMode = audioMan.getRingerMode();
            audioMan.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        else {
            // Set ringer mode to it's previous mode
            audioMan.setRingerMode(savedRingerMode);
        }
    }

    /**
     * DEBUGGING
     */
    public void dumpTasksToLog() {
        Log.d("Reading: ", "Reading all tasks...");
        DatabaseHandler db = new DatabaseHandler(this);
        List<Task> tasks = db.getAllTasks();

        for (Task t : tasks) {
            String log = "Id: " + t.getID() + " ,Task Name: " + t.getName() + " ,Length: " +
                    t.getLength() + ",LengthComplete: " + t.getLengthComplete() + " ,Days: " +
                    t.getDays();
            Log.d("TableRow ", log);
        }
    }

}
