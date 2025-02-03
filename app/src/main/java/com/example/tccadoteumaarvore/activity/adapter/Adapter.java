package com.example.tccadoteumaarvore.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccadoteumaarvore.R;
import com.example.tccadoteumaarvore.model.Arvore;
import com.example.tccadoteumaarvore.model.Doador;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private ArrayList<Arvore> listaEspecies;

    public Adapter(ArrayList<Arvore> list) {
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
        Doador doador = arvore.getDoador();
        holder.cientifico.setText(arvore.getCientifico());
        holder.popular.setText(arvore.getPopular());
        holder.origem.setText(arvore.getOrigem().toString());
        holder.porte.setText(arvore.getPorte());
        holder.rega.setText(arvore.getRega());
        holder.nomeDoador.setText(doador.getNome());
        holder.telefoneDoador.setText(doador.getTelefone());
    }

    @Override
    public int getItemCount() {
        return listaEspecies.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imagem;
        TextView cientifico;
        TextView popular;
        TextView origem;
        TextView porte;
        TextView rega;
        TextView nomeDoador;
        TextView telefoneDoador;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem      = itemView.findViewById(R.id.imgSImagem);
            cientifico  = itemView.findViewById(R.id.txtSCientifico);
            popular     = itemView.findViewById(R.id.txtSPopular);
            origem      = itemView.findViewById(R.id.txtSOrigem);
            porte       = itemView.findViewById(R.id.txtSPorte);
            rega        = itemView.findViewById(R.id.txtSRega);
            nomeDoador  = itemView.findViewById(R.id.txtSDoarNome);
            telefoneDoador = itemView.findViewById(R.id.txtSDoarTelefone);
        }
    }
}
