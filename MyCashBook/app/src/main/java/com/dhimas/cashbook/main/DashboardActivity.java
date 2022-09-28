package com.dhimas.cashbook.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dhimas.cashbook.R;
import com.dhimas.cashbook.database.DatabaseAccess;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout income, outcome, cashflow_detail, setting;
    TextView pengeluaran, pemasukkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        income = findViewById(R.id.income);
        outcome = findViewById(R.id.outcome);
        cashflow_detail = findViewById(R.id.detail);
        setting = findViewById(R.id.setting);
        pengeluaran = findViewById(R.id.outcome_cash);
        pemasukkan = findViewById(R.id.income_cash);

        getTotalIncome();
        getTotalOutcome();

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, IncomeActivity.class));
            }
        });

        outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, OutcomeActivity.class));
            }
        });

        cashflow_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, DetailCashFlowActivity.class));
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, SettingActivity.class));
            }
        });
    }

    private void getTotalIncome() {
        DatabaseAccess dbacess = DatabaseAccess.getInstance(DashboardActivity.this);
        dbacess.open();

        Cursor data = dbacess.Sum("jumlah", "keuangan", "flow = 'income'");

        if(data.getCount() == 0){
            pemasukkan.setText("Pemasukkan : Rp. 0.-");
        } else {
            while(data.moveToNext()){
                if(data.getString(0) != null) {
                    pemasukkan.setText("Pemasukkan : Rp. " + data.getString(0) + ".-");
                } else {
                    pemasukkan.setText("Pemasukkan : Rp. 0.-");
                }
            }
        }
    }

    private void getTotalOutcome() {
        DatabaseAccess dbacess = DatabaseAccess.getInstance(DashboardActivity.this);
        dbacess.open();

        Cursor data = dbacess.Sum("jumlah", "keuangan", "flow = 'outcome'");

        if(data.getCount() == 0){
            pengeluaran.setText("Pengeluaran : Rp. 0.-");
        } else {
            while(data.moveToNext()){
                if(data.getString(0) != null) {
                    pengeluaran.setText("Pengeluaran : Rp. " + data.getString(0) + ".-");
                } else {
                    pengeluaran.setText("Pengeluaran : Rp. 0.-");
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        moveTaskToBack(true);
    }
}
