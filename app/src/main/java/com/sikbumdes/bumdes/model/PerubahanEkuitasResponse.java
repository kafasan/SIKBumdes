package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerubahanEkuitasResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private PerubahanEkuitas perubahanEkuitas;

    public PerubahanEkuitasResponse(boolean success, PerubahanEkuitas perubahanEkuitas) {
        this.success = success;
        this.perubahanEkuitas = perubahanEkuitas;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PerubahanEkuitas getPerubahanEkuitas() {
        return perubahanEkuitas;
    }

    public void setPerubahanEkuitas(PerubahanEkuitas perubahanEkuitas) {
        this.perubahanEkuitas = perubahanEkuitas;
    }
}
