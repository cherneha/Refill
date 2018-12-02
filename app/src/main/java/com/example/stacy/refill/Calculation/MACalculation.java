package com.example.stacy.refill.Calculation;

public class MACalculation {

    public static int period = 7;

    public static double Calculate(double prevMA, double newDays){
        double alpha = 2 / (1 + period);
        return newDays * alpha + prevMA * (1 - alpha);
    }
}
