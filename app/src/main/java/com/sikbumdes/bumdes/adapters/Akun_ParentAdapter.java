package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.util.Log;
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
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Akun_ParentAdapter extends RecyclerView.Adapter<Akun_ParentAdapter.ViewHolder> {

    Context context;
    private ArrayList<AkunParent> akunParents;

    public Akun_ParentAdapter(Context context, ArrayList<AkunParent> akunParents) {
        this.context = context;
        this.akunParents = akunParents;
    }

    @Override
    public Akun_ParentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_akun_parent, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Akun_ParentAdapter.ViewHolder holder, int position) {
        holder.tv_parent.setText(akunParents.get(position).getParent_name());
        holder.id = akunParents.get(position).getId();

        User user = SharedPrefManager.getInstance(context).getUser();
        String token = "Bearer " + user.getToken();

        Call<AkunClassResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAkunClass(token, holder.id);

        call.enqueue(new Callback<AkunClassResponse>() {
            @Override
            public void onResponse(Call<AkunClassResponse> call, Response<AkunClassResponse> response) {
                AkunClassResponse akunClassResponse = response.body();
                if (response.isSuccessful()) {
                    if (akunClassResponse.isSuccess()) {
                        holder.akunClassArrayList = akunClassResponse.getAkunClasses();
                        holder.akun_klasifikasiAdapter = new Akun_KlasifikasiAdapter(context, holder.akunClassArrayList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(context);
                        holder.rv_akun_class.setLayoutManager(eLayoutManager);
                        holder.rv_akun_class.setItemAnimator(new DefaultItemAnimator());
                        holder.rv_akun_class.setAdapter(holder.akun_klasifikasiAdapter);
                        holder.akun_klasifikasiAdapter.notifyDataSetChanged();
                        if (eLayoutManager.getItemCount() == 0) {
                            holder.ll_empty.setVisibility(View.VISIBLE);
                            holder.rv_akun_class.setVisibility(View.GONE);
                        } else {
                            holder.ll_empty.setVisibility(View.GONE);
                            holder.rv_akun_class.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AkunClassResponse> call, Throwable t) {
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return akunParents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private int id;
        RecyclerView rv_akun_class;
        private ArrayList<AkunClass> akunClassArrayList;
        private Akun_KlasifikasiAdapter akun_klasifikasiAdapter;
        LinearLayout ll_empty;
        TextView tv_parent;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_parent = itemView.findViewById(R.id.tv_parent);
            ll_empty = itemView.findViewById(R.id.ll_empty);
            rv_akun_class = itemView.findViewById(R.id.rv_akun_class);
        }
    }

    public void refreshEvents(ArrayList<AkunParent> akunParents) {
        this.akunParents.clear();
        this.akunParents.addAll(akunParents);
        notifyDataSetChanged();
    }
}
