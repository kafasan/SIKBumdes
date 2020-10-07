package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PerubahanEkuitas {

    @SerializedName("modal_awal")
    @Expose
    private int modal_awal;

    @SerializedName("penambahan")
    @Expose
    private ArrayList<PenambahanEkuitas> penambahanEkuitas;

    @SerializedName("total_penambahan")
    @Expose
    private int total_penambahan;

    @SerializedName("modal_akhir")
    @Expose
    private int modal_akhir;

    public PerubahanEkuitas(int modal_awal, ArrayList<PenambahanEkuitas> penambahanEkuitas, int total_penambahan, int modal_akhir) {
        this.modal_awal = modal_awal;
        this.penambahanEkuitas = penambahanEkuitas;
        this.total_penambahan = total_penambahan;
        this.modal_akhir = modal_akhir;
    }

    public int getModal_awal() {
        return modal_awal;
    }

    public void setModal_awal(int modal_awal) {
        this.modal_awal = modal_awal;
    }

    public ArrayList<PenambahanEkuitas> getPenambahanEkuitas() {
        return penambahanEkuitas;
    }

    public void setPenambahanEkuitas(ArrayList<PenambahanEkuitas> penambahanEkuitas) {
        this.penambahanEkuitas = penambahanEkuitas;
    }

    public int getTotal_penambahan() {
        return total_penambahan;
    }

    public void setTotal_penambahan(int total_penambahan) {
        this.total_penambahan = total_penambahan;
    }

    public int getModal_akhir() {
        return modal_akhir;
    }

    public void setModal_akhir(int modal_akhir) {
        this.modal_akhir = modal_akhir;
    }
}
