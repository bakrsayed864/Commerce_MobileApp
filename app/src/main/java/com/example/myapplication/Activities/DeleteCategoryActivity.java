package com.example.myapplication.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class DeleteCategoryActivity extends AppCompatActivity {
    Spinner cat_spinner;
    CommerceDBHelper database;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_categor);
        TextView cat_name = (TextView) findViewById(R.id.textView14);
        EditText new_name = (EditText) findViewById(R.id.new_name_text);
        new_name.setVisibility(View.INVISIBLE);
        Button del_button = (Button) findViewById(R.id.delete_catedory_btn);
        Button edite_button = (Button) findViewById(R.id.edite_button);
        Button update_button = (Button) findViewById(R.id.update_button);
        update_button.setVisibility(View.INVISIBLE);
        cat_spinner = (Spinner) findViewById(R.id.categories_spinner);
        database = new CommerceDBHelper(getApplicationContext());
        getAllcategory();
            cat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cat_name.setText(((TextView) view).getText().toString());
                    new_name.setText(((TextView) view).getText().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            del_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.deleteCategory(Integer.parseInt(database.getCatId(cat_name.getText().toString())));
                    getAllcategory();
                    Toast.makeText(getApplicationContext(), "Category deleted", Toast.LENGTH_SHORT).show();
                }
            });
            edite_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new_name.setVisibility(View.VISIBLE);
                    update_button.setVisibility(View.VISIBLE);
                }
            });
            update_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!new_name.getText().toString().isEmpty()){
                        database.editCategory(Integer.parseInt(database.getCatId(cat_name.getText().toString())),new_name.getText().toString());
                        getAllcategory();
                        Toast.makeText(getApplicationContext(), "Category Updated", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Enter Category name", Toast.LENGTH_SHORT).show();
                }
            });
        }
    protected void getAllcategory() {
        Cursor cursor = database.getCategory();
        List<String> cate = new ArrayList<>();
        if (cursor != null) {
            while (!cursor.isAfterLast()) {
                cate.add(cursor.getString(1));
                cursor.moveToNext();
            }
            adapter = new ArrayAdapter(this, R.layout.spinner_item, cate);
            cat_spinner.setAdapter(adapter);
        }
    }
}