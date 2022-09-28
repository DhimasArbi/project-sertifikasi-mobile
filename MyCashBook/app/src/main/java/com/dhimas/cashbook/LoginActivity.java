package com.dhimas.cashbook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.dhimas.cashbook.database.DatabaseAccess;


public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText username, password;
    TextView reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        reset = findViewById(R.id.reset_password);

        cekUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("") || password.getText().toString().equals("")){
                    username.setError("");
                    password.setError("");
                    Toast.makeText(LoginActivity.this, "Username atau Password Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseAccess dbaccess = DatabaseAccess.getInstance(LoginActivity.this);
                    dbaccess.open();

                    Cursor data = dbaccess.Where("user", "username = '" + username.getText().toString().toUpperCase() + "' AND password = '" + password.getText().toString() + "'");

                    if(data.getCount() == 0){
                        Toast.makeText(LoginActivity.this, "Username atau Password Salah!", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class)); //masuk ke dashboard
                    }
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseAccess dbaccess = DatabaseAccess.getInstance(LoginActivity.this);
                dbaccess.open();

                dbaccess.DeleteAll("user").getCount();

                cekUser();
            }
        });
    }

    private void cekUser() {
        DatabaseAccess dataBaseAccess = DatabaseAccess.getInstance(LoginActivity.this);
        dataBaseAccess.open();

        Cursor data = dataBaseAccess.Get("user");

        if(data.getCount() == 0){
            boolean isInserted = dataBaseAccess.insertUser("USER", "user");

            if(isInserted){
                Toast.makeText(LoginActivity.this, "User Dibuat", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "User Gagal Dibuat", Toast.LENGTH_SHORT).show();
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
