package com.example.thiennu.dtxdtv;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DashAdapter extends RecyclerView.Adapter<DashAdapter.ViewHolder> {

        ArrayList<DashModel> dashModelArrayList;
        Context mContext;

        public DashAdapter(Context context, ArrayList<DashModel> dashModelArrayList) {
            mContext = context;
            this.dashModelArrayList = dashModelArrayList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            String ret_head = dashModelArrayList.get(position).getHead();
            holder.setheader(ret_head);

            int ret_image = dashModelArrayList.get(position).getImage();
            holder.set_image(ret_image);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Trips
                    if (position == 5){
                        Intent intent = new Intent(mContext, Trips.class);
                        mContext.startActivity(intent);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return dashModelArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView header;
            ImageView images;
            View myView;
            CardView cardView;

            public ViewHolder(View itemView) {
                super(itemView);
                myView = itemView;
                cardView = myView.findViewById(R.id.cardViewDashBoard);
            }

            public void setheader(String h)
            {
                header = myView.findViewById(R.id.dash_header);
                header.setText(h);
            }

            public void set_image(int i)
            {
                images = myView.findViewById(R.id.dash_image);
                images.setImageResource(i);
            }


        }
}
