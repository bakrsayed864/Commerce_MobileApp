package com.example.myapplication.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.Models.Category;
import com.example.myapplication.Models.Product;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddproductActivity extends AppCompatActivity {
    ImageView productimage;
    EditText productname, productprice, productquantity;
    Spinner proCategory;
    ArrayAdapter adapter;
    Button upload_btn;
    TextView reset_btn;
    CommerceDBHelper database;
    final static int GALLERY_REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        productimage = (ImageView) findViewById(R.id.imageView);
         productname = (EditText) findViewById(R.id.prod_name_txt);
         productprice = (EditText) findViewById(R.id.prod_price_txt);
         productquantity = (EditText) findViewById(R.id.prod_quantity_txt);
         proCategory = (Spinner) findViewById(R.id.spinner2);
         upload_btn = (Button) findViewById(R.id.add_btn);
        database=new CommerceDBHelper(getApplicationContext());
         //addCategory();
         getAllcategory();
         upload_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String name=productname.getText().toString().trim();
                 String price=productprice.getText().toString().trim();
                 String quantity=productquantity.getText().toString().trim();
                 int cat_id=Integer.parseInt(database.getCatId(proCategory.getSelectedItem().toString()));
                 byte [] image= convertToByteImage(productimage);
                 if(!name.equals("")||!price.equals("")||!quantity.equals(""))
                 {
                     Product product = new Product(Integer.parseInt(quantity), cat_id,name,image,Double.parseDouble(price));
                     database.newProduct(product);
                     productimage.setImageResource(R.drawable.default_image);
                     productname.setText("");
                     productprice.setText("");
                     productquantity.setText("");
                     Toast.makeText(getApplicationContext(), "product added", Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                     Toast.makeText(getApplicationContext(), "Check data again", Toast.LENGTH_SHORT).show();
                 }
                 Cursor mydata=database.getAllProducts();
                 while (!mydata.isAfterLast()){
                     System.out.println(mydata.getString(1));
                     mydata.moveToNext();
                 }
             }
         });
        productimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                productimage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    protected static byte[] convertToByteImage(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    protected void getAllcategory(){

        List<String> cate=new ArrayList<>();
        Cursor cursor=database.getCategory();
        if (cursor!=null){
            while (!cursor.isAfterLast()){
                cate.add(cursor.getString(1));
                cursor.moveToNext();
            }
            adapter=new ArrayAdapter(this,R.layout.spinner_item,cate);
            proCategory.setAdapter(adapter);
        }
    }
    protected void uploadProduct(){

        String name=productname.getText().toString();
        String price=productprice.getText().toString();
        String quan=productquantity.getText().toString();
        int catid=Integer.parseInt(database.getCatId(proCategory.getSelectedItem().toString()));
        byte [] image= convertToByteImage(productimage);

        if(!name.equals("")||!price.equals("")||!quan.equals(""))
        {
            Product product = new Product(Integer.parseInt(quan), catid,name,image,Double.parseDouble(price));
            database.newProduct(product);


            productimage.setImageResource(R.drawable.ic_launcher_background);
            productname.setText("");
            productprice.setText("");
            productquantity.setText("");


            Toast.makeText(this, "product added", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Check data again", Toast.LENGTH_SHORT).show();
        }
    }
    protected void addCategory(){
        database.newCategory(new Category("fashion"));
        database.newCategory(new Category("cars"));
        database.newCategory(new Category("sport"));
    }
}
