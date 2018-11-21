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

public class AddFragment extends Fragment {

    LinearLayout addProduct;
    AppDatabase db;
    ProductDao productDao;
    SyncProductDao syncProductDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fragment, container, false);
        addProduct = view.findViewById(R.id.add_product);

        db = Database.getInstance(getActivity().getApplicationContext()).getAppDatabase();
        productDao = db.productDao();
        syncProductDao = new SyncProductDao(productDao);

        Button button = view.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) addProduct.findViewById(R.id.name)).getText().toString();
                String units = ((EditText) addProduct.findViewById(R.id.units)).getText().toString();
                Double amount;
                try {
                    amount = Double.parseDouble(((EditText) addProduct.findViewById(R.id.start_amount))
                            .getText().toString());
                }
                catch (Exception e) {
                    amount = 0.0;
                }
                if(!name.isEmpty() && !units.isEmpty()) {

                    Product product = new Product(name, amount, units);

                    syncProductDao.insertAll(product);

                    getFragmentManager().beginTransaction().replace(R.id.fragment, new ListFragment()).commit();
                }
            }
        });
        return view;
    }
}
