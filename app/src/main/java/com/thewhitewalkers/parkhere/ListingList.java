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
 * Created by Rohit on 10/27/17.
 */

public class ListingList extends ArrayAdapter<Listing> {

    private Activity context;
    private List<Listing> listingList;

    public ListingList(Activity context, List<Listing> listingList){
        super(context, R.layout.homepage_listings, listingList);
        this.context = context;
        this.listingList = listingList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.homepage_listings, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewListingName);
        TextView textViewAddress = listViewItem.findViewById(R.id.textViewListingAddress);
        TextView textViewListingStatus = listViewItem.findViewById(R.id.textViewListingStatus);

        Listing listing = listingList.get(position);

        textViewName.setText(listing.getListingName());
        textViewAddress.setText(listing.getListingAddress());
        textViewListingStatus.setText(listing.getListingStatus());

        return listViewItem;
    }
}
