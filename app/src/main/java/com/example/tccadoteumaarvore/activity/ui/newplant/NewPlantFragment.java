package com.example.tccadoteumaarvore.activity.ui.newplant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.databinding.FragmentNewplantBinding;
import com.example.tccadoteumaarvore.model.Arvore;
import com.example.tccadoteumaarvore.model.Imagem;
import com.example.tccadoteumaarvore.model.Usuario;
import com.example.tccadoteumaarvore.utils.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewPlantFragment extends Fragment {

    private FragmentNewplantBinding binding;
    private DatabaseReference reference = ConfigFirebase.getFirebaseRef();
    private Spinner spinner;
    private ArrayList<String> especies;
    private String especie;
    private String apelido;
    private String sobre;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewplantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Listener Botão OK
        binding.nSbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                especie = (String) spinner.getSelectedItem();
                apelido = String.valueOf(binding.nStxtApelido.getText());
                sobre = String.valueOf(binding.nSedtMutiple.getText());
            }
        });
    }

    public void carregarEspecies(){
        DatabaseReference referenceArvores = reference.child("arvores");
        referenceArvores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    //Arvore arvore;
                    //arvore = postSnapshot.getValue(Arvore.class);
                    //especies.add(arvore.getPopular());
                    especies.add(postSnapshot.getValue(Arvore.class).getPopular());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter<String> adapterSpecies;
        adapterSpecies = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, especies);
        spinner.setAdapter(adapterSpecies);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}