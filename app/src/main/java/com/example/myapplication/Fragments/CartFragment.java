package com.example.myapplication.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Adapters.CartAdapter;
import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.Models.Order;
import com.example.myapplication.Models.OrderDetails;
import com.example.myapplication.Models.UnconfirmedProducts;
import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    FusedLocationProviderClient fusedLocationProviderClient;
    private ListView myView;
    private CartAdapter adapter;
    private ArrayList<UnconfirmedProducts> data = new ArrayList<UnconfirmedProducts>();
    private CommerceDBHelper database;
    private SharedPreferences sharedPreferences;
    double productsCost;
    TextView origprice_txt;
    TextView delivery_txt;
    TextView total_text;
    TextView location_txt;
    Button confirm_btn;
    LocalDate date;
    Spinner rat_spinner;
    ArrayAdapter rate_adapter;
    List<Integer> rating_list;
    int userId;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        userId = getActivity().getIntent().getExtras().getInt("user_id");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        Button Refresh_btn = (Button) view.findViewById(R.id.refresh_btn);
        Button get_locabtn = (Button) view.findViewById(R.id.get_loc_btn);
        myView = view.findViewById(R.id.list_cart);
        database = new CommerceDBHelper(getContext());
        origprice_txt = view.findViewById(R.id.orig_price_txt);
        delivery_txt = view.findViewById(R.id.deliv_price_txt);
        total_text = view.findViewById(R.id.total_price_txt);
        location_txt = view.findViewById(R.id.location_txt);
        confirm_btn = view.findViewById(R.id.confirm_btm);
        rat_spinner = view.findViewById(R.id.spinner_rating);
        rating_list = new ArrayList<Integer>();
        rating_list.add(1);
        rating_list.add(2);
        rating_list.add(3);
        rating_list.add(4);
        rating_list.add(5);
        rate_adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, rating_list);
        rat_spinner.setAdapter(rate_adapter);
        get_locabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check permission
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // when granted
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                try {
                                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    // show addreess
                                    // da el address fe string ya bakaaar
                                    String Addresss = addresses.get(0).getAddressLine(0).toString();
                                    location_txt.setText(addresses.get(0).getCountryName() + " / " + addresses.get(0).getLocality());
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + String.valueOf(addresses.get(0).getLatitude()) + ", " + String.valueOf(addresses.get(0).getLongitude())));
                                    startActivity(intent);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        Refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUnconfProducts(userId);
                adapter = new CartAdapter(getContext(), data, productsCost);
                myView.setAdapter(adapter);
                calcCost();
                origprice_txt.setText(String.valueOf(productsCost) + " $");
                total_text.setText(String.valueOf(productsCost + 30) + " $");
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (location_txt.getText().toString() == "")
                    Toast.makeText(getContext(), "set Location or wait for process complete", Toast.LENGTH_SHORT).show();
                else {
                    if (data.size() > 0) {
                        date = LocalDate.now();
                        Order order = new Order(userId, location_txt.getText().toString(), date);
                        database.makeOrder(order);
                        //System.out.println(date.toString());
                        for (int i = 0; i < data.size(); i++) {
                            OrderDetails orderDetails = new OrderDetails(data.get(i).getId(), data.get(i).getQuantity(), Integer.parseInt(rat_spinner.getSelectedItem().toString()));
                            database.insertOrderDetails(orderDetails);
                            database.updateProductQuantity(data.get(i).getId(), data.get(i).getQuantity());
                        }
                        database.cartEmpty();
                        data.clear();
                        Toast.makeText(getContext(), "order Confirmed", Toast.LENGTH_SHORT).show();
                        getUnconfProducts(userId);
                        adapter = new CartAdapter(getContext(), data, productsCost);
                        myView.setAdapter(adapter);
                        calcCost();
                        origprice_txt.setText(String.valueOf(productsCost) + " $");
                        total_text.setText(String.valueOf(productsCost + 30) + " $");
                    } else
                        Toast.makeText(getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
                    //System.out.println(rat_spinner.getSelectedItem().toString());
                    //System.out.println("blaaaaaalallal blaaaa ");
                    total_text.setText(0+"$");
                    location_txt.setText("");
                }
            }

        });
        getUnconfProducts(userId);
        calcCost();
        origprice_txt.setText(String.valueOf(productsCost));
        adapter = new CartAdapter(getContext(), data, productsCost);
        total_text.setText(String.valueOf(productsCost + 30) + " $");
        myView.setAdapter(adapter);
        return view;
    }

    public void calcCost() {
        productsCost = 0;
        for (int i = 0; i < data.size(); i++) {
            productsCost += (data.get(i).getPrice()) * (data.get(i).getQuantity());
        }
    }

    //    private void geIdes() {
////        sharedPreferences = this.getActivity().getSharedPreferences("myCart", Context.MODE_PRIVATE);
////        String ids = sharedPreferences.getString("Unconfirmed_order", null);
////        Cursor cursor=database.getUnconfirmedProductsIds();
////        ArrayList<Integer> idsList=new ArrayList<>();
////        while (!cursor.isAfterLast()){
////            idsList.add(cursor.getInt(0));
////            cursor.moveToNext();
////        }
//        //getUnconfProducts();
//        adapter = new CartAdapter(getContext(), data,productsCost);
//        adapter.setCost(productsCost);
//        myView.setAdapter(adapter);
//        origprice_txt.setText(String.valueOf(adapter.getCost()) + " $");
//        total_text.setText(productsCost+ 20.0 + "$");
////        if (ids != null) {
////            Gson gson = new Gson();
////            ArrayList id = gson.fromJson(ids, ArrayList.class);
////            getProducts(id);
////            adapter = new CartAdapter(getContext(), data,productsCost);
////            myView.setAdapter(adapter);
////            adapter.setCost(productsCost);
////            origprice_txt.setText(String.valueOf(adapter.getCost()) + " $");
////            delivery_txt.setText("20.0 $");
////            total_text.setText(productsCost+ 20.0 + "$");
////        }
//    }
    private void getUnconfProducts(int id) {
        data.clear();
        Cursor cursor = database.getUnconfirmedProducts(id);
        while (!cursor.isAfterLast()) {
            //0 id integer primary key autoincrement,1 proid integer ,2 prodname text not null,3 price real not null,4 quantity integer,5 image blob)");
            UnconfirmedProducts product = new UnconfirmedProducts(cursor.getInt(1), Integer.parseInt(cursor.getString(4)),
                    cursor.getString(2),
                    cursor.getBlob(6),
                    Double.parseDouble(cursor.getString(3)), cursor.getInt(5));
            //product.setId(Integer.parseInt(cursor.getString(0)));
            data.add(product);
            cursor.moveToNext();
            //productsCost+=product.getPrice();
        }
    }
}