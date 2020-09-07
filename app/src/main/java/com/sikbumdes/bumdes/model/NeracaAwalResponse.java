package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NeracaAwalResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private NeracaAwal neracaAwal;

    public NeracaAwalResponse(boolean success, NeracaAwal neracaAwal) {
        this.success = success;
        this.neracaAwal = neracaAwal;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public NeracaAwal getNeracaAwal() {
        return neracaAwal;
    }

    public void setNeracaAwal(NeracaAwal neracaAwal) {
        this.neracaAwal = neracaAwal;
    }
}
