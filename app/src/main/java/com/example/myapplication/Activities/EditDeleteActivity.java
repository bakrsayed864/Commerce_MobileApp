package com.example.myapplication.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adapters.EditeProductAdapter;
import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.Models.Product;
import com.example.myapplication.R;

import java.util.ArrayList;

public class EditDeleteActivity extends AppCompatActivity {
    public ListView myList;
    public ArrayList<Product> products=new ArrayList<Product>();
    public EditeProductAdapter adapter;
    public CommerceDBHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);
        myList=(ListView)findViewById(R.id.edit_delete_list);
        database=new CommerceDBHelper(getApplicationContext());
        getAllProduct();
        adapter=new EditeProductAdapter(getApplicationContext(),products);
        myList.setAdapter(adapter);
        //        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//          //  @Override
////            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
////                //database.deleteProduct(((TextView) view).getText().toString());
////                getAllProduct();
////                adapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,products);
////                myList.setAdapter(adapter);
////                return true;
////            }
//        });
    }



    public void getAllProduct() {
        Cursor cursor = database.getAllProducts();
        products.clear();
        if (cursor != null) {
            while (!cursor.isAfterLast()) {
                Product product=new Product(Integer.parseInt(cursor.getString(3)),
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