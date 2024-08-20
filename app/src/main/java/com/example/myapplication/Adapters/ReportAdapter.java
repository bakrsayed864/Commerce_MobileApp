package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Datbase.CommerceDBHelper;
import com.example.myapplication.Models.ReportInfo;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ReportAdapter extends BaseAdapter {
    ArrayList<ReportInfo> data;
    Context context;
    CommerceDBHelper database;
    TextView cust_name;
    TextView address;
    TextView product;
    TextView quantity;
    TextView date;
    TextView rate;
    public ReportAdapter(Context context, ArrayList<ReportInfo> data) {
        this.data = data;
        this.context = context;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.report_item, parent, false);
        }
        cust_name=convertView.findViewById(R.id.textView_name);
        address=convertView.findViewById(R.id.textView_address);
        product=convertView.findViewById(R.id.textView_product);
        quantity=convertView.findViewById(R.id.textView_quantity);
        date=convertView.findViewById(R.id.textView_date);
        rate=convertView.findViewById(R.id.textView_rate);
        cust_name.setText(data.get(position).getCustomerName());
        address.setText(data.get(position).getAddress());
        product.setText(data.get(position).getProductName());
        quantity.setText(String.valueOf(data.get(position).getProductQuantity()));
        date.setText(data.get(position).getDate());
        rate.setText(String.valueOf(data.get(position).getRate()));
        return convertView;
    }
}
