package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NeracaAwalAkun {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_classification")
    @Expose
    private int id_classification;

    @SerializedName("account_code")
    @Expose
    private String account_code;

    @SerializedName("account_name")
    @Expose
    private String account_name;

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("initial_balance")
    @Expose
    private ArrayList<NeracaAwalBalance> neracaAwalBalances;

    public NeracaAwalAkun(int id, int id_classification, String account_code, String account_name, String position, ArrayList<NeracaAwalBalance> neracaAwalBalances) {
        this.id = id;
        this.id_classification = id_classification;
        this.account_code = account_code;
        this.account_name = account_name;
        this.position = position;
        this.neracaAwalBalances = neracaAwalBalances;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_classification() {
        return id_classification;
    }

    public void setId_classification(int id_classification) {
        this.id_classification = id_classification;
    }

    public String getAccount_code() {
        return account_code;
    }

    public void setAccount_code(String account_code) {
        this.account_code = account_code;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ArrayList<NeracaAwalBalance> getNeracaAwalBalances() {
        return neracaAwalBalances;
    }

    public void setNeracaAwalBalances(ArrayList<NeracaAwalBalance> neracaAwalBalances) {
        this.neracaAwalBalances = neracaAwalBalances;
    }
}
