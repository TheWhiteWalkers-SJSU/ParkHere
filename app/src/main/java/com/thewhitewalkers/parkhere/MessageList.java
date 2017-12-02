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

public class MessageList extends ArrayAdapter<Message> {

    private Activity context;
    private List<Message> messageList;

    public MessageList(Activity context, List<Message> mList){
        super(context, R.layout.message_item, mList);
        this.context = context;
        this.messageList = mList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View messageItem = inflater.inflate(R.layout.message_item, null, true);

        TextView textViewUser = messageItem.findViewById(R.id.textViewMessageUser);
//        TextView textViewDateTime = messageItem.findViewById(R.id.textViewMessageDateTime);
        TextView textViewBody = messageItem.findViewById(R.id.textViewMessageBody);

        Message message = messageList.get(position);
//
        textViewUser.setText(message.getEmail());
////        textViewDateTime.setText(message.getListingAddress());
        textViewBody.setText(message.getBody());

        return messageItem;
    }
}
