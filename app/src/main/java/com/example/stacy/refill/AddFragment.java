package com.example.stacy.refill;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.stacy.refill.DBManager.AppDatabase;
import com.example.stacy.refill.DBManager.Database;
import com.example.stacy.refill.DBManager.ProductDao;
import com.example.stacy.refill.DBManager.SyncProductDao;

public class AddFragment extends Fragment {

    LinearLayout addProduct;
    LinearLayout buttonsLayout;

    AppDatabase db;
    ProductDao productDao;
    SyncProductDao syncProductDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fragment, container, false);
        addProduct = view.findViewById(R.id.add_product);
        buttonsLayout = addProduct.findViewById(R.id.buttons_layout);

        db = Database.getInstance(getContext()).getAppDatabase();
        productDao = db.productDao();
        syncProductDao = new SyncProductDao(productDao);

        final Button addButton = buttonsLayout.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = ((EditText) addProduct.findViewById(R.id.name)).getText().toString();
                final Product existingProduct = syncProductDao.findByName(name);
                if (existingProduct != null) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    alert.setTitle("You already have a product with same name.");
                    alert.setMessage("Sure you want to replace it?");

                    alert.setPositiveButton("Replace", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            syncProductDao.delete(existingProduct);
                            String units = ((EditText) addProduct.findViewById(R.id.units)).getText().toString();
                            Double amount;
                            try {
                                amount = Double.parseDouble(((EditText) addProduct.findViewById(R.id.start_amount))
                                        .getText().toString());
                            } catch (Exception e) {
                                amount = 0.0;
                            }
                            if (!name.isEmpty() && !units.isEmpty()) {
                                Product product = new Product(name, amount, units);
                                int status = syncProductDao.insertAll(product);
                                getFragmentManager().beginTransaction().replace(R.id.fragment, new ListFragment()).commit();

                            }
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            getFragmentManager().beginTransaction().replace(R.id.fragment, new AddFragment()).commit();
                        }
                    });

                    alert.show();
                } else {
                    String units = ((EditText) addProduct.findViewById(R.id.units)).getText().toString();
                    Double amount;
                    try {
                        amount = Double.parseDouble(((EditText) addProduct.findViewById(R.id.start_amount))
                                .getText().toString());
                    } catch (Exception e) {
                        amount = 0.0;
                    }
                    if (!name.isEmpty() && !units.isEmpty()) {
                        Product product = new Product(name, amount, units);
                        int status = syncProductDao.insertAll(product);
                        getFragmentManager().beginTransaction().replace(R.id.fragment, new ListFragment()).commit();

                    }
                }
            }
        });

        Button backButton = buttonsLayout.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment, new ListFragment()).commit();
            }
        });

        return view;
    }
}
