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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase myDB = null;
        String tableName = "tasks";
        String data = "";

        /* Create a database */
        try {
            myDB = this.openOrCreateDatabase("ADHD_DB", MODE_PRIVATE, null);

            /* Create a table in the database */
            myDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    tableName +
                    " (T_ID INT PRIMARY KEY NOT NULL, " +
                    " TaskName VARCHAR(64), " +
                    " LengthMinutes INT, " +
                    " Days VARCHAR(8))"
            );
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
}
