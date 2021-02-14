package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.model.LabaRugiChild;
import com.sikbumdes.bumdes.model.LabaRugiExpense;
import com.sikbumdes.bumdes.model.LabaRugiIncome;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class LabaRugi_ExpenseAdapter extends RecyclerView.Adapter<LabaRugi_ExpenseAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<LabaRugiExpense> labaRugiExpenses;
    private ArrayList<LabaRugiChild> labaRugiChildArrayList = new ArrayList<>();

    public LabaRugi_ExpenseAdapter(Context context, ArrayList<LabaRugiExpense> labaRugiExpenses) {
        this.context = context;
        this.labaRugiExpenses = labaRugiExpenses;
    }

    @Override
    public LabaRugi_ExpenseAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_laba_rugi, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LabaRugi_ExpenseAdapter.CustomViewHolder holder, int position) {
        LabaRugiExpense labaRugiExpense = labaRugiExpenses.get(position);
        DecimalFormat fmt = new DecimalFormat();
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();
        fmts.setGroupingSeparator('.');
        fmt.setGroupingSize(3);
        fmt.setGroupingUsed(true);
        fmt.setDecimalFormatSymbols(fmts);

        holder.tv_class.setText(labaRugiExpense.getClass_name());
        holder.tv_total.setText(fmt.format(labaRugiExpense.getTotal()));

        int nameArrayLength = labaRugiExpense.getName().size();
        int i;

        //asign multiple array string to new arraylist
        for (i = 0; i < nameArrayLength; i++) {
            labaRugiChildArrayList.add(new LabaRugiChild(labaRugiExpense.getName().get(i), labaRugiExpense.getCode().get(i), labaRugiExpense.getBalance().get(i)));
        }
        holder.labaRugi_childAdapter = new LabaRugi_ChildAdapter(context, labaRugiChildArrayList);
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(context);
        holder.rv_laba_child.setLayoutManager(eLayoutManager);
        holder.rv_laba_child.setAdapter(holder.labaRugi_childAdapter);
        holder.labaRugi_childAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return labaRugiExpenses.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_class, tv_total;
        private RecyclerView rv_laba_child;
        private LabaRugi_ChildAdapter labaRugi_childAdapter;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_class = itemView.findViewById(R.id.tv_class);
            tv_total = itemView.findViewById(R.id.tv_total);
            rv_laba_child = itemView.findViewById(R.id.rv_laba_child);
        }
    }
}