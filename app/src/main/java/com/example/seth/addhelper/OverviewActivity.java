package com.example.seth.addhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Seth Kerr on 12/8/2015.
 */
public class OverviewActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
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
}
