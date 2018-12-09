package com.example.stacy.refill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.stacy.refill.DBManager.AppDatabase;
import com.example.stacy.refill.DBManager.Database;
import com.example.stacy.refill.DBManager.ProductDao;
import com.example.stacy.refill.DBManager.SyncProductDao;

import java.util.List;

public class HomeFragment extends Fragment {

    LinearLayout listOfProductsToUpdate;
    AppDatabase db;
    ProductDao productDao;
    SyncProductDao syncProductDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        listOfProductsToUpdate = view.findViewById(R.id.product_list_to_update);

        db = Database.getInstance(getActivity().getApplicationContext()).getAppDatabase();
        productDao = db.productDao();
        syncProductDao = new SyncProductDao(productDao);

        List<Product> products = syncProductDao.getAll();
        LayoutGenerator<Product> layoutGenerator = new LayoutGenerator<Product>(this.getActivity(), syncProductDao);

        if (products != null) {
            for (int i = 0; i < products.size(); i++) {
                if(products.get(i).isUpdateNeeded()) {
                    System.out.println(products.get(i).getName());
                    layoutGenerator.addBlock(products.get(i).getName(), String.valueOf(products.get(i).getCurrentQuantity()),
                            products.get(i).getUnits(), listOfProductsToUpdate, true);
                }
            }
        }

        return view;

    }
}
