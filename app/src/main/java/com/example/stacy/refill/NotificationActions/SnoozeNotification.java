package com.example.stacy.refill.NotificationActions;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.stacy.refill.Calculation.Helper;
import com.example.stacy.refill.DBManager.AppDatabase;
import com.example.stacy.refill.DBManager.Constants;
import com.example.stacy.refill.DBManager.Database;
import com.example.stacy.refill.DBManager.ProductDao;
import com.example.stacy.refill.DBManager.SyncProductDao;
import com.example.stacy.refill.Product;

import static android.content.Context.NOTIFICATION_SERVICE;

public class SnoozeNotification extends BroadcastReceiver {

    static Double snoozeDays = 2d;

    @Override
    public void onReceive(Context context, Intent intent) {
        AppDatabase db = Database.getInstance(context).getAppDatabase();
        ProductDao productDao = db.productDao();
        SyncProductDao syncProductDao = new SyncProductDao(productDao);
        //android.os.Debug.waitForDebugger();

        String productName = intent.getAction();

        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(productName.hashCode());

        Product product = syncProductDao.findByName(productName);

        int remainingDays = Helper.getRemainingDays(product);

        double averageDays = product.getAverageDays();
        if(averageDays == -1)
            averageDays = 1;

        double lastUpdateQuantity = product.getLastUpdateQuantity();
        double newLastUpdateQuantity = lastUpdateQuantity +
                (snoozeDays + Constants.DaysLeastAmount - remainingDays) / averageDays;
        product.setLastUpdateQuantity(newLastUpdateQuantity);
        product.setUpdateNeeded(false);

        double quantity = Helper.getProductAmount(product);
        product.setCurrentQuantity(quantity);

        syncProductDao.update(product);
    }
}
