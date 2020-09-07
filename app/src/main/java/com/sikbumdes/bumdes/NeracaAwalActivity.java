package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.sikbumdes.bumdes.adapters.NeracaAwal_ParentAdapter;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.NeracaAwalAkun;
import com.sikbumdes.bumdes.model.NeracaAwalClass;
import com.sikbumdes.bumdes.model.NeracaAwalParent;
import com.sikbumdes.bumdes.model.NeracaAwalResponse;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NeracaAwalActivity extends AppCompatActivity {

    private ArrayList<NeracaAwalParent> neracaAwalParentArrayList;
    private ArrayList<NeracaAwalClass> neracaAwalClassArrayList;
    private ArrayList<NeracaAwalAkun> neracaAwalAkunArrayList;
    private RecyclerView rv_neracaawal_parent;
    private NeracaAwal_ParentAdapter neracaAwal_parentAdapter;
    Context context;
    ProgressDialog loading;
    Dialog dialog_year;
    Calendar dateNow = Calendar.getInstance(TimeZone.getDefault());
    int year = dateNow.get(Calendar.YEAR);

    ImageView iv_back;
    LinearLayout ll_changeyear;
    TextView tv_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neraca_awal);

        context = this;

        tv_year = findViewById(R.id.tv_year);
        tv_year.setText(String.valueOf(year));
        ll_changeyear = findViewById(R.id.ll_changeyear);
        ll_changeyear.setOnClickListener(v -> {
            dialog_year = new Dialog(context);
            dialog_year.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_year.setCancelable(true);
            dialog_year.setCanceledOnTouchOutside(false);
            dialog_year.setContentView(R.layout.dialog_choose_year);

            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            dialog_year.getWindow().setLayout(width, height);

            final NumberPicker numberPicker = dialog_year.findViewById(R.id.np_year);

            numberPicker.setMaxValue(year + 50);
            numberPicker.setMinValue(year - 50);
            numberPicker.setWrapSelectorWheel(false);
            numberPicker.setValue(year);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            ImageView iv_close = dialog_year.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_year.dismiss();
                }
            });

            Button btn_confirm = dialog_year.findViewById(R.id.btn_confirm);
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_year.setText(String.valueOf(numberPicker.getValue()));
                    dialog_year.dismiss();
                    getNeracaAwal();
                }
            });
            dialog_year.show();
        });

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> onBackPressed());

        rv_neracaawal_parent = findViewById(R.id.rv_neracaawal_parent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNeracaAwal();
    }

    public void getNeracaAwal() {
        loading = ProgressDialog.show(context, null, "Mohon tunggu sebentar...", true, false);

        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        Call<NeracaAwalResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getNeracaAwal(token, tv_year.getText().toString());

        call.enqueue(new Callback<NeracaAwalResponse>() {
            @Override
            public void onResponse(Call<NeracaAwalResponse> call, Response<NeracaAwalResponse> response) {
                NeracaAwalResponse neracaAwalResponse = response.body();
                if (response.isSuccessful()) {
                    if (neracaAwalResponse.isSuccess()) {
                        neracaAwalParentArrayList = neracaAwalResponse.getNeracaAwal().getNeracaAwalParents();
                        neracaAwalClassArrayList = neracaAwalResponse.getNeracaAwal().getNeracaAwalParents().get(0).getNeracaAwalClasses();
                        neracaAwalAkunArrayList = neracaAwalResponse.getNeracaAwal().getNeracaAwalParents().get(0).getNeracaAwalClasses().get(0).getNeracaAwalAkuns();
                        neracaAwal_parentAdapter = new NeracaAwal_ParentAdapter(context, neracaAwalParentArrayList, neracaAwalClassArrayList, neracaAwalAkunArrayList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rv_neracaawal_parent.setLayoutManager(eLayoutManager);
                        rv_neracaawal_parent.setItemAnimator(new DefaultItemAnimator());
                        rv_neracaawal_parent.setAdapter(neracaAwal_parentAdapter);
                        neracaAwal_parentAdapter.notifyDataSetChanged();
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
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<NeracaAwalResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
