package com.example.tccadoteumaarvore.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccadoteumaarvore.R;
import com.example.tccadoteumaarvore.model.Arvore;

import java.util.ArrayList;

public class AdapterEspecies extends RecyclerView.Adapter<AdapterEspecies.MyViewHolder> {
    private ArrayList<Arvore> listaEspecies;

    public AdapterEspecies(ArrayList<Arvore> list) {
        this.listaEspecies = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_especies_nativas, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Arvore arvore = listaEspecies.get(position);
        holder.cientifico.setText(arvore.getCientifico());
        holder.popular.setText(arvore.getPopular());
    }

    @Override
    public int getItemCount() {
        return listaEspecies.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cientifico;
        TextView popular;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cientifico  = itemView.findViewById(R.id.txtSCientifico);
            popular     = itemView.findViewById(R.id.txtSPopular);
        }
    }
}
