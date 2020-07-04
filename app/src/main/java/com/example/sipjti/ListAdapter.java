package com.example.sipjti;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PlayerList> dataModelArrayList;
    public ListAdapter(Context context, ArrayList<PlayerList> dataModelArrayList) {
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
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.card_schedule, null, true);
            holder.hari = (TextView) convertView.findViewById(R.id.nama_hari);
            holder.matkul = (TextView) convertView.findViewById(R.id.nama_matkul);
            holder.mulai = (TextView) convertView.findViewById(R.id.waktu_mulai);
            holder.selesai = (TextView) convertView.findViewById(R.id.waktu_selesai);
            holder.dosen = (TextView) convertView.findViewById(R.id.nama_dosen);
            holder.ruangan = (TextView) convertView.findViewById(R.id.ruangan);

            holder.hari.setText(dataModelArrayList.get(position).getHari());
            holder.matkul.setText(dataModelArrayList.get(position).getMatkul());
            holder.mulai.setText(dataModelArrayList.get(position).getMulai());
            holder.selesai.setText(dataModelArrayList.get(position).getSelesai());
            holder.dosen.setText(dataModelArrayList.get(position).getDosen());
            holder.ruangan.setText(dataModelArrayList.get(position).getRuang());
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    private class ViewHolder {
        protected TextView hari, matkul, mulai, selesai, dosen, ruangan;
    }
}