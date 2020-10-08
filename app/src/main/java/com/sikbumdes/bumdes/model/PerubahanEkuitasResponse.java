package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerubahanEkuitasResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private PerubahanEkuitasData perubahanEkuitasData;

    public PerubahanEkuitasResponse(boolean success, PerubahanEkuitasData perubahanEkuitasData) {
        this.success = success;
        this.perubahanEkuitasData = perubahanEkuitasData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PerubahanEkuitasData getPerubahanEkuitasData() {
        return perubahanEkuitasData;
    }

    public void setPerubahanEkuitasData(PerubahanEkuitasData perubahanEkuitasData) {
        this.perubahanEkuitasData = perubahanEkuitasData;
    }
}
