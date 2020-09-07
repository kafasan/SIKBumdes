package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NeracaAwalBalance {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_account")
    @Expose
    private int id_account;

    @SerializedName("amount")
    @Expose
    private int amount;

    @SerializedName("date")
    @Expose
    private String date;

    public NeracaAwalBalance(int id, int id_account, int amount, String date) {
        this.id = id;
        this.id_account = id_account;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_account() {
        return id_account;
    }

    public void setId_account(int id_account) {
        this.id_account = id_account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
