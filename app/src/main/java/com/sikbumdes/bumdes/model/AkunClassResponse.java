package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AkunClassResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private ArrayList<AkunClass> akunClasses;

    public AkunClassResponse(boolean success, ArrayList<AkunClass> akunClasses) {
        this.success = success;
        this.akunClasses = akunClasses;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<AkunClass> getAkunClasses() {
        return akunClasses;
    }

    public void setAkunClasses(ArrayList<AkunClass> akunClasses) {
        this.akunClasses = akunClasses;
    }
}
