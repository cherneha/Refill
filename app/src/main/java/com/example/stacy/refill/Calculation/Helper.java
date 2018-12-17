package com.example.stacy.refill.Calculation;

import com.example.stacy.refill.Calendar.DateUtils;
import com.example.stacy.refill.Product;

public class Helper {
    public static Integer getRemainingDays(Product product){
        Double averageDays = product.getAverageDays();
        Double lastAmount = product.getLastUpdateQuantity();
        if (averageDays != -1) {
            int daysFromLastUpdate = DateUtils.getTimeRemaining(product.getLastUpdate());
            int remainingDays = Double.valueOf(averageDays * lastAmount).intValue() - daysFromLastUpdate;

            return remainingDays;
        }
        return 0;
    }

    public static Double getProductAmount(Product product){
        Double averageDays = product.getAverageDays();
        Double lastAmount = product.getLastUpdateQuantity();

        if (averageDays != -1) {
            int daysFromLastUpdate = DateUtils.getTimeRemaining(product.getLastUpdate());
            double remainingProductPercent = lastAmount - (double) daysFromLastUpdate / averageDays;
            if (remainingProductPercent < 0)
                remainingProductPercent = 0d;
            return remainingProductPercent;
        }
        return 0d;
    }
}
