package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunClass;
import com.sikbumdes.bumdes.model.AkunClassResponse;
import com.sikbumdes.bumdes.model.AkunParent;
import com.sikbumdes.bumdes.model.NeracaAwalAkun;
import com.sikbumdes.bumdes.model.NeracaAwalClass;
import com.sikbumdes.bumdes.model.NeracaAwalParent;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NeracaAwal_ParentAdapter extends RecyclerView.Adapter<NeracaAwal_ParentAdapter.ViewHolder> {

    Context context;
    private ArrayList<NeracaAwalParent> neracaAwalParents;
    private ArrayList<NeracaAwalClass> neracaAwalClasses;
    private ArrayList<NeracaAwalAkun> neracaAwalAkuns;

    public NeracaAwal_ParentAdapter(Context context, ArrayList<NeracaAwalParent> neracaAwalParents, ArrayList<NeracaAwalClass> neracaAwalClasses, ArrayList<NeracaAwalAkun> neracaAwalAkuns) {
        this.context = context;
        this.neracaAwalParents = neracaAwalParents;
        this.neracaAwalClasses = neracaAwalClasses;
        this.neracaAwalAkuns = neracaAwalAkuns;
    }

    @Override
    public NeracaAwal_ParentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_neraca_awal_parent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NeracaAwal_ParentAdapter.ViewHolder holder, int position) {
        holder.tv_parent.setText(neracaAwalParents.get(position).getParent_name());
        holder.id = neracaAwalParents.get(position).getId();

        neracaAwalClasses = neracaAwalParents.get(position).getNeracaAwalClasses();
        if (neracaAwalClasses.size() > 0) {
            neracaAwalAkuns = neracaAwalParents.get(position).getNeracaAwalClasses().get(0).getNeracaAwalAkuns();
        }

        holder.neracaAwal_klasifikasiAdapter = new NeracaAwal_KlasifikasiAdapter(context, neracaAwalClasses, neracaAwalAkuns);
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(context);
        holder.rv_neracaawal_class.setLayoutManager(eLayoutManager);
        holder.rv_neracaawal_class.setItemAnimator(new DefaultItemAnimator());
        holder.rv_neracaawal_class.setAdapter(holder.neracaAwal_klasifikasiAdapter);
        if (neracaAwalClasses.isEmpty()) {
            holder.tv_no_data.setVisibility(View.VISIBLE);
            holder.rv_neracaawal_class.setVisibility(View.GONE);
        } else {
            holder.tv_no_data.setVisibility(View.GONE);
            holder.rv_neracaawal_class.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return neracaAwalParents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private int id;
        RecyclerView rv_neracaawal_class;
        private NeracaAwal_KlasifikasiAdapter neracaAwal_klasifikasiAdapter;
        //LinearLayout ll_empty;
        TextView tv_parent, tv_no_data;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_parent = itemView.findViewById(R.id.tv_parent);
            tv_no_data = itemView.findViewById(R.id.tv_no_data);
            //ll_empty = itemView.findViewById(R.id.ll_empty);
            rv_neracaawal_class = itemView.findViewById(R.id.rv_neracaawal_class);
        }
    }

    public void refreshEvents(ArrayList<NeracaAwalParent> neracaAwalParents) {
        this.neracaAwalParents.clear();
        this.neracaAwalParents.addAll(neracaAwalParents);
        notifyDataSetChanged();
    }
}
