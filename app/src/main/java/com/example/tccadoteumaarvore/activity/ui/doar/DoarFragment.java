package com.example.tccadoteumaarvore.activity.ui.doar;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tccadoteumaarvore.activity.adapter.AdapterDoacoes;
import com.example.tccadoteumaarvore.activity.adapter.AdapterEspecies;
import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.databinding.FragmentDoarBinding;
import com.example.tccadoteumaarvore.model.Arvore;
import com.example.tccadoteumaarvore.model.Doacao;
import com.example.tccadoteumaarvore.model.Usuario;
import com.example.tccadoteumaarvore.utils.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoarFragment extends Fragment {
    private FragmentDoarBinding binding;
    private DatabaseReference reference = ConfigFirebase.getFirebaseRef();
    private Spinner listaEspecies;
    private ArrayList<String> especies;
    private ArrayAdapter<String> adapter;
    private ArrayList<Doacao> listaDoacoes = new ArrayList<>();
    private Usuario usuarioDoacao;
    private SeekBar seekBar;
    private TextView seekBarText;
    private Arvore arvore;
    private AdapterDoacoes adapterDoacoes;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Adapter
        adapterDoacoes = new AdapterDoacoes(listaDoacoes);

        //Recycler
        recyclerView = binding.recyclerViewDoar;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapterDoacoes);
        popularListaDoacoes();

        //Spinner
        listaEspecies = binding.spinnerDoarEspecie;
        especies = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, especies);
        listaEspecies.setAdapter(adapter);

        //SeekBar
        seekBar = (SeekBar) binding.seekBarDoarQuantidade;
        seekBarText = (TextView) binding.seekBarValue;
        seekBar.setMax(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarText.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //Doação
        binding.buttonDoar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doacao doacao = new Doacao();
                String spinnerEspecie = binding.spinnerDoarEspecie.getSelectedItem().toString();
                String uui = usuarioDoacao.getLogin() + spinnerEspecie;

                doacao.setUui(uui);
                doacao.setUserid(usuarioDoacao.getUui());
                doacao.setNome(usuarioDoacao.getNome());
                doacao.setTelefone(usuarioDoacao.getFone());
                doacao.setEspecie(spinnerEspecie);
                doacao.setQuantidade(seekBarText.getText().toString());
                doacao.salvarDoacao();

                adapterDoacoes.notifyDataSetChanged();
                popularListaDoacoes();
            }
        });
        carregarEspecies();
        carregaUsuario();
    }
    public void carregarEspecies(){
        DatabaseReference referenceArvores = reference.child("arvores");
        referenceArvores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                especies.clear();
                arvore = new Arvore();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    arvore = postSnapshot.getValue(Arvore.class);
                    if(arvore !=null){
                        especies.add(arvore.getPopular());
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erro ao buscar dados", error.toException());
            }
        });
    }
    public void carregaUsuario(){
        FirebaseAuth userAuth = ConfigFirebase.getFirebaseAuth();
        String uuiUser = Base64Custom.encodeBase64(userAuth.getCurrentUser().getEmail());

        DatabaseReference referenceUser = reference.child("usuarios").child(uuiUser);
        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuarioDoacao = snapshot.getValue(Usuario.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erro ao buscar dados", error.toException());
            }
        });
    }
    public void popularListaDoacoes(){
        DatabaseReference referenceDoacoes = reference.child("doacoes");
        referenceDoacoes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDoacoes.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Doacao doacao = postSnapshot.getValue(Doacao.class);
                    listaDoacoes.add(doacao);
                }
                adapterDoacoes.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erro ao buscar dados", error.toException());
            }
        });
    }
}