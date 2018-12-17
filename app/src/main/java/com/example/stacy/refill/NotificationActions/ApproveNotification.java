package com.example.stacy.refill.NotificationActions;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.stacy.refill.Calculation.Helper;
import com.example.stacy.refill.Calculation.MACalculation;
import com.example.stacy.refill.DBManager.AppDatabase;
import com.example.stacy.refill.DBManager.Database;
import com.example.stacy.refill.DBManager.ProductDao;
import com.example.stacy.refill.DBManager.SyncProductDao;
import com.example.stacy.refill.Product;

import static android.content.Context.NOTIFICATION_SERVICE;

public class ApproveNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppDatabase db = Database.getInstance(context).getAppDatabase();
        ProductDao productDao = db.productDao();
        SyncProductDao syncProductDao = new SyncProductDao(productDao);

        String productName = intent.getAction();
        //android.os.Debug.waitForDebugger();

        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(productName.hashCode());

        Product product = syncProductDao.findByName(productName);
        product.setCurrentQuantity(Helper.getProductAmount(product));
        double newMA = MACalculation.Calculate(product.getAverageDays(), Math.floor(product.getAverageDays()));
        product.setAverageDays(newMA);
        product.setUpdateNeeded(true);

        syncProductDao.update(product);
    }
}
