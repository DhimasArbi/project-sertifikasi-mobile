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

public class OutcomeActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_outcome);

        simpan = findViewById(R.id.simpan);
        kembali = findViewById(R.id.kembali);
        nominal = findViewById(R.id.nominal);
        keterangan = findViewById(R.id.keterangan);
        btntanggal = findViewById(R.id.btntanggal);
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(OutcomeActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                if(nominal.getText().toString().equals("") || keterangan.getText().toString().equals("")){
                    Toast.makeText(OutcomeActivity.this, "Harap Lengkapi Data Anda", Toast.LENGTH_SHORT).show();
                } else {
                    Integer jumlah = Integer.valueOf(nominal.getText().toString());

                    DatabaseAccess dbaccess = DatabaseAccess.getInstance(OutcomeActivity.this);
                    dbaccess.open();

                    boolean isInserted = dbaccess.insertMoney(jumlah, keterangan.getText().toString(), tanggal.getText().toString(), "outcome");

                    if(isInserted){
                        Toast.makeText(OutcomeActivity.this, "Berhasil Simpan Pengeluaran", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OutcomeActivity.this, DashboardActivity.class));
                    } else {
                        Toast.makeText(OutcomeActivity.this, "Gagal Simpan Pengeluaran", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OutcomeActivity.this, DashboardActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(OutcomeActivity.this, DashboardActivity.class));
    }
}
