package com.innovation.piazza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.innovation.piazza.Domain.Category;
import com.innovation.piazza.R;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category> implements ListAdapter{


    private ArrayList<Category> dataSet;
    private Context mContext;

    private static class  ViewHolder{
        TextView txtName;
        ImageView imagePicture;
    }

    public CategoryAdapter(ArrayList<Category> data, Context context) {
        super(context, R.layout.category_adapter, data);
        this.dataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category dataModel = getItem(position);
        final ViewHolder viewHolder;

        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.category_adapter, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.imagePicture = (ImageView) convertView.findViewById(R.id.picture);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.imagePicture.setImageBitmap(dataModel.getBitmap());

        return convertView;
    }
}
