package com.example.stacy.refill;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private double currentQuantity;

    private double dailyUpdate;

    private Date lastUpdate;

    private double lastUpdateQuantity;

    public Product(String name, double currentQuantity){
        this.name = name;
        this.currentQuantity = currentQuantity;
        this.lastUpdateQuantity = currentQuantity;
        this.dailyUpdate = 0;
        this.lastUpdate = new Date();
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
}
