package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabaRugiData {

    @SerializedName("laba_rugi")
    @Expose
    private LabaRugi labaRugi;

    public LabaRugiData(LabaRugi labaRugi) {
        this.labaRugi = labaRugi;
    }

    public LabaRugi getLabaRugi() {
        return labaRugi;
    }

    public void setLabaRugi(LabaRugi labaRugi) {
        this.labaRugi = labaRugi;
    }
}
