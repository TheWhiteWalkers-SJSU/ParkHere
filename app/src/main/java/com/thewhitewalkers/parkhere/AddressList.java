package com.thewhitewalkers.parkhere;
import android.app.Activity;
import android.location.Address;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddressList extends ArrayAdapter<Address> {

    private Activity context;
    private List<Address> addressList;

    public AddressList(Activity context, List<Address> addressList){
        super(context, R.layout.address_item, addressList);
        this.context = context;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.address_item, null, true);

        TextView textViewAddressLine = listViewItem.findViewById(R.id.textViewAddressLine);

        Address address = addressList.get(position);

        ArrayList<String> addressFrags = new ArrayList<>();
        for(int i = 0; i <= address.getMaxAddressLineIndex(); i++){
            addressFrags.add(address.getAddressLine(i));
        }
        textViewAddressLine.setText(TextUtils.join(System.getProperty("line.separator"), addressFrags));
        return listViewItem;
    }
}