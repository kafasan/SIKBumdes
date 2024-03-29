package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.model.LabaRugiChild;
import com.sikbumdes.bumdes.model.LabaRugiIncome;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LabaRugi_IncomeAdapter extends RecyclerView.Adapter<LabaRugi_IncomeAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<LabaRugiIncome> labaRugiIncomes;
    private ArrayList<LabaRugiChild> labaRugiChildArrayList = new ArrayList<>();

    public LabaRugi_IncomeAdapter(Context context, ArrayList<LabaRugiIncome> labaRugiIncomes) {
        this.context = context;
        this.labaRugiIncomes = labaRugiIncomes;
    }

    @Override
    public LabaRugi_IncomeAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_laba_rugi, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LabaRugi_IncomeAdapter.CustomViewHolder holder, int position) {
        LabaRugiIncome labaRugiIncome = labaRugiIncomes.get(position);
        DecimalFormat fmt = new DecimalFormat();
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();
        fmts.setGroupingSeparator('.');
        fmt.setGroupingSize(3);
        fmt.setGroupingUsed(true);
        fmt.setDecimalFormatSymbols(fmts);

        holder.tv_class.setText(labaRugiIncome.getClass_name());
        holder.tv_total.setText(fmt.format(labaRugiIncome.getTotal()));

        int nameArrayLength = labaRugiIncome.getName().size();
        int i;

        //asign multiple array string to new arraylist
        for (i = 0; i < nameArrayLength; i++) {
            labaRugiChildArrayList.add(new LabaRugiChild(labaRugiIncome.getName().get(i), labaRugiIncome.getCode().get(i), labaRugiIncome.getBalance().get(i)));
        }
        holder.labaRugi_childAdapter = new LabaRugi_ChildAdapter(context, labaRugiChildArrayList);
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(context);
        holder.rv_laba_child.setLayoutManager(eLayoutManager);
        holder.rv_laba_child.setAdapter(holder.labaRugi_childAdapter);
        holder.labaRugi_childAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return labaRugiIncomes.size();
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