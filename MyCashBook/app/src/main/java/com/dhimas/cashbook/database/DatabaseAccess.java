package com.dhimas.cashbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dhimas.cashbook.main.model.DetailModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //open database
    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    //close databse
    public void close(){
        if(db != null){
            this.db.close();
        }
    }

    //delete specific table record
    public Integer Delete(String table, String where, String value){
        return db.delete(table, where, new String[]{value});
    }

    //delete al record in table
    public Cursor DeleteAll(String table){
        return db.rawQuery("DELETE FROM " + table, null);
    }

    public void hapusDataKeuangan(DetailModel detailModel) {
        this.db.delete("keuangan", "id =? ", new String[]{String.valueOf(detailModel.getId())});
        this.db.close();
    }

    public void tambahKeuangan(DetailModel detailModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put("jumlah", detailModel.getNominal());
        contentValues.put("keterangan", detailModel.getKeterangan());
        contentValues.put("tanggal", detailModel.getTanggal());
        contentValues.put("flow", detailModel.getFlow());
        this.db.insert("keuangan", null, contentValues);
        this.db.close();
    }
    //ambil semua data dari tabel
    public Cursor Get(String table){
        return db.rawQuery("SELECT * FROM " + table, null);
    }

    public List<DetailModel> getDataKeuangan(){
        List<DetailModel> list = new ArrayList<>();
        String selectKeuangan = "SELECT * FROM keuangan";
        open();
        Cursor cursor = this.db.rawQuery(selectKeuangan, null);
        if (cursor.moveToFirst()){
            do {
                DetailModel data = new DetailModel(cursor.getInt(0),cursor.getInt(1),cursor.getString(2), cursor.getString(3),cursor.getString(4));
                list.add(data);
            } while (cursor.moveToNext());
        }
        return list;
    }

    //ambil data jumlah dari tabel
    public Cursor Sum(String field, String table, String where){
        return db.rawQuery("SELECT SUM(" + field +") AS result FROM " + table + " WHERE " + where, null);
    }

    //mengambil data dengan kondisi tertentu
    public Cursor Where(String table, String where){
        return db.rawQuery("SELECT * FROM " + table + " WHERE " + where, null);
    }

    //menambahkan user
    public boolean insertUser(String username, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = db.insert("user", null, contentValues);
        return result != -1;
    }

    //mengupdate data user(password, username)
    public boolean updateUser(String password, String username){
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long result = db.update("user",  contentValues, "username=?", new String[]{username});
        return result != -1;
    }

    //memasukkan data ke tabel keuangan
    public boolean insertMoney(Integer jumlah, String keterangan, String tanggal, String flow){
        ContentValues contentValues = new ContentValues();
        contentValues.put("jumlah", jumlah);
        contentValues.put("keterangan", keterangan);
        contentValues.put("tanggal", tanggal);
        contentValues.put("flow", flow);
        long result = db.insert("keuangan", null, contentValues);
        return result != -1;
    }
}