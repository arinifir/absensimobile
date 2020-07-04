package com.example.sipjti;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodayAdapter2 extends RecyclerView.Adapter<TodayAdapter2.MyViewHolder> {

    private ArrayList<PlayerList> dataList;

    public TodayAdapter2(ArrayList<PlayerList> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_schedule, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.hari.setText(dataList.get(position).getHari());
        holder.matkul.setText(dataList.get(position).getMatkul());
        holder.mulai.setText(dataList.get(position).getMulai());
        holder.selesai.setText(dataList.get(position).getSelesai());
        holder.dosen.setText(dataList.get(position).getDosen());
        holder.ruangan.setText(dataList.get(position).getRuang());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView hari, matkul, mulai, selesai, dosen, ruangan;

        public MyViewHolder(View itemView) {
            super(itemView);
            hari = (TextView) itemView.findViewById(R.id.nama_hari);
            matkul = (TextView) itemView.findViewById(R.id.nama_matkul);
            mulai = (TextView) itemView.findViewById(R.id.waktu_mulai);
            selesai = (TextView) itemView.findViewById(R.id.waktu_selesai);
            dosen = (TextView) itemView.findViewById(R.id.nama_dosen);
            ruangan = (TextView) itemView.findViewById(R.id.ruangan);
        }
    }
}