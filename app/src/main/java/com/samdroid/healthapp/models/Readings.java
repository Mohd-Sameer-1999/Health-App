package com.samdroid.healthapp.models;

import android.content.Context;

import java.util.ArrayList;

public class Readings {
    String bloodSugarLevel, dateBs, timeBs, commentBs;
    String upperBound, lowerBound, dateBp, timeBp, commentBp;
    String bp, bs;

    public Readings(String bloodSugarLevel, String dateBs, String timeBs, String commentBs, String upperBound, String lowerBound, String dateBp, String timeBp, String commentBp) {
        this.bloodSugarLevel = bloodSugarLevel;
        this.dateBs = dateBs;
        this.timeBs = timeBs;
        this.commentBs = commentBs;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.dateBp = dateBp;
        this.timeBp = timeBp;
        this.commentBp = commentBp;
    }

    public String getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public String getDateBs() {
        return dateBs;
    }

    public String getTimeBs() {
        return timeBs;
    }

    public String getCommentBs() {
        return commentBs;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public String getDateBp() {
        return dateBp;
    }

    public String getTimeBp() {
        return timeBp;
    }

    public String getCommentBp() {
        return commentBp;
    }

}
