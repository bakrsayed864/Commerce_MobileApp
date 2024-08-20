package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    CommerceDBHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        String username= getIntent().getStringExtra("username");
        EditText nametxt=(EditText) findViewById(R.id.name_forgetpass_text);
        EditText userNametxt=(EditText) findViewById(R.id.username_forgetpass_text);
        userNametxt.setText(username);
        TextView password=(TextView) findViewById(R.id.pass_textView27);
        Button getPassButton=(Button)findViewById(R.id.getpass_button);
        database=new CommerceDBHelper(getApplicationContext());
        getPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nametxt.getText().toString()!=""&&userNametxt.getText().toString()!=""){
                    String pass=database.getPassword(nametxt.getText().toString(),userNametxt.getText().toString());
                    if(pass!=""||pass!=null){
                        password.setText(pass);
                    }
                    if(pass=="")
                        Toast.makeText(getApplicationContext(),"Invalid name or user name",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Please enter data",Toast.LENGTH_SHORT).show();
            }
        });

    }
}