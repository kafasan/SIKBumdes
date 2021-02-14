package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LabaRugiChild {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("ending_balance")
    @Expose
    private int balance;

    public LabaRugiChild(String name, String code, int balance) {
        this.name = name;
        this.code = code;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Laba{" +
                "names='" + name + '\'' +
                ", codes='" + code + '\'' +
                ", amounts='" + balance + '\'' +
                '}';
    }
}
