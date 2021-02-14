package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JurnalData {
    @SerializedName("jurnal_umum")
    @Expose
    private ArrayList<Jurnal> jurnalArrayList;

    public JurnalData(ArrayList<Jurnal> jurnalArrayList) {
        this.jurnalArrayList = jurnalArrayList;
    }

    public ArrayList<Jurnal> getJurnalArrayList() {
        return jurnalArrayList;
    }

    public void setJurnalArrayList(ArrayList<Jurnal> jurnalArrayList) {
        this.jurnalArrayList = jurnalArrayList;
    }
}
