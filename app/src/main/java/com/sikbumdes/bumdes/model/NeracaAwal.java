package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NeracaAwal {

    @SerializedName("total_debit")
    @Expose
    private int total_debit;

    @SerializedName("total_kredit")
    @Expose
    private int total_kredit;

    @SerializedName("business")
    @Expose
    private Business business;

    @SerializedName("neraca_awal")
    @Expose
    private ArrayList<NeracaAwalParent> neracaAwalParents;

    public NeracaAwal(int total_debit, int total_kredit, Business business, ArrayList<NeracaAwalParent> neracaAwalParents) {
        this.total_debit = total_debit;
        this.total_kredit = total_kredit;
        this.business = business;
        this.neracaAwalParents = neracaAwalParents;
    }

    public int getTotal_debit() {
        return total_debit;
    }

    public void setTotal_debit(int total_debit) {
        this.total_debit = total_debit;
    }

    public int getTotal_kredit() {
        return total_kredit;
    }

    public void setTotal_kredit(int total_kredit) {
        this.total_kredit = total_kredit;
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
