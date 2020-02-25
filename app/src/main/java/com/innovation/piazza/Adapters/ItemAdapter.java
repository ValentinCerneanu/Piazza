package com.innovation.piazza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.innovation.piazza.Domain.Item;
import com.innovation.piazza.R;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> implements ListAdapter {

    private ArrayList<Item> dataSet;
    private Context mContext;

    private static class  ViewHolder{
        TextView txtName;
        TextView txtDescription;
        TextView txtPrice;
        ImageView imagePicture;
    }

    public ItemAdapter(ArrayList<Item> data, Context context) {
        super(context, R.layout.item_adapter, data);
        this.dataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item dataModel = getItem(position);
        final ItemAdapter.ViewHolder viewHolder;

        final View result;

        if(convertView == null) {
            viewHolder = new ItemAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_adapter, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.description);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.price);
            viewHolder.imagePicture = (ImageView) convertView.findViewById(R.id.picture);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtDescription.setText(dataModel.getDescription());
        viewHolder.txtPrice.setText(dataModel.getPrice().toString());
        //viewHolder.imagePicture.setImageBitmap(dataModel.getBitmap());

        return convertView;
    }
}
