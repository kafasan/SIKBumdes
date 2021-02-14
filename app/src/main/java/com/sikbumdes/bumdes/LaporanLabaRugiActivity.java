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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.sikbumdes.bumdes.adapters.LabaRugi_ExpenseAdapter;
import com.sikbumdes.bumdes.adapters.LabaRugi_IncomeAdapter;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.LabaRugiExpense;
import com.sikbumdes.bumdes.model.LabaRugiIncome;
import com.sikbumdes.bumdes.model.LabaRugiResponse;
import com.sikbumdes.bumdes.model.NeracaAwalResponse;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class LaporanLabaRugiActivity extends AppCompatActivity {

    ImageView iv_back;
    TextView tv_month, tv_year, tv_total, tv_labausaha;
    LinearLayout ll_data, ll_picker;
    Context context;
    LottieAnimationView av_loading;
    Dialog datePicker;
    Calendar dateNow = Calendar.getInstance(TimeZone.getDefault());
    int year = dateNow.get(Calendar.YEAR);
    int month = dateNow.get(Calendar.MONTH) + 1;

    private RecyclerView rv_income, rv_expense, rv_otherincome, rv_otherexpense;
    private ArrayList<LabaRugiIncome> incomeArraylist;
    private ArrayList<LabaRugiExpense> expenseArrayList;
    private LabaRugi_IncomeAdapter labaRugi_incomeAdapter;
    private LabaRugi_ExpenseAdapter labaRugi_expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_laba_rugi);

        context = this;

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(view -> onBackPressed());

        ll_data = findViewById(R.id.ll_data);
        ll_picker = findViewById(R.id.ll_picker);
        av_loading = findViewById(R.id.av_loading);
        tv_total = findViewById(R.id.tv_total);
        tv_labausaha = findViewById(R.id.tv_labausaha);
        tv_month = findViewById(R.id.tv_month);
        tv_year = findViewById(R.id.tv_year);
        tv_month.setText(String.valueOf(month));
        tv_year.setText(String.valueOf(year));

        ll_picker.setOnClickListener(view -> {
            showPicker();
        });

        rv_income = findViewById(R.id.rv_income);
        rv_expense = findViewById(R.id.rv_expense);
        rv_otherincome = findViewById(R.id.rv_other_income);
        rv_otherexpense = findViewById(R.id.rv_other_expense);

    }

    public void showPicker() {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLabaRugi();
    }

    public void getLabaRugi() {
        ll_data.setVisibility(View.GONE);
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
                if (response.isSuccessful()) {
                    if (labaRugiResponse.isSuccess()) {
                        DecimalFormat fmt = new DecimalFormat();
                        DecimalFormatSymbols fmts = new DecimalFormatSymbols();
                        fmts.setGroupingSeparator('.');
                        fmt.setGroupingSize(3);
                        fmt.setGroupingUsed(true);
                        fmt.setDecimalFormatSymbols(fmts);
                        tv_total.setText(fmt.format(labaRugiResponse.getLabaRugiData().getLabaRugi().getLaba_berjalan()));
                        tv_labausaha.setText(fmt.format(labaRugiResponse.getLabaRugiData().getLabaRugi().getLaba_usaha()));

                        incomeArraylist = labaRugiResponse.getLabaRugiData().getLabaRugi().getIncomeArrayList();
                        labaRugi_incomeAdapter = new LabaRugi_IncomeAdapter(context, incomeArraylist);
                        RecyclerView.LayoutManager layoutManagerIncome = new LinearLayoutManager(getApplicationContext());
                        rv_income.setLayoutManager(layoutManagerIncome);
                        rv_income.setAdapter(labaRugi_incomeAdapter);
                        labaRugi_incomeAdapter.notifyDataSetChanged();

                        expenseArrayList = labaRugiResponse.getLabaRugiData().getLabaRugi().getExpenseArrayList();
                        labaRugi_expenseAdapter = new LabaRugi_ExpenseAdapter(context, expenseArrayList);
                        RecyclerView.LayoutManager layoutManagerExpense = new LinearLayoutManager(getApplicationContext());
                        rv_expense.setLayoutManager(layoutManagerExpense);
                        rv_expense.setAdapter(labaRugi_expenseAdapter);
                        labaRugi_expenseAdapter.notifyDataSetChanged();

                        av_loading.cancelAnimation();
                        av_loading.setVisibility(View.GONE);
                        ll_data.setVisibility(View.VISIBLE);
                    } else {
                        av_loading.cancelAnimation();
                        av_loading.setVisibility(View.GONE);
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    av_loading.cancelAnimation();
                    av_loading.setVisibility(View.GONE);
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jObjError.toString(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LabaRugiResponse> call, Throwable t) {
                av_loading.cancelAnimation();
                av_loading.setVisibility(View.GONE);
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
