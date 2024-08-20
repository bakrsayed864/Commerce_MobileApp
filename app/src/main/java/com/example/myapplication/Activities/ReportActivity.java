package com.example.myapplication.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adapters.ReportAdapter;
import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.Models.ReportInfo;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    ArrayList<ReportInfo> data=new ArrayList<ReportInfo>();
    ListView reportList;
    EditText dateText;
    Spinner usersSpinner;
    ArrayAdapter spinnAdapter;
    Button generat_btn;
    ReportAdapter adapter;

    CommerceDBHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        database=new CommerceDBHelper(getApplicationContext());
        dateText=(EditText)findViewById(R.id.edit_txt_date);
        usersSpinner=(Spinner)findViewById(R.id.users_spinner);
        generat_btn=(Button)findViewById(R.id.generate_report_btn);
        getUsers();
        generat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(dateText.getText().toString().isEmpty()) && usersSpinner.getSelectedItem().toString().equals("All")){
                    System.out.println("hellooooo3");
                    getData(dateText.getText().toString());
                    reportList=(ListView)findViewById(R.id.report_listview);
                    adapter=new ReportAdapter(getApplicationContext(),data);
                    reportList.setAdapter(adapter);
                }
                else if(!(dateText.getText().toString().isEmpty()) && !(usersSpinner.getSelectedItem().toString().equals("All"))){
                    System.out.println("hellooooo2");
                    getSpecificUserData(dateText.getText().toString(),usersSpinner.getSelectedItem().toString());
                    reportList=(ListView)findViewById(R.id.report_listview);
                    adapter=new ReportAdapter(getApplicationContext(),data);
                    reportList.setAdapter(adapter);
                }
                else if(dateText.getText().toString().isEmpty()){
                    System.out.println("hellooooo");
                    getDataWithNoDate(usersSpinner.getSelectedItem().toString());
                    reportList=(ListView)findViewById(R.id.report_listview);
                    adapter=new ReportAdapter(getApplicationContext(),data);
                    reportList.setAdapter(adapter);
                }
                //else if(d)
                if(data.size()<1)
                    Toast.makeText(getApplicationContext(), "there are no orders in this date\nmake sure that you enter date in this format\nyear-month-day in numbers", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void getData(String date){
        data.clear();;
        Cursor cursor=database.AllUsersReportInfo(date);
        ReportInfo r_info;
        if(cursor!=null){
            while (!cursor.isAfterLast()){
                r_info=new ReportInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getInt(5));
                data.add(r_info);
                cursor.moveToNext();
            }
        }
    }
    private void getSpecificUserData(String date,String name){
        data.clear();;
        Cursor cursor=database.SpecificUserReportInfo(date,name);
        ReportInfo r_info;
        if(cursor!=null){
            while (!cursor.isAfterLast()){
                r_info=new ReportInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getInt(5));
                data.add(r_info);
                cursor.moveToNext();
            }
        }
    }
    public void getDataWithNoDate(String name){
        data.clear();;
        Cursor cursor=database.AllUsersReportInfoWithNoDate(name);
        ReportInfo r_info;
        if(cursor!=null){
            while (!cursor.isAfterLast()){
                r_info=new ReportInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getInt(5));
                data.add(r_info);
                cursor.moveToNext();
            }
        }
    }
    private void getUsers(){
        Cursor cursor=database.allUsers();
        List<String> usersName=new ArrayList<String>();
        usersName.add("All");
        if(cursor!=null){
            while (!cursor.isAfterLast()){
                usersName.add(cursor.getString(0));
                cursor.moveToNext();
            }
            spinnAdapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,usersName);
            usersSpinner.setAdapter(spinnAdapter);
        }
    }
}