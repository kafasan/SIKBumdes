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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.sikbumdes.bumdes.DataAkunActivity;
import com.sikbumdes.bumdes.MainActivity;
import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunAkunUpdateResponse;
import com.sikbumdes.bumdes.model.AkunClass;
import com.sikbumdes.bumdes.model.AkunClassResponse;
import com.sikbumdes.bumdes.model.AkunDeleteResponse;
import com.sikbumdes.bumdes.model.Business;
import com.sikbumdes.bumdes.model.BusinessSessionResponse;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<Business> businesses;

    public BusinessAdapter(Context context, ArrayList<Business> businesses) {
        this.context = context;
        this.businesses = businesses;
    }

    @Override
    public BusinessAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_business, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BusinessAdapter.CustomViewHolder holder, int position) {
        Business business = businesses.get(position);
        holder.tv_business.setText(business.getBusiness_name());
        holder.id_business = business.getId();

        Business businessSave = SharedPrefManager.getInstance(context).getBusiness();
        int id_business_save = businessSave.getId();

        if (SharedPrefManager.getInstance(context).isSaveBusiness()) {
            if (holder.id_business == id_business_save) {
                holder.iv_check.setVisibility(View.VISIBLE);
            } else {
                holder.iv_check.setVisibility(View.GONE);
            }
        } else {
            holder.iv_check.setVisibility(View.GONE);
        }

        User user = SharedPrefManager.getInstance(context).getUser();
        String token = "Bearer " + user.getToken();
        int id = business.getId();

        holder.ll_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iv_check.setVisibility(View.GONE);
                holder.pb_loading.setVisibility(View.VISIBLE);
                Call<BusinessSessionResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .storeBusiness(token, id);

                call.enqueue(new Callback<BusinessSessionResponse>() {
                    @Override
                    public void onResponse(Call<BusinessSessionResponse> call, Response<BusinessSessionResponse> response) {
                        BusinessSessionResponse businessSessionResponse = response.body();
                        if (response.isSuccessful()) {
                            if (businessSessionResponse.isSuccess()) {
                                SharedPrefManager.getInstance(context).saveBusiness(businessSessionResponse.getBusiness());
                                if (context instanceof MainActivity) {
                                    ((MainActivity) context).closeDialogBusiness();
                                    ((MainActivity) context).getBusinessName();
                                }
                            } else {
                                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                ((MainActivity) context).closeDialogBusiness();
                            }
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(context, jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            ((MainActivity) context).closeDialogBusiness();
                        }
                    }

                    @Override
                    public void onFailure(Call<BusinessSessionResponse> call, Throwable t) {
                        Toast.makeText(context, "Terjadi kesalahan. Silahkan coba lagi nanti", Toast.LENGTH_SHORT).show();
                        ((MainActivity) context).closeDialogBusiness();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return businesses.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_business;
        private LinearLayout ll_business;
        private ImageView iv_check;
        private ProgressBar pb_loading;
        int id_business;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_business = itemView.findViewById(R.id.tv_business);
            ll_business = itemView.findViewById(R.id.ll_business);
            iv_check = itemView.findViewById(R.id.iv_check);
            pb_loading = itemView.findViewById(R.id.pb_loading);
        }
    }
}