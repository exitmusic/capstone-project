package com.example.kai.locallore.data;

/**
 * Created by kchang on 5/14/16.
 */
public class Lore {

    private String id;
    private String title;
    private String lore;
    private double latitude;
    private double longitude;

    public Lore(String id, String title, String lore, double latitude, double longitude) {
        this.id = id;
        this.title = title;
        this.lore = lore;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLore() {
        return this.lore;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String toString() {
        return this.id + ", " + this.title + ", " + this.lore + ", " +
                String.valueOf(this.latitude) + ", " +
                String.valueOf(this.longitude);
    }
}
