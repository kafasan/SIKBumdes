package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NeracaAwalStoreResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private NeracaAwalBalance neracaAwalBalance;

    public NeracaAwalStoreResponse(boolean success, NeracaAwalBalance neracaAwalBalance) {
        this.success = success;
        this.neracaAwalBalance = neracaAwalBalance;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public NeracaAwalBalance getNeracaAwalBalance() {
        return neracaAwalBalance;
    }

    public void setNeracaAwalBalance(NeracaAwalBalance neracaAwalBalance) {
        this.neracaAwalBalance = neracaAwalBalance;
    }
}
