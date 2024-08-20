package com.example.myapplication.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Adapters.ProductAdapter;
import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.Models.Product;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ListView listView;
    private ArrayList<Product> products = new ArrayList<Product>();
    private ProductAdapter adapter;
    private CommerceDBHelper database;
    Spinner cat_spinner;
    ArrayAdapter Cat_adapter;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        int userId=getActivity().getIntent().getExtras().getInt("user_id");
        listView = (ListView) view.findViewById(R.id.products_list_view);
        database = new CommerceDBHelper(getContext());
        cat_spinner = view.findViewById(R.id.home_cat_spinnrer);
        cat_spinner.setAdapter(Cat_adapter);
        adapter = new ProductAdapter(getContext(), products, userId);
        listView.setAdapter(adapter);
        getAllProduct();
        getAllcategory();

        cat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cat_spinner.getSelectedItem().toString().equals("All")) {
                    getAllProduct();
                    adapter = new ProductAdapter(getContext(), products, userId);
                    listView.setAdapter(adapter);
                } else {
                    searchByCategory(cat_spinner.getSelectedItem().toString());
                    adapter = new ProductAdapter(getContext(), products, userId);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public void getAllProduct() {
        products.clear();
        Cursor cursor = database.getAllProducts();
        Product product;
        if (cursor != null) {
            while (!cursor.isAfterLast()) {
                //0prodid,1 prodname,2 price,3quantity,4 cat_id,5 image,
                product = new Product(cursor.getInt(3),cursor.getInt(4),cursor.getString(1),cursor.getBlob(5),cursor.getDouble(2));
                product.setId(cursor.getInt(0));
                products.add(product);
                cursor.moveToNext();
            }
        }
//        for (int i = 0; i < products.size(); i++) {
//            System.out.println(products.get(i).getName());
//        }
    }

    protected void getAllcategory() {
        Cursor cursor = database.getCategory();
        List<String> cate = new ArrayList<>();
        cate.add("All");
        if (cursor != null) {
            while (!cursor.isAfterLast()) {
                cate.add(cursor.getString(1));
                cursor.moveToNext();
            }
            Cat_adapter = new ArrayAdapter(getContext(), R.layout.spinner_item, cate);
            cat_spinner.setAdapter(Cat_adapter);
        }
    }

    private void searchByCategory(String name) {
        products.clear();
        ArrayList<Product> filterlist = new ArrayList<>();
        String cat_id = database.getCatId(name);
        Cursor cursor = database.getProductsByCategory(Integer.parseInt(cat_id));
        Product product;
        if (cursor != null) {
            while (!cursor.isAfterLast()) {
                product = new Product(Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4)),
                        cursor.getString(1), cursor.getBlob(5),
                        Double.parseDouble(cursor.getString(2)));
                product.setId(Integer.parseInt(cursor.getString(0)));
                products.add(product);
                cursor.moveToNext();
            }
        }
    }
}