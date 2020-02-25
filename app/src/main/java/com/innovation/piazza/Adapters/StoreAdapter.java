package com.innovation.piazza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.innovation.piazza.Domain.Store;
import com.innovation.piazza.R;

import java.util.ArrayList;

public class StoreAdapter extends ArrayAdapter<Store> implements ListAdapter {

    private ArrayList<Store> dataSet;
    private Context mContext;

    private static class  ViewHolder{
        TextView txtName;
        TextView txtAddress;
        ImageView imageLogo;
    }

    public StoreAdapter(ArrayList<Store> data, Context context) {
        super(context, R.layout.store_adapter, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Store dataModel = getItem(position);
        final ViewHolder viewHolder;

        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.store_adapter, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.storeName);
            viewHolder.txtAddress = (TextView) convertView.findViewById(R.id.address);
            viewHolder.imageLogo = (ImageView) convertView.findViewById(R.id.storeImage);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtAddress.setText(dataModel.getAddress());
        viewHolder.imageLogo.setImageBitmap(dataModel.getBitmap());

        return convertView;
    }
}
