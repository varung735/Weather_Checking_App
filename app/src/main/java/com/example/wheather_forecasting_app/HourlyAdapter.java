package com.example.wheather_forecasting_app;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyHolder> {

    @NonNull
    @Override
    public HourlyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HourlyHolder extends RecyclerView.ViewHolder {

        public HourlyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
