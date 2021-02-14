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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.sikbumdes.bumdes.adapters.BukuBesarAdapter;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunAkunResponse;
import com.sikbumdes.bumdes.model.BukuBesar;
import com.sikbumdes.bumdes.model.BukuBesarResponse;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class BukuBesarActivity extends AppCompatActivity {

    Context context;
    LottieAnimationView av_loading, av_loading_dialog;
    Dialog dialog_year, dialog_choose_akun;
    Calendar dateNow = Calendar.getInstance(TimeZone.getDefault());
    int year = dateNow.get(Calendar.YEAR);
    Spinner sp_akun;
    LinearLayout ll_changeyear, ll_akun;
    TextView tv_year, id_akun, tv_akun_name, tv_saldoawal, tv_saldoakhir;
    HorizontalScrollView hsv_data;
    ShimmerFrameLayout sfl_loading;
    ImageView back;

    private RecyclerView rv_bukubesar;
    private ArrayList<AkunAkun> akunAkunArrayList;
    private ArrayAdapter<AkunAkun> akunArrayAdapter;
    private ArrayList<BukuBesar> bukuBesarArrayList;
    private BukuBesarAdapter bukuBesarAdapter;
//    private AkunAdapter akunAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_besar);

        context = this;

        hsv_data = findViewById(R.id.hsv_data);
        sfl_loading = findViewById(R.id.sfl_loading);
        av_loading = findViewById(R.id.av_loading);
        tv_year = findViewById(R.id.tv_year);
        tv_year.setText(String.valueOf(year));
        ll_changeyear = findViewById(R.id.ll_changeyear);
        ll_changeyear.setOnClickListener(v -> {
            dialog_year = new Dialog(context);
            dialog_year.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_year.setCancelable(true);
            dialog_year.setCanceledOnTouchOutside(false);
            dialog_year.setContentView(R.layout.dialog_choose_year);
            dialog_year.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            dialog_year.getWindow().setLayout(width, height);

            final NumberPicker numberPicker = dialog_year.findViewById(R.id.np_year);

            numberPicker.setMaxValue(year + 25);
            numberPicker.setMinValue(year - 25);
            numberPicker.setWrapSelectorWheel(false);
            numberPicker.setValue(Integer.parseInt(tv_year.getText().toString()));
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            ImageView iv_close = dialog_year.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(v1 -> dialog_year.dismiss());

            Button btn_confirm = dialog_year.findViewById(R.id.btn_confirm);
            btn_confirm.setOnClickListener(v12 -> {
                tv_year.setText(String.valueOf(numberPicker.getValue()));
                dialog_year.dismiss();
            });
            dialog_year.show();

        });

        id_akun = findViewById(R.id.id_akun);
        sp_akun = findViewById(R.id.sp_akun);
        sp_akun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AkunAkun akunAkun = (AkunAkun) adapterView.getSelectedItem();
                id_akun = findViewById(R.id.id_akun);
                id_akun.setText(String.valueOf(akunAkun.getId()));
                showBukuBesar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        back = findViewById(R.id.iv_back);
        back.setOnClickListener(view -> onBackPressed());

        rv_bukubesar = findViewById(R.id.rv_bukubesar);
        tv_saldoawal = findViewById(R.id.tv_saldoawal);
        tv_saldoakhir = findViewById(R.id.tv_saldoakhir);

        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        Call<AkunAkunResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllAkun(token);

        call.enqueue(new Callback<AkunAkunResponse>() {
            @Override
            public void onResponse(Call<AkunAkunResponse> call, Response<AkunAkunResponse> response) {
                AkunAkunResponse akunAkunResponse = response.body();
                if (response.isSuccessful()) {
                    if (akunAkunResponse.isSuccess()) {
                        akunAkunArrayList = akunAkunResponse.getAkunAkuns();
                        akunArrayAdapter = new ArrayAdapter<>(context, R.layout.layout_spinner, akunAkunArrayList);
                        akunArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_akun.setAdapter(akunArrayAdapter);
                    }
                } else {
                    Toast.makeText(context, "Gagal mengambil data akun", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AkunAkunResponse> call, Throwable t) {
                Log.d("Pos ", "1");
                Log.d("Pos ", t.toString());
                Toast.makeText(context, "Terjadi kesalahan, coba beberapa saat lagi", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //showBukuBesar();
    }

    public void showBukuBesar() {
        hsv_data.setVisibility(View.GONE);
        sfl_loading.setVisibility(View.VISIBLE);
        sfl_loading.startShimmer();
        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        Call<BukuBesarResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getBukuBesar(token, tv_year.getText().toString(), id_akun.getText().toString());

        call.enqueue(new Callback<BukuBesarResponse>() {
            @Override
            public void onResponse(Call<BukuBesarResponse> call, Response<BukuBesarResponse> response) {
                BukuBesarResponse bukuBesarResponse = response.body();
                if (response.isSuccessful()) {
                    Log.i("RESPONSE CHECK", "onResponse : SUCCESSFUL");
                    if (bukuBesarResponse.isSuccess()) {
                        Log.i("CHECK", "onResponse : SUCCESSFUL");
                        bukuBesarArrayList = bukuBesarResponse.getBukuBesarData().getBukuBesarArrayList();
                        bukuBesarAdapter = new BukuBesarAdapter(context, bukuBesarArrayList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        rv_bukubesar.setLayoutManager(layoutManager);
                        rv_bukubesar.setAdapter(bukuBesarAdapter);
                        bukuBesarAdapter.notifyDataSetChanged();

                        DecimalFormat fmt = new DecimalFormat();
                        DecimalFormatSymbols fmts = new DecimalFormatSymbols();

                        fmts.setGroupingSeparator('.');
                        fmt.setGroupingSize(3);
                        fmt.setGroupingUsed(true);
                        fmt.setDecimalFormatSymbols(fmts);

                        tv_saldoawal.setText(fmt.format(bukuBesarResponse.getBukuBesarData().getBukuBesarLogs().getSaldo_awal()));
                        tv_saldoakhir.setText(fmt.format(bukuBesarResponse.getBukuBesarData().getBukuBesarLogs().getSaldo_akhir()));
                        hsv_data.setVisibility(View.VISIBLE);
                        sfl_loading.stopShimmer();
                        sfl_loading.setVisibility(View.GONE);
                    } else {
                        Log.d("Pos ", "2");
                        hsv_data.setVisibility(View.GONE);
                        sfl_loading.stopShimmer();
                        sfl_loading.setVisibility(View.GONE);
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Pos ", "3");
                    hsv_data.setVisibility(View.GONE);
                    sfl_loading.stopShimmer();
                    sfl_loading.setVisibility(View.GONE);
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BukuBesarResponse> call, Throwable t) {
                Log.d("Pos ", "4");
                Log.d("Pos ", t.toString());
                hsv_data.setVisibility(View.GONE);
                sfl_loading.stopShimmer();
                sfl_loading.setVisibility(View.GONE);
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
