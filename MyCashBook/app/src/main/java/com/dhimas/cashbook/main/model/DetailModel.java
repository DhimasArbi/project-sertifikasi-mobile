package com.dhimas.cashbook.main.model;

public class DetailModel {
    Integer Nominal, id;
    String Keterangan, Tanggal, Flow;
    public DetailModel(){}
    public DetailModel(Integer id, Integer Nominal, String Keterangan, String Tanggal, String Flow){
        this.id = id;
        this.Nominal = Nominal;
        this.Keterangan = Keterangan;
        this.Tanggal = Tanggal;
        this.Flow = Flow;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNominal() {
        return Nominal;
    }

    public String getFlow() {
        return Flow;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFlow(String flow) {
        this.Flow = flow;
    }

    public void setKeterangan(String keterangan) {
        this.Keterangan = keterangan;
    }

    public void setNominal(Integer nominal) {
        this.Nominal = nominal;
    }

    public void setTanggal(String tanggal) {
        this.Tanggal = tanggal;
    }
}
