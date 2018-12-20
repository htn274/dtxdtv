package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TripListAdapter extends ArrayAdapter<TripInfo> {

    Context mContext;
    private int mResource;
    private List<TripInfo> tripList;

    public TripListAdapter(Context context, int resource,ArrayList<TripInfo> objects) {
        super(context, resource, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.tripList = objects;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.trip_info, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tripName = (TextView) convertView.findViewById(R.id.tripName);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TripInfo tripInfo = tripList.get(position);
        viewHolder.tripName.setText(tripInfo.getTrip_name());

        return convertView;
    }

    public class ViewHolder{
        TextView tripName;
    }
}
