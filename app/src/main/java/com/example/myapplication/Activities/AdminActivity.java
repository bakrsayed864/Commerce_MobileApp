package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button add_product=(Button) findViewById(R.id.add_prod_btn);
        Button add_category=(Button) findViewById(R.id.add_cat_btn);
        Button edit_delete=(Button) findViewById(R.id.edit_delete_btn);
        Button delet_category=(Button) findViewById(R.id.delete_cat_btn);
        Button reportButton=(Button) findViewById(R.id.report_btn);
        Button chartButton=(Button) findViewById(R.id.chart_button);
        Button deleteAccountButton=(Button) findViewById(R.id.delete_acccount_button);
        delet_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,DeleteCategoryActivity.class);
                startActivity(intent);
            }
        });
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddproductActivity.class);
                startActivity(intent);
            }
        });
        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,AddCategoryActivity.class);
                startActivity(intent);
            }
        });
        edit_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,EditDeleteActivity.class);
                startActivity(intent);
            }
        });
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,ReportActivity.class);
                startActivity(intent);
            }
        });
        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,ChartActivity.class);
                startActivity(intent);
            }
        });
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,DeleteUserActivity.class);
                startActivity(intent);
            }
        });
    }
}