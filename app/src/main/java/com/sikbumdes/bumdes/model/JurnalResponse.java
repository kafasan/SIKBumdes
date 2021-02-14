package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JurnalResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private JurnalData jurnalData;

    public JurnalResponse(boolean success, JurnalData jurnalData) {
        this.success = success;
        this.jurnalData = jurnalData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JurnalData getJurnalData() {
        return jurnalData;
    }

    public void setJurnalData(JurnalData jurnalData) {
        this.jurnalData = jurnalData;
    }
}
