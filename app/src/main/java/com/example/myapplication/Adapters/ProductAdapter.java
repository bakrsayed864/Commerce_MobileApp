package com.example.myapplication.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.Models.Product;
import com.example.myapplication.Models.UnconfirmedProducts;
import com.example.myapplication.R;

import java.util.ArrayList;
public class ProductAdapter extends BaseAdapter {
    private ArrayList<Product>data;
    CommerceDBHelper database;
    private Context context;
    int userId;
    public ProductAdapter(Context context, ArrayList<Product> objects,int id) {
        this.data=objects;
       userId=id;
        this.context=context;
      //getSelectedProducts();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.product_item, parent, false);
        }
            ImageView prod_image=convertView.findViewById(R.id.product_image);
            TextView price_txt=convertView.findViewById(R.id.produt_price_txt);
            TextView name_txt=convertView.findViewById(R.id.product_name_txt);
            Button add_btn=convertView.findViewById(R.id.Add_product_btn);
            database=new CommerceDBHelper(this.context);
             add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UnconfirmedProducts product=new UnconfirmedProducts(data.get(position).getId(),1,data.get(position).getName(),data.get(position).getImage(),data.get(position).getPrice(),userId);
                    database.insertUnconfirmedProduct(product);
                    Toast.makeText(context, "product added", Toast.LENGTH_SHORT).show();
                }
            });
            if (data.get(position).getImage() != null) {
                byte[] image_byte = data.get(position).getImage();
                Bitmap bmp = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
                prod_image.setImageBitmap(bmp);
            }
            name_txt.setText(data.get(position).getName());
            price_txt.setText(data.get(position).getPrice()+" $");
            return convertView;
    }
}
