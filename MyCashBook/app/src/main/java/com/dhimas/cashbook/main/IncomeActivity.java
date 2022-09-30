package com.dhimas.cashbook.main;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dhimas.cashbook.R;
import com.dhimas.cashbook.database.DatabaseAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class IncomeActivity extends AppCompatActivity {
    Button simpan, kembali;
    EditText nominal, keterangan;
    ImageButton btntanggal;
    TextView tanggal;

    String tanggalLokal = "";

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdfTanggalIndo = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        simpan = findViewById(R.id.simpan);
        kembali = findViewById(R.id.kembali);
        nominal = findViewById(R.id.nominal);
        keterangan = findViewById(R.id.keterangan);
        btntanggal = findViewById(R.id.btn_date);
        tanggal = findViewById(R.id.tanggal);

        tanggalLokal = sdfTanggalIndo.format(new Date());

        tanggal.setText(tanggalLokal);

        btntanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(IncomeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            tanggalLokal = sdfTanggalIndo.format(Objects.requireNonNull(sdfTanggalIndo.parse(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tanggal.setText(tanggalLokal);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nominal.getText().toString().equals("") && keterangan.getText().toString().equals("")){
                    Toast.makeText(IncomeActivity.this, "Nominal dan Keterangan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else if(nominal.getText().toString().equals("")){
                    Toast.makeText(IncomeActivity.this, "Nominal Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else if(keterangan.getText().toString().equals("")){
                    Toast.makeText(IncomeActivity.this, "Keterangan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else if(nominal.getText().toString().length() < 3){
                    Toast.makeText(IncomeActivity.this, "Nominal dalam bentuk Rupiah, minimal Rp. 100", Toast.LENGTH_SHORT).show();
                } else {
                    Integer jumlah = Integer.valueOf(nominal.getText().toString());
                    DatabaseAccess dbacess = DatabaseAccess.getInstance(IncomeActivity.this);
                    dbacess.open();

                    boolean isInserted = dbacess.insertMoney(jumlah, keterangan.getText().toString(), tanggal.getText().toString(), "income");

                    if(isInserted){
                        Toast.makeText(IncomeActivity.this, "Berhasil Simpan Pemasukkan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(IncomeActivity.this, DashboardActivity.class));
                    } else {
                        Toast.makeText(IncomeActivity.this, "Gagal Simpan Pemasukkan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IncomeActivity.this, DashboardActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(IncomeActivity.this, DashboardActivity.class));
    }
}
