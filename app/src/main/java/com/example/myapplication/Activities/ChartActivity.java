package com.example.myapplication.Activities;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
    BarChart barChart;
    PieChart pieChart;
    CommerceDBHelper database;
    ArrayList<String> productsName;
    ArrayList<Integer> productsSales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        database=new CommerceDBHelper(getApplicationContext());
        productsName=new ArrayList<>();
        productsSales=new ArrayList<>();
        Cursor cursor=database.productsSales();
        while (!cursor.isAfterLast()){
            productsName.add(cursor.getString(0));
            productsSales.add(cursor.getInt(1));
            cursor.moveToNext();
        }
        barChart=findViewById(R.id.bar_char);
        pieChart=findViewById(R.id.pie_char);
        //array list
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        ArrayList<PieEntry> pieEntries=new ArrayList<>();
        for (int i=0;i<cursor.getCount();i++){
            BarEntry barEntry=new BarEntry(i,productsSales.get(i));
            barEntries.add(barEntry);
            PieEntry pieEntry=new PieEntry(productsSales.get(i),productsName.get(i));
            pieEntries.add(pieEntry);
        }
        //bar dataset
        PieDataSet pieDataSet=new PieDataSet(pieEntries,"best selle products");
        BarDataSet barDataSet=new BarDataSet(barEntries,"best seller products");
        //barDataSet.setStackLabels(lables);
        barDataSet.setValues(barEntries);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        //hide draw values
        barDataSet.setDrawValues(false);
        pieDataSet.setDrawValues(false);
        //set bar
        barChart.setData(new BarData(barDataSet));
        pieChart.setData(new PieData(pieDataSet));
        //animation
       // barChart.animateY(5000);
        //disc text
        barChart.getDescription().setText("Best seller products");
        barChart.getDescription().setTextColor(Color.BLUE);
        barChart.getDescription().setTextSize(12);
        barChart.getDescription().setPosition((float) 1.0,(float) 2.0);
        pieChart.getDescription().setText("Best seller products");
        pieChart.getDescription().setTextColor(Color.BLUE);
        pieChart.getDescription().setTextSize(12);
        pieChart.getDescription().setPosition((float) 1.0,(float) 2.0);
        pieDataSet.setValueTextColor(Color.BLACK);
        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(productsName));
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(productsName.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        pieChart.animateXY(2000,2000);
        barChart.invalidate();
        pieChart.invalidate();
    }
}