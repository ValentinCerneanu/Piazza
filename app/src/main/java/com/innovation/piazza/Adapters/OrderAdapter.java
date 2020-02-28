package com.innovation.piazza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.innovation.piazza.Domain.Order;
import com.innovation.piazza.R;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> implements ListAdapter {

    private ArrayList<Order> dataSet;
    private Context mContext;

    private static class  ViewHolder{
        TextView storeNameTextView;
        TextView totalPriceTextView;
        TextView timeTextView;
        TextView addressTextView;
        TextView statusTextView;
    }

    public OrderAdapter(ArrayList<Order> data, Context context) {
        super(context, R.layout.order_adapter, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order dataModel = getItem(position);
        final OrderAdapter.ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new OrderAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.order_adapter, parent, false);
            viewHolder.storeNameTextView = (TextView) convertView.findViewById(R.id.store_name);
            viewHolder.totalPriceTextView = (TextView) convertView.findViewById(R.id.total_price);
            viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.time);
            viewHolder.addressTextView = (TextView) convertView.findViewById(R.id.address);
            viewHolder.statusTextView = (TextView) convertView.findViewById(R.id.status);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (OrderAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.storeNameTextView.setText("Magazin: " + dataModel.getStoreKey());
        viewHolder.totalPriceTextView.setText("Pret total: " + String.valueOf(dataModel.getTotalPrice()));
        viewHolder.timeTextView.setText("Plasata pe: " + dataModel.getTime());
        viewHolder.addressTextView.setText("Adresa de livrare: " + dataModel.getAddress());
        viewHolder.statusTextView.setText("Status comanda: " + dataModel.getStatus());

        return convertView;
    }
}
