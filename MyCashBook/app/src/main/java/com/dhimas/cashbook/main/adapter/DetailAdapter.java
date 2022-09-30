package com.dhimas.cashbook.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dhimas.cashbook.R;
import com.dhimas.cashbook.main.model.DetailModel;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailHolder> {
    List<DetailModel> detailModels = new ArrayList<>();

    public DetailAdapter(List<DetailModel> detailModels){
        this.detailModels = detailModels;
    }

    @NonNull
    @Override
    public DetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_detail_cashflow, parent, false);
        return new DetailHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailHolder holder, int position) {
        holder.tvKeterangan.setText(detailModels.get(position).getKeterangan());
        holder.tvTanggal.setText(detailModels.get(position).getTanggal());
        if(detailModels.get(position).getFlow().equals("income")){
            holder.tvNominal.setText("[ + ] Rp. " + detailModels.get(position).getNominal());
            holder.income.setVisibility(View.VISIBLE);
            holder.outcome.setVisibility(View.GONE);
        } else {
            holder.tvNominal.setText("[ - ] Rp. " + detailModels.get(position).getNominal());
            holder.income.setVisibility(View.GONE);
            holder.outcome.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return detailModels.size();
    }

    class DetailHolder extends RecyclerView.ViewHolder {

        TextView tvNominal, tvKeterangan, tvTanggal;
        ImageView income, outcome;

        public DetailHolder( View itemView) {
            super(itemView);
            tvNominal =  itemView.findViewById(R.id.nominal);
            tvKeterangan = itemView.findViewById(R.id.keterangan);
            tvTanggal = itemView.findViewById(R.id.tanggal);
            income = itemView.findViewById(R.id.income);
            outcome = itemView.findViewById(R.id.outcome);
        }
    }
}
