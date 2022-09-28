package com.dhimas.cashbook.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dhimas.cashbook.R;
import com.dhimas.cashbook.database.DatabaseAccess;

public class SettingActivity extends AppCompatActivity {
    Button simpan, kembali;
    EditText oldpassword, newpassword, confirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        simpan = findViewById(R.id.simpan);
        kembali = findViewById(R.id.kembali);
        oldpassword = findViewById(R.id.oldpass);
        newpassword = findViewById(R.id.newpass);
        confirmation = findViewById(R.id.confirmation);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldpassword.getText().toString().equals("") || newpassword.getText().toString().equals("") || confirmation.getText().toString().equals("")){
                    oldpassword.setError("");
                    newpassword.setError("");
                    confirmation.setError("");
                    Toast.makeText(SettingActivity.this, "Lengkapi Data Anda", Toast.LENGTH_SHORT).show();
                } else {
                    if(!newpassword.getText().toString().equals(confirmation.getText().toString()) || newpassword.getText().toString().equals("")){
                        newpassword.setError("");
                        confirmation.setError("");
                        Toast.makeText(SettingActivity.this, "Password Baru dan Konfirmasi Tidak Sama / Kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseAccess dataBaseAccess = DatabaseAccess.getInstance(SettingActivity.this);
                        dataBaseAccess.open();

                        Cursor data = dataBaseAccess.Where("user", "username = 'USER' AND password ='" + oldpassword.getText().toString() + "'");

                        if (data.getCount() == 0) {
                            Toast.makeText(SettingActivity.this, "Password Lama yang Anda Masukkan Salah", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean isUpdated = dataBaseAccess.updateUser(newpassword.getText().toString(), "USER");

                            if(isUpdated){
                                Toast.makeText(SettingActivity.this, "Password Anda Berhasil Diganti", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SettingActivity.this, DashboardActivity.class));
                            } else {
                                Toast.makeText(SettingActivity.this, "Password Anda Gagal Diganti", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, DashboardActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingActivity.this, DashboardActivity.class));
    }
}
