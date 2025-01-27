package com.example.adoteumaarvore.activity.ui.newplant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.adoteumaarvore.databinding.FragmentNewplantBinding;

public class NewPlantFragment extends Fragment {

    private FragmentNewplantBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewPlantViewModel newplantViewModel =
                new ViewModelProvider(this).get(NewPlantViewModel.class);

        binding = FragmentNewplantBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNewplant;
        newplantViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}