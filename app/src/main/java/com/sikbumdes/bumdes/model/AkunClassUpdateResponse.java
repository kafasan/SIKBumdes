package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AkunClassUpdateResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private AkunClass akunClass;

    public AkunClassUpdateResponse(boolean success, AkunClass akunClass) {
        this.success = success;
        this.akunClass = akunClass;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AkunClass getAkunClass() {
        return akunClass;
    }

    public void setAkunClass(AkunClass akunClass) {
        this.akunClass = akunClass;
    }
}
