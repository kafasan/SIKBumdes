package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.model.BukuBesar;
import com.sikbumdes.bumdes.model.Jurnal;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class JurnalAdapter extends RecyclerView.Adapter<JurnalAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<Jurnal> jurnalArrayList;

    public JurnalAdapter(Context context, ArrayList<Jurnal> jurnalArrayList) {
        this.context = context;
        this.jurnalArrayList = jurnalArrayList;
    }

    @Override
    public JurnalAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_jurnal, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final JurnalAdapter.CustomViewHolder holder, int position) {
        Jurnal jurnal = jurnalArrayList.get(position);

        holder.tv_receipt.setText(jurnal.getReceipt());
        holder.tv_desc.setText(jurnal.getDescription());
        holder.tv_account.setText(String.format("(%s) %s", jurnal.getJurnalDetailArrayList().get(0).getAkunAkun().getAccount_code(),
                jurnal.getJurnalDetailArrayList().get(0).getAkunAkun().getAccount_name()));

        DecimalFormat fmt = new DecimalFormat();
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();

        fmts.setGroupingSeparator('.');
        fmt.setGroupingSize(3);
        fmt.setGroupingUsed(true);
        fmt.setDecimalFormatSymbols(fmts);

        String pos = jurnal.getJurnalDetailArrayList().get(0).getPosition();
        if (pos.equals("Debit")) {
            holder.tv_debit.setText(fmt.format(jurnal.getJurnalDetailArrayList().get(0).getAmount()));
            holder.tv_kredit.setText("0");
        } else if (pos.equals("Kredit")) {
            holder.tv_kredit.setText(fmt.format(jurnal.getJurnalDetailArrayList().get(0).getAmount()));
            holder.tv_debit.setText("0");
        } else {
            holder.tv_debit.setText("0");
            holder.tv_kredit.setText("0");
        }
    }

    @Override
    public int getItemCount() {
        return jurnalArrayList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_receipt, tv_desc, tv_debit, tv_kredit, tv_account;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_receipt = itemView.findViewById(R.id.tv_receipt);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_debit = itemView.findViewById(R.id.tv_debit);
            tv_kredit = itemView.findViewById(R.id.tv_kredit);
            tv_account = itemView.findViewById(R.id.tv_account);

        }
    }
}