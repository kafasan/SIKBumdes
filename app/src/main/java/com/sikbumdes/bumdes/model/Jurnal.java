package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Jurnal {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("receipt")
    @Expose
    private String receipt;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("journal")
    @Expose
    private ArrayList<JurnalDetail> jurnalDetailArrayList;

    public Jurnal(int id, String receipt, String description, String date, String created_at, String updated_at, ArrayList<JurnalDetail> jurnalDetailArrayList) {
        this.id = id;
        this.receipt = receipt;
        this.description = description;
        this.date = date;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.jurnalDetailArrayList = jurnalDetailArrayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<JurnalDetail> getJurnalDetailArrayList() {
        return jurnalDetailArrayList;
    }

    public void setJurnalDetailArrayList(ArrayList<JurnalDetail> jurnalDetailArrayList) {
        this.jurnalDetailArrayList = jurnalDetailArrayList;
    }
}
