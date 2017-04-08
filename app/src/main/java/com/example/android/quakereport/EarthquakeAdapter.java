package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by elizabethtarazona on 27/02/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> Earthquakes) {
        super(context, 0, Earthquakes);
    }

/**
 * return a single view of the item
 * **/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ListItemView = convertView;
        if(ListItemView == null){
            ListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_item,parent,false);
        }
        Earthquake currentEarthquake = getItem(position);

        TextView Magnitude = (TextView)  ListItemView.findViewById(R.id.magnitude);
        TextView Location = (TextView) ListItemView.findViewById(R.id.location);
        TextView place = (TextView) ListItemView.findViewById(R.id.place);
        GradientDrawable magnitudeCircle = (GradientDrawable) Magnitude.getBackground();
        TextView date = (TextView) ListItemView.findViewById(R.id.day);

//obtain the color of the background depending of the magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

// Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(currentEarthquake.getMagnitude());
        Magnitude.setText(output);


//make better the text of the current Earthquake
        String location = currentEarthquake.getLocation();
        if(location.contains("of")) {
            String[] locationSplit =location.split("of");
            Location.setText(locationSplit[0]+" of ");
            place.setText(locationSplit[1]);
        }
        else
        {
            Location.setText("Near the ");
            place.setText(location);
        }



        Date realDate = new Date( currentEarthquake.getDate());

        String formattedDate = formatDate(realDate);
        date.setText(formattedDate);

        TextView time = (TextView) ListItemView.findViewById(R.id.time);
        String formattedTime = formatTime(realDate);
        time.setText(formattedTime);

        return ListItemView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorId;
        int magitudeFloor =(int) Math.floor(magnitude);
        switch (magitudeFloor){
            case 1:
                magnitudeColorId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorId = R.color.magnitude9;
                break;
            default:
                magnitudeColorId = R.color.magnitude1;
                break;


        }


        return ContextCompat.getColor(getContext(), magnitudeColorId);
    }

    //method for make the date with format
    private String formatDate(Date realDate) {
        SimpleDateFormat simple_format = new SimpleDateFormat("LLL dd, yyyy");
        return simple_format.format(realDate);
    }
//method for make the time with format
    private String formatTime(Date realDate) {
        SimpleDateFormat simple_format = new SimpleDateFormat("hh:mm a");
        return simple_format.format(realDate);
    }


}
