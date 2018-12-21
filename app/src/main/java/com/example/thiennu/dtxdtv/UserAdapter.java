package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class UserAdapter extends ArrayAdapter<User> {
    ArrayList<User> users;
    Context mContext;
    public UserAdapter(@NonNull Context context, int resource, ArrayList<User> users) {
        super(context, resource);
        this.users = users;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.member_info, parent, false);
        }
        ((TextView)convertView.findViewById(R.id.textViewMemberName)).setText(((User)getItem(position)).name);
        ((TextView)convertView.findViewById(R.id.textViewMemberPhone)).setText(((User)getItem(position)).phone_number);
        return convertView;
    }
}
