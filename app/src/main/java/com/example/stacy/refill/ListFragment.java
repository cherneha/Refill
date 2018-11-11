package com.example.stacy.refill;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
        for(int i = 0; i < 3; i++){
            addBlock("text " + String.valueOf(i), linLayoutParam, "0");
        }
        return view;
    }

    public void addBlock(String inputName, LinearLayout.LayoutParams params, String productQuantity){
        LinearLayout block = new LinearLayout(getActivity().getApplicationContext());
        block.setOrientation(LinearLayout.VERTICAL);
        EditText text = new EditText(getActivity().getApplicationContext());
        text.setText(inputName);
        block.addView(text, params);

        LinearLayout quantityLayout = new LinearLayout(getActivity().getApplicationContext());
        quantityLayout.setOrientation(LinearLayout.HORIZONTAL);
        Button plus = new Button(getActivity().getApplicationContext());
        plus.setText("+");
        Button minus = new Button(getActivity().getApplicationContext());
        minus.setText("-");
        TextView quantity = new TextView(getActivity().getApplicationContext());
        quantity.setText(productQuantity);
        quantityLayout.addView(minus);
        quantityLayout.addView(quantity);
        quantityLayout.addView(plus);
        block.addView(quantityLayout, params);

//        block.setLayoutParams(params);
        listOfProducts.addView(block);

    }


}
