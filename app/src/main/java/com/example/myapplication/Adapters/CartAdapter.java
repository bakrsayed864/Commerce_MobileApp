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
import com.example.myapplication.Models.UnconfirmedProducts;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    private ArrayList<UnconfirmedProducts> data;
    private Context context;
    private ArrayList<Integer> list_id = new ArrayList<>();
    CommerceDBHelper database;
    public CartAdapter(Context context, ArrayList<UnconfirmedProducts> data, double cost) {
        this.data = data;
        this.context = context;
        //getProductsids();
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
                    inflate(R.layout.cart_item, parent, false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(),data.get(position).getId()+"",Toast.LENGTH_SHORT).show();
            }
        });
        UnconfirmedProducts currentItem = (UnconfirmedProducts) getItem(position);
        ImageView product_image = convertView.findViewById(R.id.imageView2);
        TextView product_name = convertView.findViewById(R.id.textView17);
        TextView product_price = convertView.findViewById(R.id.textView18);
        TextView product_quantity= convertView.findViewById(R.id.textView19);
        Button remove_btn=convertView.findViewById(R.id.remove_btn);
        TextView add_quan = convertView.findViewById(R.id.textView20);
        TextView dec_quan = convertView.findViewById(R.id.textView21);
        database=new CommerceDBHelper(this.context);
        product_quantity.setText(String.valueOf(data.get(position).getQuantity()));
        add_quan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quan = Integer.parseInt(product_quantity.getText().toString());
                quan++;
                boolean check=database.updateQuantity(quan,data.get(position).getId());
                if(check){
                    product_quantity.setText(String.valueOf(quan));
                }
                else
                    Toast.makeText(context,"Quantity exceeded quantity in store",Toast.LENGTH_SHORT).show();
            }
        });
        dec_quan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quan = Integer.parseInt(product_quantity.getText().toString());
                    quan--;
                product_quantity.setText(String.valueOf(quan));
                database.updateQuantity(quan,data.get(position).getId());
            }
        });
        if (data.get(position).getImage() != null) {
            byte[] image_byte = data.get(position).getImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
            product_image.setImageBitmap(bmp);
        }
        product_name.setText(data.get(position).getName());
        product_price.setText(data.get(position).getPrice() + " $");
        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.removeUnconfirmedProduct(data.get(position).getId());
                data.remove(currentItem);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
