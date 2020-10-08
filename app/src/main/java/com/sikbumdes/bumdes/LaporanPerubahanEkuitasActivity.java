package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.sikbumdes.bumdes.adapters.PenambahanEkuitasAdapter;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.PenambahanEkuitas;
import com.sikbumdes.bumdes.model.PerubahanEkuitas;
import com.sikbumdes.bumdes.model.PerubahanEkuitasResponse;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class LaporanPerubahanEkuitasActivity extends AppCompatActivity {

    ImageView back;
    TextView tv_month, tv_year, tv_modalawal, tv_nambahmodal, tv_total;
//    Context context;
    LinearLayout ll_ekuitas;
    LottieAnimationView av_loading, av_loading_dialog;
    Dialog datePicker;
    Calendar dateNow = Calendar.getInstance(TimeZone.getDefault());
    int year = dateNow.get(Calendar.YEAR);
    int month = dateNow.get(Calendar.MONTH) + 1;

    private ArrayList<PenambahanEkuitas> penambahanEkuitasArrayList;
    private PenambahanEkuitasAdapter penambahanEkuitasAdapter;
    private RecyclerView rv_modal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_perubahan_ekuitas);

        back = findViewById(R.id.iv_back);
        back.setOnClickListener(view -> onBackPressed());

        av_loading = findViewById(R.id.av_loading);
        tv_month = findViewById(R.id.tv_month);
        tv_year = findViewById(R.id.tv_year);
        tv_month.setText(String.valueOf(month));
        tv_year.setText(String.valueOf(year));
        tv_year.setOnClickListener(view -> {
            datePicker = new Dialog(LaporanPerubahanEkuitasActivity.this);
            datePicker.requestWindowFeature(Window.FEATURE_NO_TITLE);
            datePicker.setCancelable(true);
            datePicker.setCanceledOnTouchOutside(false);
            datePicker.setContentView(R.layout.dialog_choose_month_year);
            datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            datePicker.getWindow().setLayout(width, height);

            final NumberPicker np_year = datePicker.findViewById(R.id.np_year);
            final NumberPicker np_month = datePicker.findViewById(R.id.np_month);

            np_year.setMaxValue(year + 25);
            np_year.setMinValue(year - 25);
            np_year.setWrapSelectorWheel(false);
            np_year.setValue(Integer.parseInt(tv_year.getText().toString()));
            np_year.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            np_month.setMinValue(1);
            np_month.setMaxValue(12);
            np_month.setWrapSelectorWheel(false);
            np_month.setValue(Integer.parseInt(tv_month.getText().toString()));
            np_month.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            ImageView iv_close = datePicker.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(v1 -> datePicker.dismiss());

            Button btn_confirm = datePicker.findViewById(R.id.btn_confirm);
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_year.setText(String.valueOf(np_year.getValue()));
                    tv_month.setText(String.valueOf(np_month.getValue()));
                    datePicker.dismiss();
                    getEkuitas();
                }
            });
            datePicker.show();

        });

        tv_modalawal = findViewById(R.id.tv_modalawal);
        rv_modal = findViewById(R.id.rv_modal);
        tv_nambahmodal = findViewById(R.id.tv_nambahmodal);
        tv_total = findViewById(R.id.tv_total);
        ll_ekuitas = findViewById(R.id.ll_ekuitas);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEkuitas();
    }

    public void getEkuitas(){
        ll_ekuitas.setVisibility(View.GONE);
        av_loading.setVisibility(View.VISIBLE);
        av_loading.playAnimation();

        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        Call<PerubahanEkuitasResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getEkuitas(token, tv_year.getText().toString(), tv_month.getText().toString());

        call.enqueue(new Callback<PerubahanEkuitasResponse>() {
            @Override
            public void onResponse(Call<PerubahanEkuitasResponse> call, Response<PerubahanEkuitasResponse> response) {
                PerubahanEkuitasResponse perubahanEkuitasResponse = response.body();
                if (response.isSuccessful()){
                    if (perubahanEkuitasResponse.isSuccess()){
                        Log.i("LOGIN CHECK", "onResponse : SUCCESSFUL");
                        penambahanEkuitasArrayList = perubahanEkuitasResponse.getPerubahanEkuitasData().getPerubahanEkuitas().getPenambahanEkuitas();
                        penambahanEkuitasAdapter = new PenambahanEkuitasAdapter(LaporanPerubahanEkuitasActivity.this, penambahanEkuitasArrayList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rv_modal.setLayoutManager(eLayoutManager);
                        rv_modal.setAdapter(penambahanEkuitasAdapter);
                        penambahanEkuitasAdapter.notifyDataSetChanged();
                        av_loading.cancelAnimation();
                        av_loading.setVisibility(View.GONE);
                        ll_ekuitas.setVisibility(View.VISIBLE);

                        DecimalFormat fmt = new DecimalFormat();
                        DecimalFormatSymbols fmts = new DecimalFormatSymbols();

                        fmts.setGroupingSeparator('.');
                        fmt.setGroupingSize(3);
                        fmt.setGroupingUsed(true);
                        fmt.setDecimalFormatSymbols(fmts);

                        tv_modalawal.setText(fmt.format(perubahanEkuitasResponse.getPerubahanEkuitasData().getPerubahanEkuitas().getModal_awal()));
                        tv_nambahmodal.setText(fmt.format(perubahanEkuitasResponse.getPerubahanEkuitasData().getPerubahanEkuitas().getTotal_penambahan()));
                        tv_total.setText(fmt.format(perubahanEkuitasResponse.getPerubahanEkuitasData().getPerubahanEkuitas().getModal_akhir()));
                    }
                    else {
                        av_loading.cancelAnimation();
                        av_loading.setVisibility(View.GONE);
                        Toast.makeText(LaporanPerubahanEkuitasActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    av_loading.cancelAnimation();
                    av_loading.setVisibility(View.GONE);
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LaporanPerubahanEkuitasActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(LaporanPerubahanEkuitasActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PerubahanEkuitasResponse> call, Throwable t) {
                av_loading.cancelAnimation();
                av_loading.setVisibility(View.GONE);
                Toast.makeText(LaporanPerubahanEkuitasActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
