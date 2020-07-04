package com.example.sipjti;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class RecordAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PlayerRecord> dataModelArrayList;
    public RecordAdapter(Context context, ArrayList<PlayerRecord> dataModelArrayList) {
        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.card_record, null, true);
            holder.inis = (TextView) convertView.findViewById(R.id.tvInis);
            holder.matkul = (TextView) convertView.findViewById(R.id.tvMatkul);
            holder.banding = (TextView) convertView.findViewById(R.id.tvBanding);
            holder.persen = (TextView) convertView.findViewById(R.id.tvPersen);
            holder.minggu = (TextView) convertView.findViewById(R.id.tvPer);
            holder.stat = (TextView) convertView.findViewById(R.id.tvStat);

            holder.inis.setText(dataModelArrayList.get(position).getInisial());
            holder.matkul.setText(dataModelArrayList.get(position).getNama_matkul());
            holder.banding.setText(dataModelArrayList.get(position).getJumlah_hadir()+"/"+dataModelArrayList.get(position).getJumlah_pertemuan());
            holder.persen.setText("("+dataModelArrayList.get(position).getPersentase()+"%)");
            holder.minggu.setText(dataModelArrayList.get(position).getMinggu_pertemuan());
            holder.stat.setText(dataModelArrayList.get(position).getStatus_absen());
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    private class ViewHolder {
        protected TextView inis, matkul, banding, persen, minggu, stat;
    }
}