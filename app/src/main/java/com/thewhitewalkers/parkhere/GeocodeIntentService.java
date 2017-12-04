package com.thewhitewalkers.parkhere;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GeocodeIntentService extends IntentService{
    public static final String ADDRESS = "ADDRESS";
    private ResultReceiver receiver;


    public GeocodeIntentService(){
        super("GeocodeIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        receiver = intent.getParcelableExtra("RECEIVER");

        if(receiver == null){
            //insert toast
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String name = intent.getStringExtra("ADDRESS");

        if(name == null || name == ""){
            //insert toast
            return;
        }


        List<Address> addresses = null;

        try{
            addresses = geocoder.getFromLocationName(name, 5);

        }
        catch(IOException ioException){
            //put down a toast
        }
        catch(IllegalArgumentException illegalArgumentException){
            //put down a toast
        }

        if(addresses == null || addresses.size() == 0){
            //toast

            deliverResultToReceiver(0,new ArrayList<Address>());
        }
        else{
            //send to listview for selection :)
            ArrayList<Address> fiveAddresses = new ArrayList<>(5);
            for(int i = 0;  i < addresses.size(); i++){
                fiveAddresses.add(addresses.get(i));
            }
            deliverResultToReceiver(1, fiveAddresses);
        }
    }

    private void deliverResultToReceiver(int resultCode, ArrayList<Address> addressedQuerried){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ADDRESSES", addressedQuerried);
        receiver.send(resultCode, bundle);
    }
}