package com.dhimas.cashbook.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhimas.cashbook.R;
import com.dhimas.cashbook.database.DatabaseAccess;
import com.dhimas.cashbook.main.adapter.DetailAdapter;
import com.dhimas.cashbook.main.model.DetailModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailCashFlowActivity extends AppCompatActivity {
    Button kembali;
    RecyclerView recyclerView;
    DetailAdapter detailAdapter;
    List<DetailModel> keuanganList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashflow_detail);

        kembali = findViewById(R.id.kembali);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailCashFlowActivity.this));
        getData();

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailCashFlowActivity.this, DashboardActivity.class));
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){

            @Override
            public boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int posisi = viewHolder.getAdapterPosition();
                int nominal;
                String keterangan, tanggal, flow;
                DetailModel deleteItem = keuanganList.get(posisi);
                nominal = keuanganList.get(posisi).getNominal();
                keterangan = keuanganList.get(posisi).getKeterangan();
                tanggal = keuanganList.get(posisi).getTanggal();
                flow = keuanganList.get(posisi).getFlow();

                DatabaseAccess dbaccess = DatabaseAccess.getInstance(DetailCashFlowActivity.this);
                dbaccess.open();
                dbaccess.hapusDataKeuangan(deleteItem);
                keuanganList.remove(posisi);

                detailAdapter.notifyItemRemoved(posisi);
                Snackbar.make(recyclerView, deleteItem.getTanggal().toString()+" "+deleteItem.getNominal().toString()+" "+deleteItem.getKeterangan().toString(), Snackbar.LENGTH_LONG).setAction("Batal", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        keuanganList.add(posisi,deleteItem);;
                        DatabaseAccess dbaccess = DatabaseAccess.getInstance(DetailCashFlowActivity.this);
                        dbaccess.open();
                        boolean isInserted = dbaccess.insertMoney(nominal, keterangan, tanggal, flow);

                        if(isInserted){
                            Toast.makeText(DetailCashFlowActivity.this, "Berhasil Dibatalkan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DetailCashFlowActivity.this, "Gagal Dibatalkan", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void getData() {
        DatabaseAccess dbaccess = DatabaseAccess.getInstance(DetailCashFlowActivity.this);
        dbaccess.open();
        keuanganList.clear();
        keuanganList = dbaccess.getDataKeuangan();
        detailAdapter = new DetailAdapter(keuanganList);

        if(detailAdapter.getItemCount() == 0){
            Toast.makeText(DetailCashFlowActivity.this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
        } else {
            recyclerView.setAdapter(detailAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailCashFlowActivity.this, DashboardActivity.class));
    }
}
