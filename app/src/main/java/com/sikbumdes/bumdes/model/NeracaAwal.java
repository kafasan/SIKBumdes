package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NeracaAwal {

    @SerializedName("business")
    @Expose
    private Business business;

    @SerializedName("neraca_awal")
    @Expose
    private ArrayList<NeracaAwalParent> neracaAwalParents;

    public NeracaAwal(Business business, ArrayList<NeracaAwalParent> neracaAwalParents) {
        this.business = business;
        this.neracaAwalParents = neracaAwalParents;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public ArrayList<NeracaAwalParent> getNeracaAwalParents() {
        return neracaAwalParents;
    }

    public void setNeracaAwalParents(ArrayList<NeracaAwalParent> neracaAwalParents) {
        this.neracaAwalParents = neracaAwalParents;
    }
}
