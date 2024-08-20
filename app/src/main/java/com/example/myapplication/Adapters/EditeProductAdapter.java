package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Activities.EditeProductActivity;
import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.Models.Product;
import com.example.myapplication.R;

import java.util.ArrayList;

public class EditeProductAdapter extends BaseAdapter {
    private ArrayList<Product> data;
    private Context context;
    CommerceDBHelper database;
    TextView prod_name_txt;
    ImageView image;
    Button edite;
    Button delete;

    public EditeProductAdapter(Context context, ArrayList<Product> objects) {
        data = objects;
        this.context = context;
        //sharedPreferences=getContext().getSharedPreferences("cart",Context.MODE_PRIVATE);
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
                    inflate(R.layout.editeproduct_item, parent, false);
        }
        prod_name_txt = (TextView) convertView.findViewById(R.id.textView11);
        prod_name_txt.setText(data.get(position).getName());
        database = new CommerceDBHelper(this.context);
        delete=(Button)convertView.findViewById(R.id.button);
        edite=(Button)convertView.findViewById(R.id.button3);
        image=(ImageView)convertView.findViewById(R.id.delete_edit_imageView);
        if (data.get(position).getImage() != null) {
            byte[] image_byte = data.get(position).getImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
            image.setImageBitmap(bmp);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteProduct(data.get(position).getId());
                data.remove(getItem(position));
                notifyDataSetChanged();
            }
        });
        edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), EditeProductActivity.class);
                intent.putExtra("product_id", data.get(position).getId());
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
