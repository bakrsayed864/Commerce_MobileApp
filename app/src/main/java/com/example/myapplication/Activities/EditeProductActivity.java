package com.example.myapplication.Activities;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.R;

public class EditeProductActivity extends AppCompatActivity {
    CommerceDBHelper database;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_product);
        int prodId = getIntent().getIntExtra("product_id", -1);
        EditText prodname = (EditText) findViewById(R.id.prod_to_edit_nametxt);
        EditText prodprice = (EditText) findViewById(R.id.prod_to_edit_pricetxt);
        EditText prodquant = (EditText) findViewById(R.id.prod_to_edit_quanttxt);
        ImageView image=(ImageView)findViewById(R.id.imageView3);
        Button update_btn=(Button)findViewById(R.id.update_product_btn);
        database = new CommerceDBHelper(getApplicationContext());
        cursor = database.getProductById(String.valueOf(prodId));
        prodname.setText(cursor.getString(1));
        prodprice.setText(cursor.getString(2));
        prodquant.setText(cursor.getString(3));
        if (cursor.getBlob(5) != null) {
            byte[] image_byte = cursor.getBlob(5);
            Bitmap bmp = BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
            image.setImageBitmap(bmp);
        }
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.editProduct(prodId,prodname.getText().toString(),Double.parseDouble(prodprice.getText().toString()),Integer.parseInt(prodquant.getText().toString()));
                Toast.makeText(getApplicationContext(),"product updated",Toast.LENGTH_SHORT).show();
                finishActivity(0);
            }
        });

    }
}