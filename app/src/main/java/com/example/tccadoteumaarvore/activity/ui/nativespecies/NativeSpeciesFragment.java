package com.example.tccadoteumaarvore.activity.ui.nativespecies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccadoteumaarvore.activity.adapter.Adapter;
import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.databinding.FragmentNativespeciesBinding;
import com.example.tccadoteumaarvore.model.Arvore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NativeSpeciesFragment extends Fragment {

    private FragmentNativespeciesBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<Arvore> listaEspecies = new ArrayList<>();
    private Arvore arvore = new Arvore();
    private DatabaseReference reference = ConfigFirebase.getFirebaseRef();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNativespeciesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Listagem de Banco
        this.popularListaArvores();

        //Adapter
        Adapter adapter = new Adapter(listaEspecies);

        //Recycler
        recyclerView = binding.recyclerViewNative;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    //TODO::Debugar Código e verificar problema!
    public void popularListaArvores(){
        DatabaseReference referenceArvores = reference.child("arvores");
        referenceArvores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    arvore = new Arvore();
                    arvore = postSnapshot.getValue(Arvore.class);
                    listaEspecies.add(arvore);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}