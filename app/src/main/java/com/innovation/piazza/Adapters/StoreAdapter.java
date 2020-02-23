package com.innovation.piazza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.innovation.piazza.Domain.StoreModel;
import com.innovation.piazza.R;
import java.util.ArrayList;

public class StoreAdapter extends ArrayAdapter<StoreModel> implements ListAdapter {

    private ArrayList<StoreModel> dataSet;
    private Context mContext;

    private static class  ViewHolder{
        TextView txtName;
        TextView txtAddress;
        ImageView imageLogo;
    }

    public StoreAdapter(ArrayList<StoreModel> data, Context context) {
        super(context, R.layout.store_adapter, data);
        this.dataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreModel dataModel = getItem(position);
        ViewHolder viewHolder;

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

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getStoreName());
        viewHolder.txtAddress.setText(dataModel.getAddress());
        //TODO : sa facem listener pe tot elementul, nu doar pe poza

        //TODO : Pune imaginea in viewHolder, nu stiu exact cum salvam url-urile.

        return convertView;
    }
}
