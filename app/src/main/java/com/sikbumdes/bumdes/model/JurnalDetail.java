package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JurnalDetail {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_detail")
    @Expose
    private int id_detail;

    @SerializedName("id_account")
    @Expose
    private int id_account;

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("amount")
    @Expose
    private int amount;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("account")
    @Expose
    private AkunAkun akunAkun;

    public JurnalDetail(int id, int id_detail, int id_account, String position, int amount, String created_at, String updated_at, AkunAkun akunAkun) {
        this.id = id;
        this.id_detail = id_detail;
        this.id_account = id_account;
        this.position = position;
        this.amount = amount;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.akunAkun = akunAkun;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public int getId_account() {
        return id_account;
    }

    public void setId_account(int id_account) {
        this.id_account = id_account;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public AkunAkun getAkunAkun() {
        return akunAkun;
    }

    public void setAkunAkun(AkunAkun akunAkun) {
        this.akunAkun = akunAkun;
    }
}
