package com.sikbumdes.bumdes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LabaRugi {

    @SerializedName("income")
    @Expose
    private ArrayList<LabaRugiIncome> incomeArrayList;

    @SerializedName("income")
    @Expose
    private ArrayList<LabaRugiExpense> expenseArrayList;

    @SerializedName("income")
    @Expose
    private ArrayList<LabaRugiOtherIncome> otherIncomeArrayList;

    @SerializedName("income")
    @Expose
    private ArrayList<LabaRugiOtherExpense> otherExpenseArrayList;

    @SerializedName("laba_usaha")
    @Expose
    private int laba_usaha;

    @SerializedName("laba_berjalan")
    @Expose
    private int laba_berjalan;

    public LabaRugi(ArrayList<LabaRugiIncome> incomeArrayList, ArrayList<LabaRugiExpense> expenseArrayList, ArrayList<LabaRugiOtherIncome> otherIncomeArrayList, ArrayList<LabaRugiOtherExpense> otherExpenseArrayList, int laba_usaha, int laba_berjalan) {
        this.incomeArrayList = incomeArrayList;
        this.expenseArrayList = expenseArrayList;
        this.otherIncomeArrayList = otherIncomeArrayList;
        this.otherExpenseArrayList = otherExpenseArrayList;
        this.laba_usaha = laba_usaha;
        this.laba_berjalan = laba_berjalan;
    }

    public ArrayList<LabaRugiIncome> getIncomeArrayList() {
        return incomeArrayList;
    }

    public void setIncomeArrayList(ArrayList<LabaRugiIncome> incomeArrayList) {
        this.incomeArrayList = incomeArrayList;
    }

    public ArrayList<LabaRugiExpense> getExpenseArrayList() {
        return expenseArrayList;
    }

    public void setExpenseArrayList(ArrayList<LabaRugiExpense> expenseArrayList) {
        this.expenseArrayList = expenseArrayList;
    }

    public ArrayList<LabaRugiOtherIncome> getOtherIncomeArrayList() {
        return otherIncomeArrayList;
    }

    public void setOtherIncomeArrayList(ArrayList<LabaRugiOtherIncome> otherIncomeArrayList) {
        this.otherIncomeArrayList = otherIncomeArrayList;
    }

    public ArrayList<LabaRugiOtherExpense> getOtherExpenseArrayList() {
        return otherExpenseArrayList;
    }

    public void setOtherExpenseArrayList(ArrayList<LabaRugiOtherExpense> otherExpenseArrayList) {
        this.otherExpenseArrayList = otherExpenseArrayList;
    }

    public int getLaba_usaha() {
        return laba_usaha;
    }

    public void setLaba_usaha(int laba_usaha) {
        this.laba_usaha = laba_usaha;
    }

    public int getLaba_berjalan() {
        return laba_berjalan;
    }

    public void setLaba_berjalan(int laba_berjalan) {
        this.laba_berjalan = laba_berjalan;
    }
}
