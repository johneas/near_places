package com.gap.test.view.places.list.adapter;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gap.test.R;
import com.gap.test.data.model.Venue;
import com.gap.test.view.places.list.interfaces.OnItemClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S. on 22/04/2017.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private ArrayList<Venue> placesList;
    private Location myLocation;
    private DecimalFormat decimalFormat = new DecimalFormat("00.00");
    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PlaceAdapter(ArrayList<Venue> androidList, Location myLocation) {
        this.placesList = androidList;
        this.myLocation = myLocation;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.text_placeName.setText(placesList.get(position).getName());

        //Calculate distance from my location to near place, distance in Km
        double distance = distFrom(
                myLocation.getLatitude(),
                myLocation.getLongitude(),
                placesList.get(position).getLocation().getLat(),
                placesList.get(position).getLocation().getLng());

        holder.text_distance.setText(String.format("%s Km", decimalFormat.format(distance)));
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_placeName)
        TextView text_placeName;

        @BindView(R.id.text_distance)
        TextView text_distance;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(placesList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    /**
     * Created by oussama on 06/04/17.
     *
     * Support from https://github.com/OusamaLaidi/CurrentLocation
     */
    private static double distFrom(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 6371.0;

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadius * c;
    }
}
