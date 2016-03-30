package com.example.app.kupangpublicarea;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Phone> {

    private ArrayList<Phone> list;
    private Activity act;

    public ListAdapter(Activity context, ArrayList<Phone> objects) {
        super(context, R.layout.item, objects);
        this.list = objects;
        this.act = context;
    }

    static class ViewHolder {
        protected ImageView icon;
        protected TextView nama;
        protected TextView keterangan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = act.getLayoutInflater();
            view = inflater.inflate(R.layout.item, null);

            ViewHolder holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.item_icon);
            holder.nama = (TextView) view.findViewById(R.id.item_nama);
            holder.keterangan = (TextView) view
                    .findViewById(R.id.item_keterangan);
            view.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        Phone phone = list.get(position);

        holder.icon.setImageResource(R.drawable.info);
        holder.nama.setText(phone.getName());
        holder.keterangan.setText(phone.getBrand());

        return view;
    }
}
