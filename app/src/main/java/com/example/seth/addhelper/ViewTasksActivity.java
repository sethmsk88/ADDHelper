package com.example.seth.addhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ViewTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        loadTasks();

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

        DatabaseHandler dbHandler = new DatabaseHandler(this);
        SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(this,
                R.layout.task_list_item, dbHandler.getCursor(), fromCols, toViews);
        ListView listView = (ListView) findViewById(R.id.tasks_list_view);
        listView.setAdapter(myAdapter);
    }

}
