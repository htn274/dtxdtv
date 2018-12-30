package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends ArrayAdapter<Place_In_Plan> {

    Context mContext;
    private int mResource;
    private List<Place_In_Plan> placeInPlanList;

    public PlanAdapter(Context context, int resource, ArrayList<Place_In_Plan> objects) {
        super(context, resource, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.placeInPlanList = objects;
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

        Place_In_Plan placeInPlan = placeInPlanList.get(position);
        viewHolder.name.setText(placeInPlan.name);
        viewHolder.time.setText(placeInPlan.time);

        return convertView;
    }

    public class ViewHolder{
        TextView name;
        TextView time;
    }
}
