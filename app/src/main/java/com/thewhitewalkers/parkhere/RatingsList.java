package com.thewhitewalkers.parkhere;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class RatingsList extends ArrayAdapter<Rating> {

    private Activity context;
    private List<Rating> ratingsList;

    public RatingsList(Activity context, List<Rating> ratingsList){
        super(context, R.layout.rating_item, ratingsList);
        this.context = context;
        this.ratingsList = ratingsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.rating_item, null, true);

        RatingBar ratingBarComment = listViewItem.findViewById(R.id.ratingBarComment);
        TextView textViewRatingComment = listViewItem.findViewById(R.id.textViewRatingComment);

        Rating rating = ratingsList.get(position);

        ratingBarComment.setRating((float) rating.getRating());
        textViewRatingComment.setText(rating.getComment());

        return listViewItem;
    }
}
