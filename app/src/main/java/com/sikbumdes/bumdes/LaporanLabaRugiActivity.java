package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.LabaRugiExpense;
import com.sikbumdes.bumdes.model.LabaRugiIncome;
import com.sikbumdes.bumdes.model.LabaRugiResponse;
import com.sikbumdes.bumdes.model.NeracaAwalResponse;
import com.sikbumdes.bumdes.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class LaporanLabaRugiActivity extends AppCompatActivity {

    ImageView back;
    TextView tv_month, tv_year, tv_totalincome, tv_totalexpense, tv_totalotherincome, tv_totalotherexpense, tv_total;
    Context context;
    LottieAnimationView av_loading, av_loading_dialog;
    Dialog datePicker;
    Calendar dateNow = Calendar.getInstance(TimeZone.getDefault());
    int year = dateNow.get(Calendar.YEAR);
    int month = dateNow.get(Calendar.MONTH) + 1;

    private RecyclerView rv_income, rv_expense, rv_otherincome, rv_otherexpense;
    private ArrayList<LabaRugiIncome> incomeArraylist;
    private ArrayList<LabaRugiExpense> expenseArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_laba_rugi);

        context = this;

        back = findViewById(R.id.iv_back);
        back.setOnClickListener(view -> onBackPressed());

        av_loading = findViewById(R.id.av_loading);
        tv_month = findViewById(R.id.tv_month);
        tv_year = findViewById(R.id.tv_year);
        tv_month.setText(String.valueOf(month));
        tv_year.setText(String.valueOf(year));
        tv_year.setOnClickListener(view -> {
            datePicker = new Dialog(context);
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
//            np_month.setValue(dateNow.get(Calendar.MONTH) + 1);
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
                    getLabaRugi();
                }
            });
            datePicker.show();

        });

        rv_income = findViewById(R.id.rv_pendapatan);
        rv_expense = findViewById(R.id.rv_biaya);
        rv_otherincome = findViewById(R.id.rv_pendapatanlain);
        rv_otherexpense = findViewById(R.id.rv_biayalain);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getLabaRugi();
    }

    public void getLabaRugi(){
        rv_income.setVisibility(View.GONE);
        rv_expense.setVisibility(View.GONE);
        rv_otherincome.setVisibility(View.GONE);
        rv_otherexpense.setVisibility(View.GONE);
        av_loading.setVisibility(View.VISIBLE);
        av_loading.playAnimation();

        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        Call<LabaRugiResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getLabaRugi(token, tv_year.getText().toString(), tv_month.getText().toString());

        call.enqueue(new Callback<LabaRugiResponse>() {
            @Override
            public void onResponse(Call<LabaRugiResponse> call, Response<LabaRugiResponse> response) {
                LabaRugiResponse labaRugiResponse = response.body();
                if (response.isSuccessful()){
                    if (labaRugiResponse.isSuccess()){
                        incomeArraylist = labaRugiResponse.getLabaRugiData().getLabaRugi().getIncomeArrayList();
                    }
                }
            }

            @Override
            public void onFailure(Call<LabaRugiResponse> call, Throwable t) {

            }
        });

    }
}
