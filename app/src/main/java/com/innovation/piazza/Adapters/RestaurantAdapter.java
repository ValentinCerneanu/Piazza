package com.innovation.piazza.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.innovation.piazza.Domain.OrderModel;
import com.innovation.piazza.Domain.Restaurant;
import com.innovation.piazza.R;
import com.innovation.piazza.Repository.UserRepository;

import java.util.ArrayList;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> implements ListAdapter {

    private ArrayList<Restaurant> dataSet;
    private Context mContext;

    private FirebaseDatabase database;
    private DatabaseReference myRefToDatabase;

    private static class  ViewHolder{
        TextView name;
        TextView address;
        TextView discountValue;
        TextView oreDiscout;
        Button rezerva;
    }

    public RestaurantAdapter(ArrayList<Restaurant> data, Context context) {
        super(context, R.layout.order_adapter, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Restaurant dataModel = getItem(position);
        final RestaurantAdapter.ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new RestaurantAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.order_adapter, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.restaurant_name);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);
            viewHolder.discountValue = (TextView) convertView.findViewById(R.id.value);
            viewHolder.oreDiscout = (TextView) convertView.findViewById(R.id.ore_discount);
            viewHolder.rezerva = convertView.findViewById(R.id.button_action);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RestaurantAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(dataModel.getName());
        viewHolder.address.setText("Adresa: " + String.valueOf(dataModel.getAddress()));
        viewHolder.discountValue.setText("Valoare discount: " + dataModel.getDiscountValue());
        viewHolder.oreDiscout.setText("Ore aplicare discount: " + dataModel.getOreDiscount());

        final View finalConvertView = convertView;
        viewHolder.rezerva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                myRefToDatabase = database.getReference("Restaurants");
                myRefToDatabase.child(dataModel.getKey()).child("booked")
                        .setValue("da").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });

               /* myRefToDatabase.child(dataModel.getOrderKey()).child(OrderModel.DELIVERY_KEY)
                        .setValue(UserRepository.getInstance().getFirebareUserID()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });*/
            }
        });

        return finalConvertView;
    }
}