package com.innovation.piazza.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.innovation.piazza.Activities.CartActivity;
import com.innovation.piazza.Domain.Item;
import com.innovation.piazza.R;
import com.innovation.piazza.Repository.CartRepository;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> implements ListAdapter {

    private ArrayList<Item> dataSet;
    private Context context;
    private String selectedStoreKey;

    private static class  ViewHolder{
        TextView txtName;
        TextView txtDescription;
        TextView txtPrice;
        ImageView imagePicture;
        ImageButton minusButton;
        ImageButton plusButton;
        TextView quantity;
    }

    public ItemAdapter(ArrayList<Item> data, Context context, String selectedStoreKey) {
        super(context, R.layout.item_adapter, data);
        this.dataSet = data;
        this.context = context;
        this.selectedStoreKey = selectedStoreKey;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Item selectedItem = getItem(position);
        final ItemAdapter.ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ItemAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_adapter, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.description);
            viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.price);
            viewHolder.imagePicture = (ImageView) convertView.findViewById(R.id.item_picture);
            viewHolder.minusButton = (ImageButton) convertView.findViewById(R.id.minus_button);
            viewHolder.plusButton = (ImageButton) convertView.findViewById(R.id.plus_button);
            viewHolder.quantity = (TextView) convertView.findViewById(R.id.quantity);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(selectedItem.getName());
        viewHolder.txtDescription.setText(selectedItem.getDescription());
        viewHolder.quantity.setText(String.valueOf(CartRepository.getInstance().getQuantity(selectedItem.getKey())));
        viewHolder.txtPrice.setText(selectedItem.getPrice().toString());
        viewHolder.imagePicture.setImageBitmap(selectedItem.getBitmap());

        viewHolder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] quantity = {Integer.parseInt(viewHolder.quantity.getText().toString())};
                if(quantity[0] > 0) {
                    if(quantity[0] == 1) {
                        new AlertDialog.Builder(context)
                                .setTitle("Eliminare produs")
                                .setMessage("Vrei sa elimini acest produs din cos?")

                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        viewHolder.quantity.setText(String.valueOf(--quantity[0]));
                                        CartRepository cartRepository = CartRepository.getInstance();
                                        selectedItem.setQuantity(quantity[0]);
                                        cartRepository.addItemInCart(selectedItem, selectedStoreKey);
                                        if(context instanceof CartActivity) {
                                            ((CartActivity) context).refreshUI(selectedItem);
                                        }
                                    }
                                })
                                .setNegativeButton(R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        viewHolder.quantity.setText(String.valueOf(--quantity[0]));
                        CartRepository cartRepository = CartRepository.getInstance();
                        selectedItem.setQuantity(quantity[0]);
                        cartRepository.addItemInCart(selectedItem, selectedStoreKey);
                    }
                }
            }
        });

        viewHolder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(viewHolder.quantity.getText().toString());
                viewHolder.quantity.setText(String.valueOf(++quantity));

                CartRepository cartRepository = CartRepository.getInstance();
                selectedItem.setQuantity(quantity);
                if(cartRepository.addItemInCart(selectedItem, selectedStoreKey)){
                    //TODO: o animatie pe butonul de cart
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("Produse din alt magazin")
                            .setMessage("Ai in cos produse de la alt magazin! Doresti sa stergi cosul curent si sa incepi unul nou cu acest produs?")

                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    CartRepository.getInstance().clearCart();
                                    CartRepository.getInstance().addItemInCart(selectedItem, selectedStoreKey);
                                }
                            })
                            .setNegativeButton(R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

        return convertView;
    }
}
