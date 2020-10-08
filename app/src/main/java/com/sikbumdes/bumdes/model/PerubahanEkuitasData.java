package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PerubahanEkuitasData {

    @SerializedName("perubahan_ekuitas")
    @Expose
    private PerubahanEkuitas perubahanEkuitas;

    public PerubahanEkuitasData(PerubahanEkuitas perubahanEkuitas) {
        this.perubahanEkuitas = perubahanEkuitas;
    }

    public PerubahanEkuitas getPerubahanEkuitas() {
        return perubahanEkuitas;
    }

    public void setPerubahanEkuitas(PerubahanEkuitas perubahanEkuitas) {
        this.perubahanEkuitas = perubahanEkuitas;
    }
}
