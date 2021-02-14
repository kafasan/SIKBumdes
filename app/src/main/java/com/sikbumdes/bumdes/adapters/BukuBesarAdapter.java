package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sikbumdes.bumdes.BukuBesarActivity;
import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.BukuBesar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class BukuBesarAdapter extends RecyclerView.Adapter<BukuBesarAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<BukuBesar> bukuBesarArrayList;

    public BukuBesarAdapter(Context context, ArrayList<BukuBesar> bukuBesarArrayList) {
        this.context = context;
        this.bukuBesarArrayList = bukuBesarArrayList;
    }

    @Override
    public BukuBesarAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_buku_besar, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BukuBesarAdapter.CustomViewHolder holder, int position) {
        BukuBesar bukuBesar = bukuBesarArrayList.get(position);

        if (position % 2 == 0) {
            holder.ll_buku.setBackgroundColor(context.getResources().getColor(R.color.white_smoke));
        } else {
            holder.ll_buku.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
        }

        holder.tv_date.setText(bukuBesar.getDate());
        holder.tv_keterangan.setText(bukuBesar.getDescription());

        DecimalFormat fmt = new DecimalFormat();
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();

        fmts.setGroupingSeparator('.');
        fmt.setGroupingSize(3);
        fmt.setGroupingUsed(true);
        fmt.setDecimalFormatSymbols(fmts);

        String pos = bukuBesar.getPosition();
        if (pos.equals("Debit")) {
            holder.tv_debit.setText(fmt.format(bukuBesar.getAmount()));
            holder.tv_kredit.setText("0");
        } else if (pos.equals("Kredit")){
            holder.tv_kredit.setText(fmt.format(bukuBesar.getAmount()));
            holder.tv_debit.setText("0");
        } else {
            holder.tv_debit.setText("0");
            holder.tv_kredit.setText("0");
        }

        holder.tv_saldo.setText(fmt.format(bukuBesar.getSaldo()));
    }

    @Override
    public int getItemCount() {
        return bukuBesarArrayList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_date, tv_keterangan, tv_debit, tv_kredit, tv_saldo;
        private LinearLayout ll_buku;
        private ProgressBar pb_loading;
        private String pos;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ll_buku = itemView.findViewById(R.id.ll_buku);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_keterangan = itemView.findViewById(R.id.tv_keterangan);
            tv_debit = itemView.findViewById(R.id.tv_debit);
            tv_kredit = itemView.findViewById(R.id.tv_kredit);
            tv_saldo = itemView.findViewById(R.id.tv_saldo);
//            pb_loading = itemView.findViewById(R.id.pb_loading);

        }
    }
}