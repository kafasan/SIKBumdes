package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AkunParentResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("data")
    @Expose
    private ArrayList<AkunParent> akunParents;

    public AkunParentResponse(boolean success, ArrayList<AkunParent> akunParents) {
        this.success = success;
        this.akunParents = akunParents;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<AkunParent> getAkunParents() {
        return akunParents;
    }

    public void setAkunParents(ArrayList<AkunParent> akunParents) {
        this.akunParents = akunParents;
    }
}
