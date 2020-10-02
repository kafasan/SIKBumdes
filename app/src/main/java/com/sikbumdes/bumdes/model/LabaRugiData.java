package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabaRugiData {

    @SerializedName("available_year")
    @Expose
    private int available_year;

    @SerializedName("laba_rugi")
    @Expose
    private LabaRugi labaRugi;

    public LabaRugiData(int available_year, LabaRugi labaRugi) {
        this.available_year = available_year;
        this.labaRugi = labaRugi;
    }

    public int getAvailable_year() {
        return available_year;
    }

    public void setAvailable_year(int available_year) {
        this.available_year = available_year;
    }

    public LabaRugi getLabaRugi() {
        return labaRugi;
    }

    public void setLabaRugi(LabaRugi labaRugi) {
        this.labaRugi = labaRugi;
    }
}
