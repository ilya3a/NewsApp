package com.yoyo.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yoyo.newsapp.weather_data_models.Day;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeatherStatusRecyclerViewAdapter extends RecyclerView.Adapter<WeatherStatusRecyclerViewAdapter.ViewHolder> {

    private List<Day> dayList;
    private Context mContext;


    public WeatherStatusRecyclerViewAdapter(List<Day> dayList, Context mContext) {
        this.dayList = dayList;
        this.mContext = mContext;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayStatusTv;
        ImageView iconStatusIv;
        TextView tempStatusTv;
        TextView tempStatus2Tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayStatusTv = itemView.findViewById(R.id.day_status_tv);
            iconStatusIv = itemView.findViewById(R.id.icon_status_iv);
            tempStatusTv = itemView.findViewById(R.id.temp_status_tv);
            tempStatus2Tv = itemView.findViewById(R.id.temp_status2_tv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_status_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Day day = dayList.get(i);


        viewHolder.dayStatusTv.setText(day.getUv());
        viewHolder.tempStatusTv.setText(day.getMaxtemp_c().substring(0, day.getMaxtemp_c().indexOf(".")) + "°");
        viewHolder.tempStatus2Tv.setText(day.getMintemp_c().substring(0, day.getMintemp_c().indexOf(".")) + "°");
        String iconUrl = day.getCondition().getIcon();
        Glide.with(mContext).load("https:" + iconUrl).into(viewHolder.iconStatusIv);

    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

}
