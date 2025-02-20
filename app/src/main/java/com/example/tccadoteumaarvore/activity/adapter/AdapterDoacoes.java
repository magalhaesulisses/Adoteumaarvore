package com.example.tccadoteumaarvore.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tccadoteumaarvore.R;
import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.model.Doacao;
import com.example.tccadoteumaarvore.utils.Base64Custom;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;

public class AdapterDoacoes extends RecyclerView.Adapter<AdapterDoacoes.MyViewHolder> {
    private ArrayList<Doacao> listaDoacoes;

    public AdapterDoacoes(ArrayList<Doacao> list) {
        this.listaDoacoes = list;
    }

    @NonNull
    @Override
    public AdapterDoacoes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_doacoes, parent, false);
        return new AdapterDoacoes.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doacao doacao = listaDoacoes.get(position);
        holder.nome.setText(doacao.getNome());
        holder.telefone.setText(doacao.getTelefone());
        holder.especie.setText(doacao.getEspecie());

        holder.doacaoUser = doacao.getUserid();
        holder.uui = doacao.getUui();

        holder.quantidade.setText(doacao.getQuantidade());
    }

    @Override
    public int getItemCount() {
        return listaDoacoes.size();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        TextView telefone;
        TextView especie;
        TextView quantidade;
        Button btnExcluir;
        String activeUserUui;
        String doacaoUser;
        String uui;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome        = itemView.findViewById(R.id.txtNomeDoador);
            telefone    = itemView.findViewById(R.id.txtTelefoneDoador);
            especie     = itemView.findViewById(R.id.txtEspecieDoador);
            quantidade  = itemView.findViewById(R.id.txtQuantidadeDoador);
            btnExcluir  = itemView.findViewById(R.id.buttonDoacaoRemover);
            activeUserUui = carregaUsuario();
            btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validaBotaoExclusao()){
                        DatabaseReference reference = ConfigFirebase.getFirebaseRef();
                        Task<Void> referenceDoacao = reference.child("doacoes").child(uui).removeValue();
                    }else{
                        Toast.makeText(btnExcluir.getContext(), "Apenas o dono possui permissão de remoção", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public String carregaUsuario(){
            FirebaseAuth userAuth = ConfigFirebase.getFirebaseAuth();
            String uuiUser = Base64Custom.encodeBase64(userAuth.getCurrentUser().getEmail());
            return uuiUser;
        }

        public Boolean validaBotaoExclusao(){
            if (activeUserUui.equals(doacaoUser)){
                return true;
            }else{
                return false;
            }
        }
    }
}
