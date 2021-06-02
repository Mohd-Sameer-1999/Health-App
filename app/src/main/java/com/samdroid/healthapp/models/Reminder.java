package com.samdroid.healthapp.models;

public class Reminder {
    String reminder, label, time, date;

    public Reminder() {
    }

    public Reminder(String reminder, String label, String time, String date) {
        this.reminder = reminder;
        this.label = label;
        this.time = time;
        this.date = date;
    }

    public String getReminder() {
        return reminder;
    }

    public String getLabel() {
        return label;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
