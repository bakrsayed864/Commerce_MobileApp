package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LoginActivity extends AppCompatActivity {
    CommerceDBHelper userdatabase;
    FusedLocationProviderClient fusedLocationProviderClient;
    SharedPreferences rememberPref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText username=(EditText) findViewById(R.id.user_name);
        EditText password=(EditText) findViewById(R.id.password);
        TextView forgetText=(TextView) findViewById(R.id.forget_txt);
        Button login=(Button)findViewById(R.id.login_btn);
        Button register=(Button)findViewById(R.id.button2);
        CheckBox checkBox=(CheckBox)findViewById(R.id.checkBox);
        userdatabase=new CommerceDBHelper(getApplicationContext());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin")&&password.getText().toString().equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                else if(userdatabase.logIn(username.getText().toString(),password.getText().toString())){
                    rememberPref=getSharedPreferences("remember_me",MODE_PRIVATE);
                    editor=rememberPref.edit();
                    if(checkBox.isChecked()){
                        editor.putString("username",username.getText().toString());
                        editor.putString("password",password.getText().toString());
                        editor.putInt("userid",userdatabase.getUserId(username.getText().toString()));
                        editor.putBoolean("isloged",true);
                        editor.apply();
                    }
                    if(!checkBox.isChecked()){
                        editor.clear().apply();
                    }
                    Toast.makeText(getApplicationContext(),"Loged successfully",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("user_id", userdatabase.getUserId(username.getText().toString()));
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Account not exist!\nenter valid data or sign up first", Toast.LENGTH_LONG).show();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        forgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
            }
        });
    }
}