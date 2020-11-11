package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BukuBesarLogs {

    @SerializedName("saldo_awal")
    @Expose
    private int saldo_awal;

    @SerializedName("saldo_akhir")
    @Expose
    private int saldo_akhir;

    public BukuBesarLogs(int saldo_awal, int saldo_akhir) {
        this.saldo_awal = saldo_awal;
        this.saldo_akhir = saldo_akhir;
    }

    public int getSaldo_awal() {
        return saldo_awal;
    }

    public void setSaldo_awal(int saldo_awal) {
        this.saldo_awal = saldo_awal;
    }

    public int getSaldo_akhir() {
        return saldo_akhir;
    }

    public void setSaldo_akhir(int saldo_akhir) {
        this.saldo_akhir = saldo_akhir;
    }
}
