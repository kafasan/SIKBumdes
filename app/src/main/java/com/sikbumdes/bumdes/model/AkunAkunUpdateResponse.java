package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AkunAkunUpdateResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private AkunAkun akunAkun;

    public AkunAkunUpdateResponse(boolean success, AkunAkun akunAkun) {
        this.success = success;
        this.akunAkun = akunAkun;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AkunAkun getAkunAkun() {
        return akunAkun;
    }

    public void setAkunAkun(AkunAkun akunAkun) {
        this.akunAkun = akunAkun;
    }
}
