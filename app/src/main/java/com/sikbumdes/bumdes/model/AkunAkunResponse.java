package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AkunAkunResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private ArrayList<AkunAkun> akunClasses;

    public AkunAkunResponse(boolean success, ArrayList<AkunAkun> akunClasses) {
        this.success = success;
        this.akunClasses = akunClasses;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<AkunAkun> getAkunClasses() {
        return akunClasses;
    }

    public void setAkunClasses(ArrayList<AkunAkun> akunClasses) {
        this.akunClasses = akunClasses;
    }
}
