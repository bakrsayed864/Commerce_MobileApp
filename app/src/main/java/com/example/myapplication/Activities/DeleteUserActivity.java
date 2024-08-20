package com.example.myapplication.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class DeleteUserActivity extends AppCompatActivity {
    CommerceDBHelper database;
    Spinner useresSpinner;
    ArrayAdapter spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        database=new CommerceDBHelper(getApplicationContext());
        useresSpinner=(Spinner) findViewById(R.id.spinner);
        Button deleteBtn=(Button) findViewById(R.id.button4);
        getUsers();
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.DeleteAccount(useresSpinner.getSelectedItem().toString());
                Toast.makeText(getApplicationContext(),"User "+useresSpinner.getSelectedItem().toString()+" deleted",Toast.LENGTH_SHORT).show();
                getUsers();
            }
        });
    }
    private void getUsers(){
        Cursor cursor=database.allUsers();
        List<String> usersName=new ArrayList<String>();
        if(cursor!=null){
            while (!cursor.isAfterLast()){
                usersName.add(cursor.getString(0));
                cursor.moveToNext();
            }
            spinnerAdapter=new ArrayAdapter(getApplicationContext(), R.layout.spinner_item,usersName);
            useresSpinner.setAdapter(spinnerAdapter);
        }
    }
}