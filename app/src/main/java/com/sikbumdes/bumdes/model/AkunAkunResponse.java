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
    private ArrayList<AkunAkun> akunAkuns;

    public AkunAkunResponse(boolean success, ArrayList<AkunAkun> akunAkuns) {
        this.success = success;
        this.akunAkuns = akunAkuns;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<AkunAkun> getAkunAkuns() {
        return akunAkuns;
    }

    public void setAkunAkuns(ArrayList<AkunAkun> akunAkuns) {
        this.akunAkuns = akunAkuns;
    }
}
