package com.example.myapplication.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.Models.Customer;
import com.example.myapplication.R;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener setListener;
    String date;
    CommerceDBHelper userDatabase;
    SharedPreferences sharedPref;
    //SharedPreferences.Editor editor=sharedPref.edit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref=getSharedPreferences("remember_me",MODE_PRIVATE);
        boolean login=sharedPref.getBoolean("isloged",false);
        int id=sharedPref.getInt("userid",0);
        if(login){
            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
            intent.putExtra("user_id",id);
            startActivity(intent);
            finish();;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        TextView name=(TextView)findViewById(R.id.name_id);
        TextView username=(TextView)findViewById(R.id.user_name_id);
        TextView password=(TextView)findViewById(R.id.password_id);
        TextView gender=(TextView)findViewById(R.id.gender_id);
        TextView birthdate=(TextView) findViewById(R.id.date_id);
        TextView job=(TextView)findViewById(R.id.job_id);
        Button signButton=(Button)findViewById(R.id.sign_button_id);
        Button loginButton=(Button)findViewById(R.id.show_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        userDatabase=new CommerceDBHelper(getApplicationContext());
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DatePickerDialog dpd = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Dialog,setListener,year,month,day);
                    dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dpd.show();
            }
        });
        setListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfmonth) {
                month = month+1;
                date = dayOfmonth+"/"+month+"/"+year;
                birthdate.setText(date);
            }
        };

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString( ).isEmpty()||password.getText().toString().isEmpty()||name.getText().toString().isEmpty()||job.getText().toString().isEmpty()||birthdate.getText().toString().isEmpty()||gender.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Invalid data\nplease re enter valid data",Toast.LENGTH_LONG).show();
                }
                else {
                Customer customer=new Customer(name.getText().toString(),username.getText().toString(),password.getText().toString(),gender.getText().toString(),date,job.getText().toString());
              if(userDatabase.checkUserExist(username.getText().toString())){
                    Toast.makeText(getApplicationContext(),"user name is exist,try another",Toast.LENGTH_SHORT).show();
                }
                else {
//                    if(gender.getText().toString()!="male"||gender.getText().toString()!="female"){
//                        Toast.makeText(getApplicationContext(),"invalid gender\npleas enter male or female",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
                        userDatabase.newUser(customer);
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        name.setText("");
                        username.setText("");
                        password.setText("");
                        gender.setText("");
                        birthdate.setText("");
                        job.setText("");
                        Toast.makeText(getApplicationContext(),"Signed successfully",Toast.LENGTH_SHORT).show();
                        finish();
                }
                }
            }
        });
    }
    public void ChecLoged(){

    }
}