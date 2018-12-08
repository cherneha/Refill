package com.example.stacy.refill;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.stacy.refill.DBManager.AppDatabase;
import com.example.stacy.refill.DBManager.Database;
import com.example.stacy.refill.DBManager.ListItemDao;
import com.example.stacy.refill.DBManager.SyncListItemDao;

import java.util.List;

public class ShoppingListFragment extends Fragment {

    LinearLayout listOfListItems;
    AppDatabase db;
    ListItemDao listItemDao;
    SyncListItemDao syncListItemDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shopping_list_fragment, container, false);
        listOfListItems = view.findViewById(R.id.shopping_list);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linLayoutParam.gravity = Gravity.CENTER_HORIZONTAL;

        db = Database.getInstance(getActivity().getApplicationContext()).getAppDatabase();
        listItemDao = db.listItemDao();
        syncListItemDao = new SyncListItemDao(listItemDao);

        List<ListItem> listItems = syncListItemDao.getAll();
        LayoutGenerator layoutGenerator = new LayoutGenerator(this.getActivity());

        if(listItems != null) {
            for (int i = 0; i < listItems.size(); i++) {
                System.out.println(listItems.get(i).getName());
                layoutGenerator.addBlock(listItems.get(i).getName(), String.valueOf(listItems.get(i).getQuantity()),
                        listItems.get(i).getUnits(), listOfListItems);
            }
        }

        FloatingActionButton addButton = view.findViewById(R.id.add_fab);
        if (Build.VERSION.SDK_INT > 15) {
            Drawable addIcon = getActivity().getResources().getDrawable(R.drawable.add2);
            addIcon.setBounds(0, 0, 50, 50);
            addButton.setImageDrawable(addIcon);
            addButton.setBackgroundColor(Color.parseColor("#f09090"));

        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment, new AddItemFragment()).commit();
            }
        });
        return view;
    }

//    public void addBlock(final String inputName, String productQuantity, String units) {
//
//        final LinearLayout block = new LinearLayout(getActivity().getApplicationContext());
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        if (Build.VERSION.SDK_INT > 15) {
//            GradientDrawable shape = new GradientDrawable();
//            shape.setCornerRadius(12);
//            shape.setColor(Color.parseColor("#f0cece"));
//            block.setBackground(shape);
//        } else {
//            block.setBackgroundColor(Color.parseColor("#e4a7a7"));
//        }
//
//        block.setOrientation(LinearLayout.VERTICAL);
//        params.setMargins(8, 8, 8, 0);
//        block.setLayoutParams(params);
//
//        final LinearLayout nameLayout = new LinearLayout(getActivity().getApplicationContext());
//        nameLayout.setOrientation(LinearLayout.HORIZONTAL);
//        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        if (Build.VERSION.SDK_INT > 15) {
//            GradientDrawable shape = new GradientDrawable();
//            shape.setCornerRadius(12);
//            shape.setColor(Color.parseColor("#e4a7a7"));
//            nameLayout.setBackground(shape);
//        } else {
//            nameLayout.setBackgroundColor(Color.parseColor("#e4a7a7"));
//        }
//
//        final ImageButton editButton = new ImageButton(getActivity().getApplicationContext());
//        if (Build.VERSION.SDK_INT > 15) {
//            Drawable editIcon = getActivity().getResources().getDrawable(R.drawable.edit);
//            editIcon.setBounds(0, 0, 50, 50);
//            editButton.setBackground(editIcon);
//
//        } else {
//            editButton.setBackgroundColor(Color.parseColor("#e4a7a7"));
//        }
//
//        ImageButton deleteButton = new ImageButton(getActivity().getApplicationContext());
//        if (Build.VERSION.SDK_INT > 23) {
//            Drawable deleteIcon = getActivity().getResources().getDrawable(R.drawable.delete);
//            deleteIcon.setBounds(0, 0, 50, 50);
//            deleteButton.setBackground(deleteIcon);
//        } else {
//            deleteButton.setBackgroundColor(Color.parseColor("#FF9800"));
//        }
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                syncListItemDao.delete(syncListItemDao.findByName(inputName));
//                listOfListItems.removeView(block);
//                return;
//            }
//        });
//        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(80, 80);
//        buttonParams.setMargins(0, 0, 0, 0);
//        deleteButton.setLayoutParams(buttonParams);
//
//        LinearLayout.LayoutParams buttonParams2 = new LinearLayout.LayoutParams(80, 80);
//        buttonParams2.setMargins(650, 0, 0, 0);
//
//
//        final TextView text = new TextView(getActivity().getApplicationContext());
//        text.setText(inputName);
//        text.setPadding(8, 8, 8, 8);
//        text.setTextSize(20);
//        nameLayout.setLayoutParams(params2);
//        text.setGravity(Gravity.LEFT);
//        nameLayout.addView(text);
//        nameLayout.addView(editButton);
//        nameLayout.addView(deleteButton);
//        block.addView(nameLayout);
//        editButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//
//                alert.setTitle("Rename Product");
//                alert.setMessage(inputName);
//
//                final EditText input = new EditText(getContext());
//                alert.setView(input);
//
//                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        ListItem edited = syncListItemDao.findByName(inputName);
//                        edited.setName(String.valueOf(input.getText()));
//                        syncListItemDao.updateListItem(edited);
//
//                        text.setText(String.valueOf(input.getText()));
//                    }
//                });
//
//                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        // Canceled.
//                    }
//                });
//
//                alert.show();
//            }
//        });
//
//
//        LinearLayout quantityLayout = new LinearLayout(getActivity().getApplicationContext());
//        quantityLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//
//        final TextView quantity = new TextView(getActivity().getApplicationContext());
//        quantity.setText(productQuantity);
//        quantity.setTextSize(30);
//        quantity.setPadding(16, 8, 16, 0);
//        quantityLayout.addView(quantity);
//
//        final TextView unit = new TextView(getActivity().getApplicationContext());
//        unit.setText(units);
//        unit.setTextSize(30);
//        unit.setPadding(32, 8, 16, 0);
//        quantityLayout.addView(unit);
//
//        ImageButton addButton = new ImageButton(getActivity().getApplicationContext());
//        if (Build.VERSION.SDK_INT > 15) {
//            Drawable cartIcon = getActivity().getResources().getDrawable(R.drawable.add);
//            cartIcon.setBounds(0, 0, 50, 50);
//            addButton.setBackground(cartIcon);
//        } else {
//            addButton.setBackgroundColor(Color.parseColor("#FF9800"));
//        }
//        addButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//
//                alert.setTitle("Add");
//                alert.setMessage("How much?");
//
//                final EditText input = new EditText(getContext());
//                alert.setView(input);
//
//                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        ListItem edited = syncListItemDao.findByName(inputName);
//                        double newValue = Double.valueOf(String.valueOf(input.getText()))
//                                + edited.getQuantity();
//                        edited.setQuantity(newValue);
//                        syncListItemDao.updateListItem(edited);
//
//                        quantity.setText(String.valueOf(newValue));
//                    }
//                });
//
//                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        // Canceled.
//                    }
//                });
//
//                alert.show();
//            }
//        });
//        LinearLayout.LayoutParams addButtonParams = new LinearLayout.LayoutParams(80, 80);
//        addButtonParams.setMargins(0, 20, 0, 0);
//        addButton.setLayoutParams(addButtonParams);
//        quantityLayout.addView(addButton);
//
//        block.addView(quantityLayout);
//
//        listOfListItems.addView(block);
//
//    }
}
