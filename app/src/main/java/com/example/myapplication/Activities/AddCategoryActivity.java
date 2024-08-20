package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.Models.Category;
import com.example.myapplication.R;

public class AddCategoryActivity extends AppCompatActivity {
    CommerceDBHelper database;
    Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        EditText categoryName=(EditText)findViewById(R.id.editTextTextCategoryName);
        Button  addButton=(Button)findViewById(R.id.button_addnew_cat);
        database=new CommerceDBHelper(getApplicationContext());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoryName.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Enter valid category",Toast.LENGTH_SHORT).show();
                }
                else {
                    category=new Category(categoryName.getText().toString());
                    database.newCategory(category);
                    categoryName.setText("");
                    Toast.makeText(getApplicationContext(),"New category added",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}