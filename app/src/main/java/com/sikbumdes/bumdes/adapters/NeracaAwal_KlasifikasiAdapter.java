package com.sikbumdes.bumdes.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunAkunResponse;
import com.sikbumdes.bumdes.model.AkunClass;
import com.sikbumdes.bumdes.model.AkunClassUpdateResponse;
import com.sikbumdes.bumdes.model.AkunDeleteResponse;
import com.sikbumdes.bumdes.model.AkunParent;
import com.sikbumdes.bumdes.model.AkunParentResponse;
import com.sikbumdes.bumdes.model.NeracaAwalAkun;
import com.sikbumdes.bumdes.model.NeracaAwalClass;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NeracaAwal_KlasifikasiAdapter extends RecyclerView.Adapter<NeracaAwal_KlasifikasiAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<NeracaAwalClass> neracaAwalClasses;
    private ArrayList<NeracaAwalAkun> neracaAwalAkuns;

    public NeracaAwal_KlasifikasiAdapter(Context context, ArrayList<NeracaAwalClass> neracaAwalClasses, ArrayList<NeracaAwalAkun> neracaAwalAkuns) {
        this.context = context;
        this.neracaAwalClasses = neracaAwalClasses;
        this.neracaAwalAkuns = neracaAwalAkuns;
    }

    @Override
    public NeracaAwal_KlasifikasiAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_neraca_awal_class, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NeracaAwal_KlasifikasiAdapter.CustomViewHolder holder, int position) {
        NeracaAwalClass neracaAwalClass = neracaAwalClasses.get(position);

        holder.tv_class.setText(neracaAwalClass.getClassification_name());
        neracaAwalAkuns = neracaAwalClass.getNeracaAwalAkuns();

        holder.neracaAwal_akunAdapter = new NeracaAwal_AkunAdapter(context, neracaAwalAkuns);
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(context);
        holder.rv_neracaawal_akun.setLayoutManager(eLayoutManager);
        holder.rv_neracaawal_akun.setItemAnimator(new DefaultItemAnimator());
        holder.rv_neracaawal_akun.setAdapter(holder.neracaAwal_akunAdapter);
        if (neracaAwalClasses.isEmpty()) {
            holder.tv_no_data.setVisibility(View.VISIBLE);
            holder.rv_neracaawal_akun.setVisibility(View.GONE);
        } else {
            holder.tv_no_data.setVisibility(View.GONE);
            holder.rv_neracaawal_akun.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return neracaAwalClasses.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_class, tv_no_data;
        RecyclerView rv_neracaawal_akun;
        private NeracaAwal_AkunAdapter neracaAwal_akunAdapter;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_class = itemView.findViewById(R.id.tv_class);
            tv_no_data = itemView.findViewById(R.id.tv_no_data);
            rv_neracaawal_akun = itemView.findViewById(R.id.rv_neracaawal_akun);

            /*
            if (eLayoutManager.getItemCount() == 0) {
                ll_akun_class.setOnClickListener(null);
            } else {
                ll_akun_class.setOnClickListener(this);
            }
             */
        }
    }
}
