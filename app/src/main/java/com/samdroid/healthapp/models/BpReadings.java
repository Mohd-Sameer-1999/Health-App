package com.samdroid.healthapp.models;

public class BpReadings {
    String upperBound, lowerBound, date, time, comment;

    public BpReadings() {
    }

    public BpReadings(String upperBound, String lowerBound, String date, String time, String comment) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.date = date;
        this.time = time;
        this.comment = comment;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public String getLowerBound() {
        return lowerBound;
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
