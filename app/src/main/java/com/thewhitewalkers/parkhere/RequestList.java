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
 * Created by Rohit on 10/31/17.
 */

public class RequestList extends ArrayAdapter<Request> {

    private Activity context;
    private List<Request> requestList;

    public RequestList(Activity context, List<Request> requestList){
        super(context, R.layout.inbox_item, requestList);
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.inbox_item, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewRequestSubject);
        TextView textViewRequestedMessage= listViewItem.findViewById(R.id.textViewRequestedMessage);


        Request request = requestList.get(position);

        //TODO: Currently the request view is being populated w/ the ListingID and Sender's UUID, this needs to be changed to the listing address and sender's email
        textViewName.setText(request.getSubject());
        textViewRequestedMessage.setText(request.getMessage());

        return listViewItem;
    }
}
