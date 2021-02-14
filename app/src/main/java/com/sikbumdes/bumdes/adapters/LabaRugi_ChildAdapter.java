package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.model.LabaRugiChild;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LabaRugi_ChildAdapter extends RecyclerView.Adapter<LabaRugi_ChildAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<LabaRugiChild> labaRugiChildren;
    private List<Integer> colors;

    public LabaRugi_ChildAdapter(Context context, ArrayList<LabaRugiChild> labaRugiChildren) {
        this.context = context;
        this.labaRugiChildren = labaRugiChildren;
    }

    @Override
    public LabaRugi_ChildAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_laba_rugi_child, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LabaRugi_ChildAdapter.CustomViewHolder holder, int position) {
        LabaRugiChild labaRugiChild = labaRugiChildren.get(position);
        DecimalFormat fmt = new DecimalFormat();
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();
        fmts.setGroupingSeparator('.');
        fmt.setGroupingSize(3);
        fmt.setGroupingUsed(true);
        fmt.setDecimalFormatSymbols(fmts);

        holder.tv_name.setText(String.format("%s (%s)", labaRugiChild.getName(), labaRugiChild.getCode()));
        holder.tv_total.setText(fmt.format(labaRugiChild.getBalance()));

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
        return labaRugiChildren.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_total;
        private ImageView iv_circle;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_total = itemView.findViewById(R.id.tv_total);
            iv_circle = itemView.findViewById(R.id.iv_circle);
        }
    }
}