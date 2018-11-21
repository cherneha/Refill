package com.example.stacy.refill;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ListFragment extends Fragment {
    LinearLayout listOfProducts;
    

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        listOfProducts = view.findViewById(R.id.product_list);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linLayoutParam.gravity = Gravity.CENTER_HORIZONTAL;
        for (int i = 0; i < 3; i++) {
            addBlock("Olive Oil", "1.2");
        }
        return view;
    }

    public void addBlock(String inputName, String productQuantity) {

        LinearLayout block = new LinearLayout(getActivity().getApplicationContext());
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

        LinearLayout nameLayout = new LinearLayout(getActivity().getApplicationContext());
        nameLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT > 15) {
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(12);
            shape.setColor(getResources().getColor(R.color.colorPrimary));
            nameLayout.setBackground(shape);
        } else {
            nameLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        ImageButton editButton = new ImageButton(getActivity().getApplicationContext());
        if (Build.VERSION.SDK_INT > 15) {
            Drawable editIcon = getActivity().getResources().getDrawable(R.drawable.edit);
            editIcon.setBounds(0, 0, 50, 50);
            editButton.setBackground(editIcon);

        } else {
            editButton.setBackgroundColor(Color.parseColor("#FF9800"));
        }
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            }
        });

        ImageButton deleteButton = new ImageButton(getActivity().getApplicationContext());
        if (Build.VERSION.SDK_INT > 15) {
            Drawable deleteIcon = getActivity().getResources().getDrawable(R.drawable.delete);
            deleteIcon.setBounds(0, 0, 50, 50);
            deleteButton.setBackground(deleteIcon);
        } else {
            deleteButton.setBackgroundColor(Color.parseColor("#FF9800"));
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Do something in response to button click
            }
        });
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(80, 80);
        buttonParams.setMargins(0, 0, 0, 0);
        deleteButton.setLayoutParams(buttonParams);

        LinearLayout.LayoutParams buttonParams2 = new LinearLayout.LayoutParams(80, 80);
        buttonParams2.setMargins(650, 0, 0, 0);
        editButton.setLayoutParams(buttonParams2);


//        Button menuButton = new Button(getActivity().getApplicationContext());
//
//        if (Build.VERSION.SDK_INT > 15) {
//            Drawable bag = getActivity().getResources().getDrawable(R.drawable.dots_vertical);
//            menuButton.setCompoundDrawablesWithIntrinsicBounds( bag, null, null, null );
//            menuButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        }
//        else {
//            menuButton.setText(":");
//        }
//        menuButton.setGravity(Gravity.RIGHT);


        TextView text = new TextView(getActivity().getApplicationContext());
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

        LinearLayout quantityLayout = new LinearLayout(getActivity().getApplicationContext());
        quantityLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageButton addButton = new ImageButton(getActivity().getApplicationContext());
        if (Build.VERSION.SDK_INT > 15) {
            Drawable cartIcon = getActivity().getResources().getDrawable(R.drawable.add);
            cartIcon.setBounds(0, 0, 50, 50);
            addButton.setBackground(cartIcon);
        } else {
            addButton.setBackgroundColor(Color.parseColor("#FF9800"));
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            }
        });

        LinearLayout.LayoutParams addButtonParams = new LinearLayout.LayoutParams(80, 80);
        addButtonParams.setMargins(0, 20, 0, 0);
        addButton.setLayoutParams(addButtonParams);


        TextView quantity = new TextView(getActivity().getApplicationContext());
        quantity.setText(productQuantity);
        quantity.setTextSize(30);
        quantity.setPadding(16, 8, 16, 0);
        quantityLayout.addView(quantity);
        quantityLayout.addView(addButton);


//        View view = new View(getActivity().getApplicationContext());
//        LinearLayout.LayoutParams separatorParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, 4);
//
//
//        separatorParams.setMargins(8, 0, 8, 4);
//        view.setLayoutParams(separatorParams);
//        view.setBackgroundColor(Color.parseColor("#3E2723"));
//        block.addView(view);
        block.addView(quantityLayout);

        listOfProducts.addView(block);

    }


}
