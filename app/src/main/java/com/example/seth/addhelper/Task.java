package com.example.seth.addhelper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Seth on 12/3/2015.
 */
public class Task {

    // Private variables
    private int _id;
    private String _name;
    private int _length;
    private int _lengthComplete;
    private String _days;

    // Empty constructor
    public Task() {
        // intentionally left blank
    }

    // Constructor
    public Task(int id, String name, int length, int lengthComplete, String days) {
        this._id = id;
        this._name = name;
        this._length = length;
        this._lengthComplete = lengthComplete;
        this._days = days;
    }

    // Constructor
    public Task(int id, String name, int length, String days) {
        this._id = id;
        this._name = name;
        this._length = length;
        this._days = days;
    }

    // Constructor
    public Task(String name, int length, String days) {
        this._name = name;
        this._length = length;
        this._days = days;
    }

    // Getting ID
    public int getID() {
        return this._id;
    }

    // Setting ID
    public void setID(int id) {
        this._id = id;
    }

    // Getting Task name
    public String getName() {
        return this._name;
    }

    // Setting Task name
    public void setName(String name) {
        this._name = name;
    }

    // Getting Task length
    public int getLength() {
        return this._length;
    }

    // Setting Task length
    public void setLength(int length) {
        this._length = length;
    }

    // Getting Task Length Complete
    public int getLengthComplete() { return this._lengthComplete; }

    // Getting Task Length Complete
    public void setLengthComplete(int lengthComplete) { this._lengthComplete = lengthComplete; }

    // Getting days
    public String getDays() {
        return this._days;
    }

    // Setting days
    public void setDays(String days) {
        this._days = days;
    }
}
