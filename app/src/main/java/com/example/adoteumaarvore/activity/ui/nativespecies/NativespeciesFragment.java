package com.example.adoteumaarvore.activity.ui.nativespecies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.adoteumaarvore.databinding.FragmentNativespeciesBinding;

public class NativespeciesFragment extends Fragment {

    private FragmentNativespeciesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NativespeciesViewModel nativespeciesViewModel =
                new ViewModelProvider(this).get(NativespeciesViewModel.class);

        binding = FragmentNativespeciesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNativespecies;
        nativespeciesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}