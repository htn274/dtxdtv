package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlanAdapter extends ArrayAdapter<Plan> {
    ArrayList<Plan> mData;
    Context mContext;
    public PlanAdapter(@NonNull Context context, int resource, ArrayList<Plan> data) {
        super(context, resource);
        this.mData = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = createItemViewAt(position);
        }
        return convertView;
    }

    private View createItemViewAt(int position) {
        View view = createViewByLayoutId(R.layout.plan_layout);
        Plan itemData = getItem(position);
        bindItemDataToView(itemData, view);
        return view;
    }

    private void bindItemDataToView(Plan itemData, View view) {
        ((TextView)view.findViewById(R.id.textViewPlanName)).setText(itemData.name);
        ((TextView)view.findViewById(R.id.textViewTime)).setText(itemData.time);
    }

    private View createViewByLayoutId(int id) {
        return LayoutInflater.from(this.mContext).inflate(id, null);
    }
}
