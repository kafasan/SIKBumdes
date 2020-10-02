package com.sikbumdes.bumdes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sikbumdes.bumdes.MainActivity;
import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.Business;
import com.sikbumdes.bumdes.model.BusinessSessionResponse;
import com.sikbumdes.bumdes.model.LabaRugiIncome;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabaRugi_IncomeAdapter extends RecyclerView.Adapter<LabaRugi_IncomeAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<LabaRugiIncome> labaRugiIncomes;

    public LabaRugi_IncomeAdapter(Context context, ArrayList<LabaRugiIncome> labaRugiIncomes) {
        this.context = context;
        this.labaRugiIncomes = labaRugiIncomes;
    }

    @Override
    public LabaRugi_IncomeAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_laba_rugi, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LabaRugi_IncomeAdapter.CustomViewHolder holder, int position) {
        LabaRugiIncome labaRugiIncome = labaRugiIncomes.get(position);
        int i = Integer.parseInt(labaRugiIncome.getName().get(position));

        for (i = 0, i <= labaRugiIncome.getName().size(), i++){holder.tv_name.setText(labaRugiIncome.getName());
            holder.tv_name.setText(labaRugiIncome.getName());
        }


    }

    @Override
    public int getItemCount() {
        return labaRugiIncomes.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_total;
        private ImageView iv_circle;
        private ProgressBar pb_loading;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_total = itemView.findViewById(R.id.tv_total);
            iv_circle = itemView.findViewById(R.id.iv_circle);
            pb_loading = itemView.findViewById(R.id.pb_loading);
        }
    }
}