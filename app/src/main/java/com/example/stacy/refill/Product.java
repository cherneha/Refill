package com.example.stacy.refill;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(indices = {@Index(value = "name", unique = true)})

public class Product {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private double currentQuantity;

    private double dailyUpdate;

    private Date lastUpdate;

    private double lastUpdateQuantity;

    private String units;

    private double averageDays;

    public Product(String name, double currentQuantity, String units){
        this.name = name;
        this.currentQuantity = currentQuantity;
        this.lastUpdateQuantity = currentQuantity;
        this.dailyUpdate = 0;
        this.lastUpdate = new Date();
        this.units = units;
        this.averageDays = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(double currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public double getDailyUpdate() {
        return dailyUpdate;
    }

    public void setDailyUpdate(double dailyUpdate) {
        this.dailyUpdate = dailyUpdate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public double getLastUpdateQuantity() {
        return lastUpdateQuantity;
    }

    public void setLastUpdateQuantity(double lastUpdateQuantity) {
        this.lastUpdateQuantity = lastUpdateQuantity;
    }
    public String getUnits(){
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setAverageDays(double averageDays){
        this.averageDays = averageDays;
    }

    public double getAverageDays() {
        return averageDays;
    }
}
