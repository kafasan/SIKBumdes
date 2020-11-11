package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BukuBesarResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private BukuBesarData bukuBesarData;

    public BukuBesarResponse(boolean success, BukuBesarData bukuBesarData) {
        this.success = success;
        this.bukuBesarData = bukuBesarData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BukuBesarData getBukuBesarData() {
        return bukuBesarData;
    }

    public void setBukuBesarData(BukuBesarData bukuBesarData) {
        this.bukuBesarData = bukuBesarData;
    }
}
