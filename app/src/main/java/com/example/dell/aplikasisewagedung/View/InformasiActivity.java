package com.example.dell.aplikasisewagedung.View;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dell.aplikasisewagedung.Api.ApiClient;
import com.example.dell.aplikasisewagedung.Api.ApiInterface;
import com.example.dell.aplikasisewagedung.Api.Daftar;
import com.example.dell.aplikasisewagedung.Api.DataAdapter;
import com.example.dell.aplikasisewagedung.Api.JSONResponse;
import com.example.dell.aplikasisewagedung.R;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformasiActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ArrayList<Daftar> data;
    private DataAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Load Data ...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);

        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON() {
        progressDialog.show();

        ApiInterface request = ApiClient.getApiClient().create(ApiInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                progressDialog.dismiss();
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getInformasi()));
                adapter = new DataAdapter(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Error",t.getMessage());
            }
        });
    }
}
