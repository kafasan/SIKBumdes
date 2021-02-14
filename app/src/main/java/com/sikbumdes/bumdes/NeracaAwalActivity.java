package com.sikbumdes.bumdes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.sikbumdes.bumdes.adapters.NeracaAwal_ParentAdapter;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunAkunResponse;
import com.sikbumdes.bumdes.model.NeracaAwalAkun;
import com.sikbumdes.bumdes.model.NeracaAwalClass;
import com.sikbumdes.bumdes.model.NeracaAwalParent;
import com.sikbumdes.bumdes.model.NeracaAwalResponse;
import com.sikbumdes.bumdes.model.NeracaAwalStoreResponse;
import com.sikbumdes.bumdes.model.User;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NeracaAwalActivity extends AppCompatActivity {

    private ArrayList<NeracaAwalParent> neracaAwalParentArrayList;
    private ArrayList<NeracaAwalClass> neracaAwalClassArrayList;
    private ArrayList<NeracaAwalAkun> neracaAwalAkunArrayList;
    private ArrayList<AkunAkun> akunAkunArrayList;
    private ArrayAdapter<AkunAkun> akunArrayAdapter;
    private RecyclerView rv_neracaawal_parent;
    private NeracaAwal_ParentAdapter neracaAwal_parentAdapter;
    Context context;
    LottieAnimationView av_loading, av_loading_dialog;
    Dialog dialog_year, dialog_add;
    Calendar dateNow = Calendar.getInstance(TimeZone.getDefault());
    int year = dateNow.get(Calendar.YEAR);

    ImageView iv_back, iv_add;
    LinearLayout ll_changeyear, ll_button, ll_date;
    TextView tv_year, id_akun, tv_date, tv_warn, tv_totalkredit, tv_totaldebit;
    EditText et_amount;
    private int pYear, pMonth, pDay;
    String formatDateID, formatDateStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neraca_awal);

        context = this;

        av_loading = findViewById(R.id.av_loading);
        tv_totalkredit = findViewById(R.id.tv_totalkredit);
        tv_totaldebit = findViewById(R.id.tv_totaldebit);
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
        iv_add = findViewById(R.id.iv_add);
        iv_add.setOnClickListener(view -> showDialogAdd());
        iv_back.setOnClickListener(view -> onBackPressed());

        rv_neracaawal_parent = findViewById(R.id.rv_neracaawal_parent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNeracaAwal();
    }

    public void getNeracaAwal() {
        rv_neracaawal_parent.setVisibility(View.GONE);
        av_loading.setVisibility(View.VISIBLE);
        av_loading.playAnimation();

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
                        if (!neracaAwalClassArrayList.isEmpty()) {
                            neracaAwalAkunArrayList = neracaAwalResponse.getNeracaAwal().getNeracaAwalParents().get(0).getNeracaAwalClasses().get(0).getNeracaAwalAkuns();
                        }
                        neracaAwal_parentAdapter = new NeracaAwal_ParentAdapter(context, neracaAwalParentArrayList, neracaAwalClassArrayList, neracaAwalAkunArrayList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rv_neracaawal_parent.setLayoutManager(eLayoutManager);
                        rv_neracaawal_parent.setAdapter(neracaAwal_parentAdapter);
                        neracaAwal_parentAdapter.notifyDataSetChanged();
                        av_loading.cancelAnimation();
                        av_loading.setVisibility(View.GONE);
                        rv_neracaawal_parent.setVisibility(View.VISIBLE);

                        DecimalFormat fmt = new DecimalFormat();
                        DecimalFormatSymbols fmts = new DecimalFormatSymbols();
                        fmts.setGroupingSeparator('.');
                        fmt.setGroupingSize(3);
                        fmt.setGroupingUsed(true);
                        fmt.setDecimalFormatSymbols(fmts);
                        tv_totalkredit.setText(fmt.format(neracaAwalResponse.getNeracaAwal().getTotal_kredit()));
                        tv_totaldebit.setText(fmt.format(neracaAwalResponse.getNeracaAwal().getTotal_debit()));
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
            public void onFailure(Call<NeracaAwalResponse> call, Throwable t) {
                av_loading.cancelAnimation();
                av_loading.setVisibility(View.GONE);
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialogAdd() {
        dialog_add = new Dialog(context);
        dialog_add.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_add.setCancelable(false);
        dialog_add.setCanceledOnTouchOutside(false);
        dialog_add.setContentView(R.layout.dialog_add_neraca_awal);
        dialog_add.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialog_add.getWindow().setLayout(width, height);

        Spinner sp_akun = dialog_add.findViewById(R.id.sp_akun);
        id_akun = dialog_add.findViewById(R.id.id_akun);
        tv_date = dialog_add.findViewById(R.id.tv_date);
        et_amount = dialog_add.findViewById(R.id.et_amount);
        tv_warn = dialog_add.findViewById(R.id.tv_warn);
        ll_date = dialog_add.findViewById(R.id.ll_date);
        ll_button = dialog_add.findViewById(R.id.ll_button);
        av_loading_dialog = dialog_add.findViewById(R.id.av_loading_dialog);

        Calendar calendar = Calendar.getInstance();
        pYear = calendar.get(Calendar.YEAR);
        pMonth = calendar.get(Calendar.MONTH) + 1;
        pDay = calendar.get(Calendar.DAY_OF_MONTH);

        String tempMonth = "" + pMonth;
        String tempDay = "" + pDay;
        if (pMonth < 10) {
            tempMonth = "0" + pMonth;
        }
        if (pDay < 10) {
            tempDay = "0" + pDay;
        }
        formatDateStore = pYear + "-" + tempMonth + "-" + tempDay;
        formatDateID = tempDay + "-" + tempMonth + "-" + pYear;
        tv_date.setText(formatDateID);

        ll_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener pDateSetListener = new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        pYear = year;
                        pMonth = monthOfYear + 1;
                        pDay = dayOfMonth;

                        String fm = "" + pMonth;
                        String fd = "" + pDay;
                        if (pMonth < 10) {
                            fm = "0" + pMonth;
                        }
                        if (pDay < 10) {
                            fd = "0" + pDay;
                        }

                        formatDateStore = pYear + "-" + fm + "-" + fd;
                        formatDateID = fd + "-" + fm + "-" + pYear;

                        tv_date.setText(formatDateID);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(context, pDateSetListener, pYear, pMonth, pDay);
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

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
                    dialog_add.dismiss();
                    Toast.makeText(context, "Gagal mengambil data akun", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AkunAkunResponse> call, Throwable t) {
                dialog_add.dismiss();
                Toast.makeText(context, "Terjadi kesalahan, coba beberapa saat lagi", Toast.LENGTH_SHORT).show();
            }
        });

        sp_akun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AkunAkun akunAkun = (AkunAkun) adapterView.getSelectedItem();
                id_akun = dialog_add.findViewById(R.id.id_akun);
                id_akun.setText(String.valueOf(akunAkun.getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        MaterialButton cancel = dialog_add.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_add.dismiss();
            }
        });

        MaterialButton save = dialog_add.findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNeracaAwal();
            }
        });
        dialog_add.show();
    }

    public void createNeracaAwal() {
        tv_warn.setVisibility(View.GONE);
        ll_button.setVisibility(View.GONE);
        av_loading_dialog.setVisibility(View.VISIBLE);
        av_loading_dialog.playAnimation();
        User user = SharedPrefManager.getInstance(this).getUser();
        String token = "Bearer " + user.getToken();

        int id = Integer.parseInt(id_akun.getText().toString());
        String amount = et_amount.getText().toString();

        if (amount.isEmpty()) {
            et_amount.setError("Jumlah tidak boleh kosong");
            et_amount.requestFocus();
            return;
        }

        Call<NeracaAwalStoreResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .storeNeracaAwal(token, id, formatDateStore, amount);

        call.enqueue(new Callback<NeracaAwalStoreResponse>() {
            @Override
            public void onResponse(Call<NeracaAwalStoreResponse> call, Response<NeracaAwalStoreResponse> response) {
                NeracaAwalStoreResponse neracaAwalStoreResponse = response.body();
                if (response.isSuccessful()) {
                    if (neracaAwalStoreResponse.isSuccess()) {
                        Toast.makeText(context, "Neraca Awal berhasil ditambahkan", Toast.LENGTH_LONG).show();
                        av_loading_dialog.setVisibility(View.GONE);
                        av_loading_dialog.cancelAnimation();
                        dialog_add.dismiss();
                        onResume();
                    }
                } else {
                    av_loading_dialog.setVisibility(View.GONE);
                    av_loading_dialog.cancelAnimation();
                    tv_warn.setVisibility(View.VISIBLE);
                    ll_button.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Penginputan neraca awal hanya sekali dalam setahun", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NeracaAwalStoreResponse> call, Throwable t) {
                Log.d("NERACA_AWAL ", t.toString());
                av_loading_dialog.setVisibility(View.GONE);
                av_loading_dialog.cancelAnimation();
                tv_warn.setVisibility(View.VISIBLE);
                ll_button.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Terjadi kesalahan, coba beberapa saat lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
