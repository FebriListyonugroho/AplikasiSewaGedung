package com.example.dell.aplikasisewagedung.View;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.aplikasisewagedung.Api.ApiClient;
import com.example.dell.aplikasisewagedung.Api.ApiInterface;
import com.example.dell.aplikasisewagedung.Api.Daftar;
import com.example.dell.aplikasisewagedung.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DaftarActivity extends AppCompatActivity implements OnClickListener{

    private int Hari, Bulan, Tahun;
    private EditText txtDate,txtnama,txttlp,txtalamat;
    private Spinner txtsesi;
    private Dialog pesan;
    ProgressDialog progressDialog;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses Daftar ...");
        ///data
       txtnama = (EditText)findViewById(R.id.isinama);
       txttlp = (EditText)findViewById(R.id.isitlp);
       txtalamat = (EditText)findViewById(R.id.isialamat);
       txtDate = (EditText) findViewById(R.id.txt_tanggal);
       txtsesi = (Spinner)findViewById(R.id.sesi);

       Button pilihDate = (Button)findViewById(R.id.pil_date);
       Button btnBatal = (Button) findViewById(R.id.btn_batal);
       Button btnDaftar = (Button)findViewById(R.id.btn_daftar);
        btnDaftar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = txtnama.getText().toString().trim();
                String tlp = txttlp.getText().toString().trim();
                String alamat = txtalamat.getText().toString().trim();
                String sesi = txtsesi.getSelectedItem().toString().trim();
                String tanggal = txtDate.getText().toString();

                if (nama.isEmpty()){
                    txtnama.setError("Tolong Isi Nama");
                }else if (tlp.isEmpty()){
                    txttlp.setError("Tolong Isi No.Telepon");
                }else if (alamat.isEmpty()){
                    txtalamat.setError("Tolong Isi Alamat");
                }else if (tanggal.isEmpty()){
                    txtDate.setError("Harap Isi Tanggal");
                }else{
                    btnDaftar(nama, tlp, alamat, sesi, tanggal);
                }
            }
        });
       btnBatal.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(DaftarActivity.this, MainActivity.class);
               startActivity(i);
           }
       });
       pilihDate.setOnClickListener(this);

    }
    ///METHOD INSERT DATA
    private void btnDaftar(final String nama, final String tlp, final String alamat, final String sesi, final String tanggal) {

        progressDialog.show();


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Daftar>call = apiInterface.btnDaftar(nama, tlp, alamat ,sesi, tanggal);

        call.enqueue(new Callback<Daftar>() {
            @Override
            public void onResponse(@NonNull Call<Daftar> call, @NonNull Response<Daftar> response) {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if (success){

                        AlertDialog.Builder builder= new AlertDialog.Builder(DaftarActivity.this);

                        builder.setTitle("\t\t\t\t\t\t\tBooking berhasil\n")
                                .setMessage("Nama    : "+nama+"\nNo.Tlp   : "+tlp+"\nAlamat  : "+alamat+"\nSesi       : "+sesi+"\nTanggal : "+tanggal+"\n\nSilahkan melakukan pembayaran ke kantor DISHUB")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(DaftarActivity.this , MainActivity.class);
                                        startActivity(i);

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                        /*Toast.makeText(DaftarActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_LONG).show();
                        finish();*/
                    }else {
                        AlertDialog.Builder builder= new AlertDialog.Builder(DaftarActivity.this);

                        builder.setTitle("\t\t\t\t\t\t\t\t\t\t\tMaaf !")
                                .setMessage("Tanggal dan Sesi Yang Anda Pilih Telah Dibooking")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(DaftarActivity.this, DaftarActivity.class);
                                        startActivity(i);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                        /*Toast.makeText(DaftarActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_LONG).show();*/
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Daftar> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DaftarActivity.this,
                        t.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
    ///TANGGAL
    @Override
    public void onClick(View v) {
        Calendar c = Calendar.getInstance();
        Tahun = c.get(Calendar.YEAR);
        Bulan = c.get(Calendar.MONTH);
        Hari = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        txtDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                    }
                }, Tahun, Bulan, Hari);
        datePickerDialog.show();
    }
}
