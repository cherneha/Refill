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
        LayoutGenerator layoutGenerator = new LayoutGenerator(this.getActivity(), syncListItemDao);

        if (listItems != null) {
            for (int i = 0; i < listItems.size(); i++) {
                System.out.println(listItems.get(i).getName());
                layoutGenerator.addBlock(listItems.get(i).getName(), String.valueOf(listItems.get(i).getQuantity()),
                        listItems.get(i).getUnits(), listOfListItems, false);
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
}