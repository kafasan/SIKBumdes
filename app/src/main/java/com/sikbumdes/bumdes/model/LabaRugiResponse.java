package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabaRugiResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private LabaRugiData labaRugiData;

    public LabaRugiResponse(boolean success, LabaRugiData labaRugiData) {
        this.success = success;
        this.labaRugiData = labaRugiData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LabaRugiData getLabaRugiData() {
        return labaRugiData;
    }

    public void setLabaRugiData(LabaRugiData labaRugiData) {
        this.labaRugiData = labaRugiData;
    }
}
