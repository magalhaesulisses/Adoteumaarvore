package com.example.tccadoteumaarvore.activity.ui.nativespecies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccadoteumaarvore.activity.adapter.AdapterEspecies;
import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.databinding.FragmentNativespeciesBinding;
import com.example.tccadoteumaarvore.model.Arvore;
import com.example.tccadoteumaarvore.utils.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NativeSpeciesFragment extends Fragment {

    private FragmentNativespeciesBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<Arvore> listaEspecies = new ArrayList<>();
    private DatabaseReference reference = ConfigFirebase.getFirebaseRef();
    private AdapterEspecies adapterEspecies;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNativespeciesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Adapter
        adapterEspecies = new AdapterEspecies(listaEspecies);

        //Recycler
        recyclerView = binding.recyclerViewNative;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapterEspecies);
        popularListaArvores();

        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(getContext(), recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Arvore arvore = listaEspecies.get(position);

                        //Intent i = new Intent() //TODO Criar novo Intent Para Activity Externa

                        Toast.makeText(getContext(), "Clique: "+ arvore.getPopular(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
            )
        );
    }

    public void popularListaArvores(){
        DatabaseReference referenceArvores = reference.child("arvores");
        referenceArvores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaEspecies.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Arvore arvore = postSnapshot.getValue(Arvore.class);
                    listaEspecies.add(arvore);
                }
                adapterEspecies.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erro ao buscar dados", error.toException());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}