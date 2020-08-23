package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sikbumdes.bumdes.DataAkunActivity;
import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunAkunResponse;
import com.sikbumdes.bumdes.model.AkunClass;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Akun_KlasifikasiAdapter extends RecyclerView.Adapter<Akun_KlasifikasiAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<AkunClass> akunClasses;

    public Akun_KlasifikasiAdapter(Context context, ArrayList<AkunClass> akunClasses) {
        this.context = context;
        this.akunClasses = akunClasses;
    }

    @Override
    public Akun_KlasifikasiAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_akun_class, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Akun_KlasifikasiAdapter.CustomViewHolder holder, int position) {
        AkunClass akunClass = akunClasses.get(position);
        holder.tv_class.setText(akunClass.getClassification_name() + " (" + akunClass.getClassification_code() + ")");
        holder.id = akunClasses.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return akunClasses.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int id;
        private TextView tv_class;
        private ImageView iv_edit;
        private LinearLayout ll_akun, ll_akun_class;
        private RecyclerView rv_akun_akun;
        private ArrayList<AkunAkun> akunAkunArrayList;
        private Akun_AkunAdapter akun_akunAdapter;
        RecyclerView.LayoutManager eLayoutManager;
        User user = SharedPrefManager.getInstance(context).getUser();
        String token = "Bearer " + user.getToken();

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_class = itemView.findViewById(R.id.tv_class);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            ll_akun = itemView.findViewById(R.id.ll_akun);
            ll_akun_class = itemView.findViewById(R.id.ll_akun_class);
            rv_akun_akun = itemView.findViewById(R.id.rv_akun_akun);
            ll_akun_class.setOnClickListener(this);
//            iv_edit.setOnClickListener(this);

//            if (eLayoutManager.getItemCount() == 0) {
//                ll_akun_class.setOnClickListener(null);
//            } else {
//                ll_akun_class.setOnClickListener(this);
//            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ll_akun_class:
                    int id = getLayoutPosition();

                    AkunClass akunClass = akunClasses.get(id);

                    if (akunClass.isChildrenVisible()) {
                        akunClass.setChildrenVisible(false);
                        ll_akun.setVisibility(View.GONE);
                    } else {
                        akunClass.setChildrenVisible(true);
                        ll_akun.setVisibility(View.VISIBLE);
                        getAkunAkun();
                    }
                    break;
                case R.id.iv_edit:
                    PopupMenu popupMenu = new PopupMenu(context, iv_edit);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.menu_edit:
                                editClassification();
                                    return true;
                                case  R.id.menu_delete:
//                                deleteClassification();
                                    return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.inflate(R.menu.data_akun_menu);
                    popupMenu.show();
                    break;
                default:
                    break;
            }

        }

        public void getAkunAkun() {
            Call<AkunAkunResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getAkunAkun(token, id);

            call.enqueue(new Callback<AkunAkunResponse>() {
                @Override
                public void onResponse(Call<AkunAkunResponse> call, Response<AkunAkunResponse> response) {
                    AkunAkunResponse akunAkunResponse = response.body();
                    if (response.isSuccessful()) {
                        if (akunAkunResponse.isSuccess()) {
                            akunAkunArrayList = akunAkunResponse.getAkunClasses();
                            akun_akunAdapter = new Akun_AkunAdapter(context, akunAkunArrayList);
                            RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(itemView.getContext());
                            rv_akun_akun.setLayoutManager(eLayoutManager);
                            rv_akun_akun.setItemAnimator(new DefaultItemAnimator());
                            rv_akun_akun.setAdapter(akun_akunAdapter);
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
                public void onFailure(Call<AkunAkunResponse> call, Throwable t) {
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void editClassification(){

        }
    }
}
