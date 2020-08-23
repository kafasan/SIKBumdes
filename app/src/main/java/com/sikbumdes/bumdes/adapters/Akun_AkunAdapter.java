package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sikbumdes.bumdes.DataAkunActivity;
import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.model.AkunAkun;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Akun_AkunAdapter extends RecyclerView.Adapter<Akun_AkunAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<AkunAkun> akunAkuns;
    private List<Integer> colors;

    public Akun_AkunAdapter(Context context, ArrayList<AkunAkun> akunAkuns) {
        this.context = context;
        this.akunAkuns = akunAkuns;
    }

    @Override
    public Akun_AkunAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_akun_akun, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Akun_AkunAdapter.CustomViewHolder holder, int position) {
        holder.tv_akun.setText(akunAkuns.get(position).getAccount_name() + " (" + akunAkuns.get(position).getAccount_code() + ")");

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
        return akunAkuns.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_akun;
        private ImageView iv_edit, iv_circle;
        private Context context;

        public CustomViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_akun = itemView.findViewById(R.id.tv_akun);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_circle = itemView.findViewById(R.id.iv_circle);
            iv_edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(context, iv_edit);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.menu_edit:
//                            editAkun();
                            return true;
                        case  R.id.menu_delete:
//                            deleteAkun();
                            return true;
                    }
                    return false;
                }
            });
            popupMenu.inflate(R.menu.akun_edit_menu);
            popupMenu.show();
        }
    }
}
