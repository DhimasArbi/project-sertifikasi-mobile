package com.dhimas.cashbook.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dhimas.cashbook.R;

public class DetailAdapter extends ArrayAdapter {
    Integer[] Nominal;
    String[] Keterangan, Tanggal, Flow;

    public DetailAdapter(@NonNull Context context, Integer[] Nominal, String[] Keterangan, String[] Tanggal, String[] Flow){
        super(context, R.layout.listview_detail_cashflow, R.id.tanggal, Tanggal);
        this.Nominal = Nominal;
        this.Keterangan = Keterangan;
        this.Tanggal = Tanggal;
        this.Flow = Flow;
    }

    @SuppressLint("SetTextI18n")
    public View getView(final int position, View converView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.listview_detail_cashflow, parent, false);

        TextView tvNominal = row.findViewById(R.id.nominal);
        TextView tvKeterangan = row.findViewById(R.id.keterangan);
        TextView tvTanggal = row.findViewById(R.id.tanggal);
        ImageView income = row.findViewById(R.id.income);
        ImageView outcome = row.findViewById(R.id.outcome);

        tvKeterangan.setText(Keterangan[position]);
        tvTanggal.setText(Tanggal[position]);
        if(Flow[position].equals("income")){
            tvNominal.setText("[ + ] Rp. " + Nominal[position]);
            income.setVisibility(View.VISIBLE);
            outcome.setVisibility(View.GONE);
        } else {
            tvNominal.setText("[ - ] Rp. " + Nominal[position]);
            income.setVisibility(View.GONE);
            outcome.setVisibility(View.VISIBLE);
        }

        return row;
    }
}
