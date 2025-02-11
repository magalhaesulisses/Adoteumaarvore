package com.example.tccadoteumaarvore.activity.ui.newplant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.databinding.FragmentNewplantBinding;
import com.example.tccadoteumaarvore.model.Imagem;
import com.example.tccadoteumaarvore.model.Usuario;
import com.example.tccadoteumaarvore.utils.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class NewPlantFragment extends Fragment {

    private FragmentNewplantBinding binding;
    private String especie;
    private String apelido;
    private String sobre;
    private Imagem imagens;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewplantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nSbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                especie = String.valueOf(binding.nStxtSpecies.getText());
                apelido = String.valueOf(binding.nStxtApelido.getText());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}