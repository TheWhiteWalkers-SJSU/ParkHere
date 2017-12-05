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

public class HistoryList extends ArrayAdapter<RecentAddress> {

    private Activity context;
    private List<RecentAddress> addressList;

    public HistoryList(Activity context, List<RecentAddress> addressList){
        super(context, R.layout.history_item, addressList);
        this.context = context;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.history_item, null, true);

        TextView textViewHistoryAddressLine = listViewItem.findViewById(R.id.textViewHistoryAddressLine);

        textViewHistoryAddressLine.setText(addressList.get(position).getAddress().toString());
        return listViewItem;
    }
}