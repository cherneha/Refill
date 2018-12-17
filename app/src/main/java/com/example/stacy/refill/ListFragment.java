package com.example.stacy.refill;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class ListFragment extends Fragment {
    LinearLayout listOfProducts;
    AppDatabase db;
    ProductDao productDao;
    SyncProductDao syncProductDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);
        listOfProducts = view.findViewById(R.id.product_list);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linLayoutParam.gravity = Gravity.CENTER_HORIZONTAL;

        db = Database.getInstance(getActivity().getApplicationContext()).getAppDatabase();
        productDao = db.productDao();
        syncProductDao = new SyncProductDao(productDao);
        

        List<Product> products = syncProductDao.getAll();
        LayoutGenerator layoutGenerator = new LayoutGenerator(this.getActivity(), syncProductDao);

        if (products != null) {
            for (int i = 0; i < products.size(); i++) {
                System.out.println(products.get(i).getName());
                layoutGenerator.addBlock(products.get(i).getName(), products.get(i).getCurrentQuantity(),
                        products.get(i).getUnits(), listOfProducts, true);
            }
        }

        FloatingActionButton addButton = view.findViewById(R.id.add_fab);
        if (Build.VERSION.SDK_INT > 15) {
            Drawable addIcon = getActivity().getResources().getDrawable(R.drawable.add2);
            addIcon.setBounds(0, 0, 50, 50);
            addButton.setImageDrawable(addIcon);

        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment, new AddFragment()).commit();
            }
        });

        return view;
    }
}