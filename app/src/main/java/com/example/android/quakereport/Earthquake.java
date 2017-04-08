package com.example.android.quakereport;

/**
 * Created by elizabethtarazona on 27/02/2017.
 */

public class Earthquake {
    private double magnitude;
    private String location;
    private Long timeInMiliseconds;
    private String url;

    public Earthquake(double magnitude,String location, Long timeInMilliseconds,String murl)
    {
        this.magnitude = magnitude;
        this.location = location;
        this.timeInMiliseconds = timeInMilliseconds;
        this.url = murl;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(Long date) {
        this.timeInMiliseconds = date;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public Long getDate() {
        return timeInMiliseconds;
    }

    public String getUrl() {
        return url;
    }
}
