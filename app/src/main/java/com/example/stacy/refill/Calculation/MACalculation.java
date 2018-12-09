package com.example.stacy.refill.Calculation;

public class MACalculation {

    public static double period = 7d;

    public static double Calculate(double prevMA, double newDays){
        double alpha = 2d / (1d + period);
        return newDays * alpha + prevMA * (1 - alpha);
    }
}
