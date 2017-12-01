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
    private String currentEmail;
    private String otherEmail;

    public ChatList(Activity context, List<Chat> cList){
        super(context, R.layout.chat_item, cList);
        this.context = context;
        this.chatList = cList;
    }

    public void setCurrentChatEmail(String email) {
        currentEmail = email;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View chatItem = inflater.inflate(R.layout.chat_item, null, true);

        TextView textViewUser = chatItem.findViewById(R.id.textViewMessageUser);
        Chat chat = chatList.get(position);

        //if current user is 1st email, set other user to 2nd email, vice versa
        if(currentEmail.equals(chat.getEmailUser1())) {
            otherEmail = chat.getEmailUser2();
        }
        else {
            otherEmail = chat.getEmailUser1();
        }
        textViewUser.setText(otherEmail);

        return chatItem;
    }
}
