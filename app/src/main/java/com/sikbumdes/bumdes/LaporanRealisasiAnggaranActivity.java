package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Calendar;
import java.util.TimeZone;

public class LaporanRealisasiAnggaranActivity extends AppCompatActivity {

    Context context;
    LottieAnimationView av_loading, av_loading_dialog;
    TextView tv_nama, tv_anggaran, tv_realisasi, tv_persen, tv_year, tv_month,  id_akun;
    ImageView back;
    LinearLayout ll_lra, ll_picker;
    HorizontalScrollView hsv_data;
    ShimmerFrameLayout sfl_loading;
    Calendar dateNow = Calendar.getInstance(TimeZone.getDefault());
    int year = dateNow.get(Calendar.YEAR);
    int month = dateNow.get(Calendar.MONTH) + 1;
    Dialog datePicker;

    private RecyclerView rv_lra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_realisasi_anggaran);

        back = findViewById(R.id.iv_back);
        back.setOnClickListener(view -> onBackPressed());

        context = this;

        hsv_data = findViewById(R.id.hsv_data);
        sfl_loading = findViewById(R.id.sfl_loading);
        av_loading = findViewById(R.id.av_loading);
        tv_year = findViewById(R.id.tv_year);
        tv_year.setText(String.valueOf(year));
        ll_picker = findViewById(R.id.ll_picker);
        ll_picker.setOnClickListener(view -> showPicker());
        ll_lra = findViewById(R.id.ll_lra);

//        tv_nama = findViewById(R.id.tv_nama);
//        tv_anggaran = findViewById(R.id.tv_anggaran);
//        tv_realisasi = findViewById(R.id.tv_realisasi);
//        tv_persen = findViewById(R.id.tv_persen);
    }

    public void showPicker(){
        datePicker = new Dialog(context);
        datePicker.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datePicker.setCancelable(true);
        datePicker.setCanceledOnTouchOutside(false);
        datePicker.setContentView(R.layout.dialog_choose_month_year);
        datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
                getLRA();
            }
        });
        datePicker.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLRA();
    }

    public void getLRA(){
        ll_lra.setVisibility(View.GONE);
        av_loading.setVisibility(View.VISIBLE);
        av_loading.playAnimation();


    }
}
