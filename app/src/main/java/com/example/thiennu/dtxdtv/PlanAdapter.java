package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends ArrayAdapter<Place> {

    Context mContext;
    private int mResource;
    private List<Place> placeList;

    public PlanAdapter(Context context, int resource, ArrayList<Place> objects) {
        super(context, resource, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.placeList = objects;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.plan_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.textViewPlanName);
            viewHolder.time = (TextView) convertView.findViewById(R.id.textViewTime);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Place place = placeList.get(position);
        viewHolder.name.setText(place.name);
        viewHolder.time.setText(place.time);

        return convertView;
    }

    public class ViewHolder{
        TextView name;
        TextView time;
    }
}
