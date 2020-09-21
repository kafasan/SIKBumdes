package com.sikbumdes.bumdes.adapters;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.sikbumdes.bumdes.NeracaAwalActivity;
import com.sikbumdes.bumdes.R;
import com.sikbumdes.bumdes.api.RetrofitClient;
import com.sikbumdes.bumdes.api.SharedPrefManager;
import com.sikbumdes.bumdes.model.AkunAkun;
import com.sikbumdes.bumdes.model.AkunAkunResponse;
import com.sikbumdes.bumdes.model.AkunAkunUpdateResponse;
import com.sikbumdes.bumdes.model.AkunClass;
import com.sikbumdes.bumdes.model.AkunClassResponse;
import com.sikbumdes.bumdes.model.AkunDeleteResponse;
import com.sikbumdes.bumdes.model.NeracaAwalAkun;
import com.sikbumdes.bumdes.model.NeracaAwalBalance;
import com.sikbumdes.bumdes.model.NeracaAwalStoreResponse;
import com.sikbumdes.bumdes.model.User;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NeracaAwal_AkunAdapter extends RecyclerView.Adapter<NeracaAwal_AkunAdapter.CustomViewHolder> {

    Context context;
    private ArrayList<NeracaAwalAkun> neracaAwalAkuns;
    private List<Integer> colors;

    public NeracaAwal_AkunAdapter(Context context, ArrayList<NeracaAwalAkun> neracaAwalAkuns) {
        this.context = context;
        this.neracaAwalAkuns = neracaAwalAkuns;
    }

    @Override
    public NeracaAwal_AkunAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_neraca_awal_akun, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NeracaAwal_AkunAdapter.CustomViewHolder holder, int position) {
        holder.tv_akun.setText(neracaAwalAkuns.get(position).getAccount_name());

        String pos = neracaAwalAkuns.get(position).getPosition();
        if (pos.equals("Debit")) {
            holder.tv_position.setTextColor(context.getResources().getColor(R.color.text_red));
        } else {
            holder.tv_position.setTextColor(context.getResources().getColor(R.color.text_green));
        }
        holder.tv_position.setText(String.format("(%s)", pos.substring(0, 1)));

        DecimalFormat fmt = new DecimalFormat();
        DecimalFormatSymbols fmts = new DecimalFormatSymbols();

        fmts.setGroupingSeparator('.');
        fmt.setGroupingSize(3);
        fmt.setGroupingUsed(true);
        fmt.setDecimalFormatSymbols(fmts);

        ArrayList<NeracaAwalBalance> neracaAwalBalances = neracaAwalAkuns.get(position).getNeracaAwalBalances();

        if (!neracaAwalBalances.isEmpty()) {
            holder.tv_amount.setText(fmt.format(neracaAwalBalances.get(0).getAmount()));
            holder.tv_date.setText(neracaAwalBalances.get(0).getDate());
        }

        colors = new ArrayList<Integer>();

        colors.add(context.getResources().getColor(R.color.colorPastel_1));
        colors.add(context.getResources().getColor(R.color.colorPastel_2));
        colors.add(context.getResources().getColor(R.color.colorPastel_3));
        colors.add(context.getResources().getColor(R.color.colorPastel_4));
        colors.add(context.getResources().getColor(R.color.colorPastel_5));
        colors.add(context.getResources().getColor(R.color.colorPastel_6));
        colors.add(context.getResources().getColor(R.color.colorPastel_7));
        colors.add(context.getResources().getColor(R.color.colorPastel_8));
        colors.add(context.getResources().getColor(R.color.colorPastel_9));

        Random r = new Random();
        int i = r.nextInt(colors.size());

        holder.v_line.setBackgroundColor(colors.get(i));

        holder.id = neracaAwalAkuns.get(position).getNeracaAwalBalances().get(0).getId();
        holder.id_account = neracaAwalAkuns.get(position).getNeracaAwalBalances().get(0).getId_account();
        holder.id_classification = neracaAwalAkuns.get(position).getId_classification();
        holder.account_code = neracaAwalAkuns.get(position).getAccount_code();
        holder.account_name = neracaAwalAkuns.get(position).getAccount_name();
        holder.position = neracaAwalAkuns.get(position).getPosition();
        holder.amount = neracaAwalAkuns.get(position).getNeracaAwalBalances().get(0).getAmount();
        holder.date = neracaAwalAkuns.get(position).getNeracaAwalBalances().get(0).getDate();
    }

    @Override
    public int getItemCount() {
        return neracaAwalAkuns.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int id, id_account, id_classification, amount;
        private int pYear, pMonth, pDay;
        private String date, formatDateStore, formatDateID, account_code, account_name, position;
        private TextView tv_akun, tv_amount, tv_date, tv_position, tv_warn, id_akun;
        private View v_line;
        private ImageView iv_edit;
        private EditText et_amount;
        private LinearLayout ll_button, ll_date;
        private LottieAnimationView av_loading_dialog;
        private ArrayList<AkunAkun> akunAkunArrayList;
        private ArrayAdapter<AkunAkun> akunArrayAdapter;
        Dialog dialog_edit;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_akun = itemView.findViewById(R.id.tv_akun);
            tv_position = itemView.findViewById(R.id.tv_position);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            v_line = itemView.findViewById(R.id.v_line);
            iv_edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            showDialogEdit();
        }

        void showDialogEdit() {
            dialog_edit = new Dialog(itemView.getContext());
            dialog_edit.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog_edit.setCancelable(false);
            dialog_edit.setCanceledOnTouchOutside(false);
            dialog_edit.setContentView(R.layout.dialog_add_neraca_awal);
            dialog_edit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            DisplayMetrics metrics = itemView.getContext().getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            dialog_edit.getWindow().setLayout(width, height);

            Spinner sp_akun = dialog_edit.findViewById(R.id.sp_akun);
            id_akun = dialog_edit.findViewById(R.id.id_akun);
            tv_date = dialog_edit.findViewById(R.id.tv_date);
            et_amount = dialog_edit.findViewById(R.id.et_amount);
            tv_warn = dialog_edit.findViewById(R.id.tv_warn);
            ll_date = dialog_edit.findViewById(R.id.ll_date);
            ll_button = dialog_edit.findViewById(R.id.ll_button);
            av_loading_dialog = dialog_edit.findViewById(R.id.av_loading_dialog);

            String tempDay, tempMonth, tempYear;
            tempYear = date.substring(0, 4);
            tempMonth = date.substring(5, 7);
            tempDay = date.substring(8, 10);

            formatDateStore = tempYear + "-" + tempMonth + "-" + tempDay;
            formatDateID = tempDay + "-" + tempMonth + "-" + tempYear;
            tv_date.setText(formatDateID);
            et_amount.setText(String.valueOf(amount));
            id_akun.setText(String.valueOf(id_account));

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
                    DatePickerDialog dialog = new DatePickerDialog(itemView.getContext(), pDateSetListener, pYear, pMonth, pDay);
                    dialog.getDatePicker().setMaxDate(new Date().getTime());
                    dialog.show();
                }
            });

            User user = SharedPrefManager.getInstance(itemView.getContext()).getUser();
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
                            akunArrayAdapter = new ArrayAdapter<>(itemView.getContext(), R.layout.layout_spinner, akunAkunArrayList);
                            akunArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            AkunAkun akunSelected = new AkunAkun(id_account, id_classification, account_code, account_name, position);
                            Log.e("AKUN", akunSelected.toString());
                            int selectedPosition = sp_akun.getSelectedItemPosition();
                            selectedPosition = akunAkunArrayList.indexOf(akunSelected);
                            sp_akun.setAdapter(akunArrayAdapter);
                            sp_akun.setSelection(selectedPosition);
                        }
                    } else {
                        dialog_edit.dismiss();
                        Toast.makeText(itemView.getContext(), "Gagal mengambil data akun", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AkunAkunResponse> call, Throwable t) {
                    dialog_edit.dismiss();
                    Toast.makeText(itemView.getContext(), "Terjadi kesalahan, coba beberapa saat lagi", Toast.LENGTH_SHORT).show();
                }
            });

            sp_akun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    AkunAkun akunAkun = (AkunAkun) adapterView.getSelectedItem();
                    id_akun = dialog_edit.findViewById(R.id.id_akun);
                    id_akun.setText(String.valueOf(akunAkun.getId()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            MaterialButton cancel = dialog_edit.findViewById(R.id.btn_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_edit.dismiss();
                }
            });

            MaterialButton save = dialog_edit.findViewById(R.id.btn_save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editNeracaAwal();
                }
            });
            dialog_edit.show();
        }

        void editNeracaAwal() {
            tv_warn.setVisibility(View.GONE);
            ll_button.setVisibility(View.GONE);
            av_loading_dialog.setVisibility(View.VISIBLE);
            av_loading_dialog.playAnimation();
            User user = SharedPrefManager.getInstance(itemView.getContext()).getUser();
            String token = "Bearer " + user.getToken();

            int _id_akun = Integer.parseInt(id_akun.getText().toString());
            String amount = et_amount.getText().toString();

            if (amount.isEmpty()) {
                et_amount.setError("Jumlah tidak boleh kosong");
                et_amount.requestFocus();
                return;
            }

            Call<NeracaAwalStoreResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .updateNeracaAwal(token, id, _id_akun, formatDateStore, amount);

            call.enqueue(new Callback<NeracaAwalStoreResponse>() {
                @Override
                public void onResponse(Call<NeracaAwalStoreResponse> call, Response<NeracaAwalStoreResponse> response) {
                    NeracaAwalStoreResponse neracaAwalStoreResponse = response.body();
                    if (response.isSuccessful()) {
                        if (neracaAwalStoreResponse.isSuccess()) {
                            Toast.makeText(itemView.getContext(), "Neraca Awal berhasil diubah", Toast.LENGTH_LONG).show();
                            av_loading_dialog.setVisibility(View.GONE);
                            av_loading_dialog.cancelAnimation();
                            dialog_edit.dismiss();
                            if (itemView.getContext() instanceof NeracaAwalActivity) {
                                ((NeracaAwalActivity)itemView.getContext()).getNeracaAwal();
                            }
                        }
                    } else {
                        av_loading_dialog.setVisibility(View.GONE);
                        av_loading_dialog.cancelAnimation();
                        tv_warn.setVisibility(View.VISIBLE);
                        ll_button.setVisibility(View.VISIBLE);
                        Log.i("NERACA_AWAL ", neracaAwalStoreResponse.toString());
                        Toast.makeText(itemView.getContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NeracaAwalStoreResponse> call, Throwable t) {
                    Log.d("NERACA_AWAL ", t.toString());
                    av_loading_dialog.setVisibility(View.GONE);
                    av_loading_dialog.cancelAnimation();
                    tv_warn.setVisibility(View.VISIBLE);
                    ll_button.setVisibility(View.VISIBLE);
                    Toast.makeText(itemView.getContext(), "Terjadi kesalahan, coba beberapa saat lagi", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}