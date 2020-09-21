package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusinessSessionResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("session")
    @Expose
    private Business business;

    public BusinessSessionResponse(boolean success, Business business) {
        this.success = success;
        this.business = business;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}
