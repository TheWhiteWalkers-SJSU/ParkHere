package com.thewhitewalkers.parkhere;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rohit on 12/3/17.
 */

public class ParkingSpotList extends ArrayAdapter<ParkingSpot> {

    private Activity context;
    private List<ParkingSpot> parkingSpotList;

    public ParkingSpotList(Activity context, List<ParkingSpot> parkingSpotList){
        super(context, R.layout.parking_spot_list, parkingSpotList);
        this.context = context;
        this.parkingSpotList = parkingSpotList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.parking_spot_list, null, true);

        TextView textViewAddress = listViewItem.findViewById(R.id.textViewParkingSpotAddress);
        ParkingSpot parkingSpot = parkingSpotList.get(position);
        textViewAddress.setText(parkingSpot.getAddress());

        return listViewItem;
    }


}


