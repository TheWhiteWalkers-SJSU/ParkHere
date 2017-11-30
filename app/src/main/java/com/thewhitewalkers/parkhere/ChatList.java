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

public class ChatList extends ArrayAdapter<Chat> {

    private Activity context;
    private List<Chat> chatList;

    public ChatList(Activity context, List<Chat> cList){
        super(context, R.layout.chat_item, cList);
        this.context = context;
        this.chatList = cList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View chatItem = inflater.inflate(R.layout.chat_item, null, true);

        TextView textViewUser = chatItem.findViewById(R.id.textViewMessageUser);
        TextView textViewNameAddress = chatItem.findViewById(R.id.textViewMessageNameAddress);

        Chat chat = chatList.get(position);

        textViewUser.setText(chat.getRecipient().getEmail());
//        textViewNameAddress.setText(chat.getNameAddress());

        return chatItem;
    }
}
