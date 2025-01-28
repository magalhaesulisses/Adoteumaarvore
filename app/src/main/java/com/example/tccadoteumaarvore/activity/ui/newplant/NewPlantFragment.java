package com.example.tccadoteumaarvore.activity.ui.newplant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tccadoteumaarvore.databinding.FragmentNewplantBinding;

public class NewPlantFragment extends Fragment {

    private FragmentNewplantBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewPlantViewModel newplantViewModel =
                new ViewModelProvider(this).get(NewPlantViewModel.class);

        binding = FragmentNewplantBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}