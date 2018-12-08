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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stacy.refill.DBManager.AppDatabase;
import com.example.stacy.refill.DBManager.Database;
import com.example.stacy.refill.DBManager.ProductDao;
import com.example.stacy.refill.DBManager.SyncProductDao;

public class LayoutGenerator {
    private Activity activity;
    AppDatabase db;
    ProductDao productDao;
    SyncProductDao syncProductDao;

    public LayoutGenerator(Activity activity){
        this.activity = activity;
        db = Database.getInstance(activity.getApplicationContext()).getAppDatabase();
        productDao = db.productDao();
        syncProductDao = new SyncProductDao(productDao);
    }

    public void addBlock(final String inputName, String productQuantity, String units, final LinearLayout listOfProducts) {

        final LinearLayout block = new LinearLayout(activity.getApplicationContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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
                syncProductDao.delete(syncProductDao.findByName(inputName));
                listOfProducts.removeView(block);
                return;
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
                AlertDialog.Builder alert = new AlertDialog.Builder(activity.getApplicationContext());

                alert.setTitle("Rename Product");
                alert.setMessage(inputName);

                final EditText input = new EditText(activity.getApplicationContext());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Product edited = syncProductDao.findByName(inputName);
                        edited.setName(String.valueOf(input.getText()));
                        syncProductDao.updateProduct(edited);

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
        quantity.setText(productQuantity);
        quantity.setTextSize(30);
        quantity.setPadding(16, 8, 16, 0);
        quantityLayout.addView(quantity);

        final TextView unit = new TextView(activity.getApplicationContext());
        unit.setText(units);
        unit.setTextSize(30);
        unit.setPadding(16, 8, 16, 0);
        quantityLayout.addView(unit);

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
                        Product edited = syncProductDao.findByName(inputName);
                        double newValue = Double.valueOf(String.valueOf(input.getText()))
                                + edited.getCurrentQuantity();
                        edited.setCurrentQuantity(newValue);
                        syncProductDao.updateProduct(edited);

                        quantity.setText(String.valueOf(newValue));
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