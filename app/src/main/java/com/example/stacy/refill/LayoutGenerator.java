package com.example.stacy.refill;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stacy.refill.Alarm.AlarmUtil;
import com.example.stacy.refill.Calculation.MACalculation;
import com.example.stacy.refill.Calendar.DateUtils;
import com.example.stacy.refill.DBManager.AppDatabase;
import com.example.stacy.refill.DBManager.Database;
import com.example.stacy.refill.DBManager.ProductDao;
import com.example.stacy.refill.DBManager.SyncDao;
import com.example.stacy.refill.DBManager.SyncProductDao;

import java.util.Date;
import java.util.List;

public class LayoutGenerator<T extends Item> {
    private Activity activity;
    //AppDatabase db;
    //ProductDao productDao;
    SyncDao<T> syncDao;

    public LayoutGenerator(Activity activity, SyncDao<T> dao) {
        this.activity = activity;
        //db = Database.getInstance(activity.getApplicationContext()).getAppDatabase();
        //productDao = db.productDao();
        //syncProductDao = new SyncProductDao(productDao);
        syncDao = dao;
    }


    public void addBlock(final String inputName, Double productQuantity, String units, final LinearLayout listOfProducts,
                         final boolean withRunOut) {

        final LinearLayout block = new LinearLayout(activity.getApplicationContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        block.setTag(inputName);
        if (Build.VERSION.SDK_INT > 15) {
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(12);
            shape.setColor(Color.parseColor("#FFE0B2"));
            block.setBackground(shape);
        } else {
            block.setBackgroundColor(Color.parseColor("#FFE0B2"));
        }

        block.setOrientation(LinearLayout.VERTICAL);
        params.setMargins(8, 8, 8, 0);
        block.setLayoutParams(params);

        final LinearLayout nameLayout = new LinearLayout(activity.getApplicationContext());
        nameLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT > 15) {
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(12);
            shape.setColor(activity.getResources().getColor(R.color.colorPrimary));
            nameLayout.setBackground(shape);
        } else {
            nameLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        final ImageButton editButton = new ImageButton(activity.getApplicationContext());
        if (Build.VERSION.SDK_INT > 15) {
            Drawable editIcon = activity.getResources().getDrawable(R.drawable.edit);
            editIcon.setBounds(0, 0, 50, 50);
            editButton.setBackground(editIcon);

        } else {
            editButton.setBackgroundColor(Color.parseColor("#FF9800"));
        }

        ImageButton deleteButton = new ImageButton(activity.getApplicationContext());
        if (Build.VERSION.SDK_INT > 23) {
            Drawable deleteIcon = activity.getResources().getDrawable(R.drawable.delete);
            deleteIcon.setBounds(0, 0, 50, 50);
            deleteButton.setBackground(deleteIcon);
        } else {
            deleteButton.setBackgroundColor(Color.parseColor("#FF9800"));
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(activity);

                alert.setTitle("Sure you want to delete this item?");

                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        syncDao.delete(syncDao.findByName(inputName));
                        listOfProducts.removeView(block);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(80, 80);
        buttonParams.setMargins(0, 0, 0, 0);
        deleteButton.setLayoutParams(buttonParams);

        LinearLayout.LayoutParams buttonParams2 = new LinearLayout.LayoutParams(80, 80);
        buttonParams2.setMargins(650, 0, 0, 0);

        final TextView text = new TextView(activity.getApplicationContext());
        text.setText(inputName);
        text.setPadding(8, 8, 8, 8);
        text.setTextSize(20);
        nameLayout.setLayoutParams(params2);
        text.setGravity(Gravity.LEFT);
        nameLayout.addView(text);
//        nameLayout.addView(menuButton);

        nameLayout.addView(editButton);
        nameLayout.addView(deleteButton);

        block.addView(nameLayout);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(activity);

                alert.setTitle("Rename Product");
                alert.setMessage(inputName);

                final EditText input = new EditText(activity.getApplicationContext());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String newName = String.valueOf(input.getText());
                        T edited = syncDao.findByName(inputName);
                        T existingItem = syncDao.findByName(newName);
                        if (existingItem != null) {
                            input.setError("Product with this name already exists.");
                        }
                        edited.setName(newName);
                        block.setTag(String.valueOf(input.getText()));
                        syncDao.update(edited);

                        text.setText(String.valueOf(input.getText()));
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });

        LinearLayout quantityLayout = new LinearLayout(activity.getApplicationContext());
        quantityLayout.setOrientation(LinearLayout.HORIZONTAL);

        final TextView quantity = new TextView(activity.getApplicationContext());
        quantity.setText(String.format("%.2f", productQuantity));
        quantity.setTextSize(30);
        quantity.setPadding(16, 8, 16, 0);
        quantityLayout.addView(quantity);

        final TextView unit = new TextView(activity.getApplicationContext());
        unit.setText(units);
        unit.setTextSize(30);
        unit.setPadding(16, 8, 16, 0);
        quantityLayout.addView(unit);

        if (withRunOut) {
            final Button runOutButton = new Button(activity.getApplicationContext());
            runOutButton.setText("Run out");
            runOutButton.setTextSize(20);
            runOutButton.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            runOutButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    T runOut = syncDao.findByName(inputName);

                    int daysFromLastUpdate = DateUtils.getTimeRemaining(runOut.getLastUpdate());
                    // we want to have days value on 100% of product
                    double daysFromLastUpdateWithQuantity = daysFromLastUpdate;
                    if (runOut.getLastUpdateQuantity() != 0)
                        daysFromLastUpdateWithQuantity = daysFromLastUpdate / runOut.getLastUpdateQuantity();

                    Double prevEma = runOut.getAverageDays(); // it's on 100% of product
                    //int remainingDays = prevEma.intValue() - daysFromLastUpdate;
                    double newEma;
                    if (prevEma == -1) {
                        newEma = daysFromLastUpdateWithQuantity;
                    } else {
                        newEma = MACalculation.Calculate(prevEma, daysFromLastUpdateWithQuantity);
                    }
                    runOut.setAverageDays(newEma);
                    runOut.setCurrentQuantity(0d);
                    runOut.setLastUpdate(new Date());
                    runOut.setLastUpdateQuantity(0d);
                    runOut.setUpdateNeeded(true);
                    syncDao.update(runOut);
                    quantity.setText(String.format("%.2f", 0d));
                }
            });
            nameLayout.addView(runOutButton);
        }

        ImageButton addButton = new ImageButton(activity.getApplicationContext());
        if (Build.VERSION.SDK_INT > 15) {
            Drawable cartIcon = activity.getResources().getDrawable(R.drawable.add);
            cartIcon.setBounds(0, 0, 50, 50);
            addButton.setBackground(cartIcon);
        } else {
            addButton.setBackgroundColor(Color.parseColor("#FF9800"));
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(activity);

                alert.setTitle("Add");
                alert.setMessage("How much?");

                final EditText input = new EditText(activity.getApplicationContext());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        T edited = syncDao.findByName(inputName);
                        double newValue = Double.valueOf(String.valueOf(input.getText()))
                                + edited.getCurrentQuantity();
                        edited.setCurrentQuantity(newValue);
                        edited.setLastUpdateQuantity(newValue);
                        edited.setLastUpdate(new Date());
                        if (withRunOut && !AlarmUtil.isRemainingDaysLessThanAverage((Product) edited))
                            edited.setUpdateNeeded(false);
                        syncDao.update(edited);

                        quantity.setText(String.format("%.2f", newValue));
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });
        LinearLayout.LayoutParams addButtonParams = new LinearLayout.LayoutParams(80, 80);
        addButtonParams.setMargins(0, 20, 0, 0);
        addButton.setLayoutParams(addButtonParams);
        quantityLayout.addView(addButton);
        block.addView(quantityLayout);
        listOfProducts.addView(block);
    }
}
