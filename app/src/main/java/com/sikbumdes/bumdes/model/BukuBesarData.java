package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BukuBesarData {

    @SerializedName("logs")
    @Expose
    private BukuBesarLogs bukuBesarLogs;

    @SerializedName("buku_besar")
    @Expose
    private ArrayList<BukuBesar> bukuBesarArrayList;

    public BukuBesarData(BukuBesarLogs bukuBesarLogs, ArrayList<BukuBesar> bukuBesarArrayList) {
        this.bukuBesarLogs = bukuBesarLogs;
        this.bukuBesarArrayList = bukuBesarArrayList;
    }

    public BukuBesarLogs getBukuBesarLogs() {
        return bukuBesarLogs;
    }

    public void setBukuBesarLogs(BukuBesarLogs bukuBesarLogs) {
        this.bukuBesarLogs = bukuBesarLogs;
    }

    public ArrayList<BukuBesar> getBukuBesarArrayList() {
        return bukuBesarArrayList;
    }

    public void setBukuBesarArrayList(ArrayList<BukuBesar> bukuBesarArrayList) {
        this.bukuBesarArrayList = bukuBesarArrayList;
    }
}
