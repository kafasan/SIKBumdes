package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LabaRugiExpense {

    @SerializedName("classification")
    @Expose
    private String class_name;

    @SerializedName("name")
    @Expose
    private ArrayList<String> name;

    @SerializedName("code")
    @Expose
    private ArrayList<String> code;

    @SerializedName("ending_balance")
    @Expose
    private ArrayList<Integer> balance;

    @SerializedName("total")
    @Expose
    private int total;

    public LabaRugiExpense(String class_name, ArrayList<String> name, ArrayList<String> code, ArrayList<Integer> balance, int total) {
        this.class_name = class_name;
        this.name = name;
        this.code = code;
        this.balance = balance;
        this.total = total;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
