package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusinessResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("business")
    @Expose
    private ArrayList<Business> businesses;

    public BusinessResponse(boolean success, ArrayList<Business> businesses) {
        this.success = success;
        this.businesses = businesses;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(ArrayList<Business> businesses) {
        this.businesses = businesses;
    }
}
