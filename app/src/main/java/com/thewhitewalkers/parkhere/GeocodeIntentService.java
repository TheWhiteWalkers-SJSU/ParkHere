package com.thewhitewalkers.parkhere;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;


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
            addresses = geocoder.getFromLocationName(name, 1);

        }
        catch(IOException ioException){
            //put down a toast
        }
        catch(IllegalArgumentException illegalArgumentException){
            //put down a toast
        }

        if(addresses == null || addresses.size() == 0){
            //toast

            deliverResultToReceiver(0, "no ", null);
        }
        else{
            //send to listview for selection :)
            Address addr = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();

            for(int i = 0; i <= addr.getMaxAddressLineIndex(); i++){
                addressFragments.add(addr.getAddressLine(i));
            }
            deliverResultToReceiver(1,
                    TextUtils.join(System.getProperty("line.separator"), addressFragments), addr);
        }
    }

    private void deliverResultToReceiver(int resultCode, String message, Address address){
        Bundle bundle = new Bundle();
        bundle.putParcelable("ADDRESS", address);
        bundle.putString("result", message);
        receiver.send(resultCode, bundle);
    }
}