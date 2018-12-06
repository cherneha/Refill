package com.example.stacy.refill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddItemFragment extends Fragment {

    LinearLayout addListItem;
    LinearLayout buttonsLayout;

    AppDatabase db;
    ListItemDao listItemDao;
    SyncListItemDao syncListItemDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fragment, container, false);
        addListItem = view.findViewById(R.id.add_product);
        buttonsLayout = addListItem.findViewById(R.id.buttons_layout);

        db = Database.getInstance(getActivity().getApplicationContext()).getAppDatabase();
        listItemDao = db.listItemDao();
        syncListItemDao = new SyncListItemDao(listItemDao);

        Button addButton = buttonsLayout.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) addListItem.findViewById(R.id.name)).getText().toString();
                String units = ((EditText) addListItem.findViewById(R.id.units)).getText().toString();
                Double amount;
                try {
                    amount = Double.parseDouble(((EditText) addListItem.findViewById(R.id.start_amount))
                            .getText().toString());
                } catch (Exception e) {
                    amount = 0.0;
                }
                if (!name.isEmpty() && !units.isEmpty()) {

                    ListItem listItem = new ListItem(name, amount, units);

                    syncListItemDao.insertAll(listItem);

                    getFragmentManager().beginTransaction().replace(R.id.fragment, new ShoppingListFragment()).commit();
                }
            }
        });

        Button backButton = buttonsLayout.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment, new ShoppingListFragment()).commit();
            }
        });

        return view;
    }
}