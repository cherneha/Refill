package com.example.stacy.refill;

import java.util.Date;

public abstract class Item {
    abstract void setName(String name);
    abstract double getCurrentQuantity();
    abstract void setCurrentQuantity(double currentQuantity);
    abstract void setLastUpdateQuantity(double lastUpdateQuantity);
    abstract void setLastUpdate(Date lastUpdate);
}
