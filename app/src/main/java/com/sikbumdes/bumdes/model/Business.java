package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Business {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_company")
    @Expose
    private int id_company;

    @SerializedName("business_name")
    @Expose
    private String business_name;

    public Business(int id, int id_company, String business_name) {
        this.id = id;
        this.id_company = id_company;
        this.business_name = business_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_company() {
        return id_company;
    }

    public void setId_company(int id_company) {
        this.id_company = id_company;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }
}
