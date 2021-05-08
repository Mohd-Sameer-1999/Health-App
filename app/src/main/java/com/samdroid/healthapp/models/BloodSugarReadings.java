package com.samdroid.healthapp.models;

public class BloodSugarReadings {

    String bloodSugarLevel, date, time, comment;

    public BloodSugarReadings() {
    }

    public BloodSugarReadings(String bloodSugarLevel, String date, String time, String comment) {
        this.bloodSugarLevel = bloodSugarLevel;
        this.date = date;
        this.time = time;
        this.comment = comment;
    }

    public String getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }
}
