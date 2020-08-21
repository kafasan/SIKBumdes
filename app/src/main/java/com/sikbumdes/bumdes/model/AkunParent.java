package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AkunParent {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_business")
    @Expose
    private int id_business;

    @SerializedName("parent_code")
    @Expose
    private int parent_code;

    @SerializedName("parent_name")
    @Expose
    private String parent_name;

    public AkunParent(int id, int id_business, int parent_code, String parent_name) {
        this.id = id;
        this.id_business = id_business;
        this.parent_code = parent_code;
        this.parent_name = parent_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_business() {
        return id_business;
    }

    public void setId_business(int id_business) {
        this.id_business = id_business;
    }

    public int getParent_code() {
        return parent_code;
    }

    public void setParent_code(int parent_code) {
        this.parent_code = parent_code;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }
}
