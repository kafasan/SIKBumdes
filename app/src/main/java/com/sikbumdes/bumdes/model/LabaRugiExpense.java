package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LabaRugiExpense {

    @SerializedName("name")
    @Expose
    private ArrayList<String> name;

    @SerializedName("code")
    @Expose
    private ArrayList<String> code;

    @SerializedName("ending_balance")
    @Expose
    private ArrayList<Integer> balance;

    public LabaRugiExpense(ArrayList<String> name, ArrayList<String> code, ArrayList<Integer> balance) {
        this.name = name;
        this.code = code;
        this.balance = balance;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<String> getCode() {
        return code;
    }

    public void setCode(ArrayList<String> code) {
        this.code = code;
    }

    public ArrayList<Integer> getBalance() {
        return balance;
    }

    public void setBalance(ArrayList<Integer> balance) {
        this.balance = balance;
    }
}
