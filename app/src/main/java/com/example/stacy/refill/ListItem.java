package com.example.stacy.refill;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(indices = {@Index(value = "name", unique = true)})

public class ListItem extends Item{
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private double quantity;

    private String units;

    public ListItem(String name, double quantity, String units) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getCurrentQuantity() {
        return getQuantity();
    }

    @Override
    public void setCurrentQuantity(double currentQuantity) {
        setQuantity(currentQuantity);
    }

    // no logic
    @Override
    public void setLastUpdateQuantity(double lastUpdateQuantity) { }

    // no logic
    @Override
    public void setLastUpdate(Date lastUpdate) {}

    // no logic
    @Override
    double getAverageDays() {
        return 0d;
    }

    // no logic
    @Override
    Date getLastUpdate() {
        return null;
    }

    // no logic
    @Override
    double getLastUpdateQuantity() {
        return 0d;
    }

    // no logic
    @Override
    void setAverageDays(double averageDays) { }

    // no logic
    @Override
    void setUpdateNeeded(boolean updateNeeded) { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
