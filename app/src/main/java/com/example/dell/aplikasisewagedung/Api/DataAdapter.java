package com.example.dell.aplikasisewagedung.Api;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.aplikasisewagedung.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<Daftar> DataKu;

    public DataAdapter(ArrayList<Daftar> DataKu) {
        this.DataKu = DataKu;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        holder.txtnama.setText(DataKu.get(position).getNama());
        holder.txttlp.setText(DataKu.get(position).getTlp());
        holder.txtalamat.setText(DataKu.get(position).getAlamat());
        holder.txtsesi.setText(DataKu.get(position).getSesi());
        holder.txttanggal.setText(DataKu.get(position).getTanggal());
        holder.txtstatus.setText(DataKu.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return DataKu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtnama, txttlp, txtalamat, txtsesi, txttanggal, txtstatus;

        public ViewHolder(View itemView) {
            super(itemView);

            txtnama = (TextView)itemView.findViewById(R.id.txt_nama);
            txttlp = (TextView)itemView.findViewById(R.id.txt_tlp);
            txtalamat = (TextView)itemView.findViewById(R.id.txt_alamat);
            txtsesi = (TextView)itemView.findViewById(R.id.txt_sesi);
            txttanggal = (TextView)itemView.findViewById(R.id.txt_tanggal);
            txtstatus = (TextView)itemView.findViewById(R.id.txt_status);

        }
    }
}
