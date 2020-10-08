package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.model.LabaRugiIncome;
import com.sikbumdes.bumdes.model.PenambahanEkuitas;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.recyclerview.widget.RecyclerView;

public class PenambahanEkuitasAdapter extends RecyclerView.Adapter<PenambahanEkuitasAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<PenambahanEkuitas> penambahanEkuitasArrayList;
    private List<Integer> colors;

    public PenambahanEkuitasAdapter(Context context, ArrayList<PenambahanEkuitas> penambahanEkuitasArrayList) {
        this.context = context;
        this.penambahanEkuitasArrayList = penambahanEkuitasArrayList;
    }

    @Override
    public PenambahanEkuitasAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_penambahan_ekuitas, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PenambahanEkuitasAdapter.CustomViewHolder holder, int position) {
        PenambahanEkuitas penambahanEkuitas = penambahanEkuitasArrayList.get(position);

        DecimalFormat fmt = new DecimalFormat();
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();
        fmts.setGroupingSeparator('.');
        fmt.setGroupingSize(3);
        fmt.setGroupingUsed(true);
        fmt.setDecimalFormatSymbols(fmts);

        holder.tv_name.setText(penambahanEkuitas.getName());
        holder.tv_amount.setText(fmt.format(penambahanEkuitas.getAmount()));
        colors = new ArrayList<Integer>();

        colors.add(context.getResources().getColor(R.color.colorPastel_1));
        colors.add(context.getResources().getColor(R.color.colorPastel_2));
        colors.add(context.getResources().getColor(R.color.colorPastel_3));
        colors.add(context.getResources().getColor(R.color.colorPastel_4));
        colors.add(context.getResources().getColor(R.color.colorPastel_5));
        colors.add(context.getResources().getColor(R.color.colorPastel_6));
        colors.add(context.getResources().getColor(R.color.colorPastel_7));
        colors.add(context.getResources().getColor(R.color.colorPastel_8));
        colors.add(context.getResources().getColor(R.color.colorPastel_9));

        Random r = new Random();
        int i = r.nextInt(colors.size());

        holder.iv_circle.setColorFilter(colors.get(i));
    }

    @Override
    public int getItemCount() {
        return penambahanEkuitasArrayList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_amount;
        private ImageView iv_circle;
        private ProgressBar pb_loading;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            iv_circle = itemView.findViewById(R.id.iv_circle);
            pb_loading = itemView.findViewById(R.id.pb_loading);
        }
    }
}