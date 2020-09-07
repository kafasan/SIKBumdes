package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NeracaAwalClass {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("id_parent")
    @Expose
    private int id_parent;

    @SerializedName("classification_code")
    @Expose
    private String classification_code;

    @SerializedName("classification_name")
    @Expose
    private String classification_name;

    @SerializedName("account")
    @Expose
    private ArrayList<NeracaAwalAkun> neracaAwalAkuns;

    public NeracaAwalClass(int id, int id_parent, String classification_code, String classification_name, ArrayList<NeracaAwalAkun> neracaAwalAkuns) {
        this.id = id;
        this.id_parent = id_parent;
        this.classification_code = classification_code;
        this.classification_name = classification_name;
        this.neracaAwalAkuns = neracaAwalAkuns;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_parent() {
        return id_parent;
    }

    public void setId_parent(int id_parent) {
        this.id_parent = id_parent;
    }

    public String getClassification_code() {
        return classification_code;
    }

    public void setClassification_code(String classification_code) {
        this.classification_code = classification_code;
    }

    public String getClassification_name() {
        return classification_name;
    }

    public void setClassification_name(String classification_name) {
        this.classification_name = classification_name;
    }

    public ArrayList<NeracaAwalAkun> getNeracaAwalAkuns() {
        return neracaAwalAkuns;
    }

    public void setNeracaAwalAkuns(ArrayList<NeracaAwalAkun> neracaAwalAkuns) {
        this.neracaAwalAkuns = neracaAwalAkuns;
    }
}
