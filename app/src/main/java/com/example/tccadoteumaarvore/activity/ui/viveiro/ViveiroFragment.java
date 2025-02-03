package com.example.tccadoteumaarvore.activity.ui.viveiro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tccadoteumaarvore.databinding.FragmentViveiroBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ViveiroFragment extends Fragment {
    private FragmentViveiroBinding binding;
    private String resultadoScrap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViveiroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Scrapper Example
        /**
        new Thread(new Runnable() {
            @Override public void run() {
                try{
                    Document doc = Jsoup.connect("https://www.toledo.pr.gov.br/secretarias/secretaria_meio_ambiente/programas_da_secretaria/viveiro_municipal_de_mudas")
                            .timeout(6000).get();

                    Elements tableSpecies = doc.select("table.tBodies[] "); //td[p]
                    resultadoScrap = tableSpecies.text();
                    binding.txtViveiroSpecies.setText(resultadoScrap);
                }
                catch (Exception e){
                    //
                }
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        binding.txtViveiroSpecies.setText(resultadoScrap);
                    }
                });
            }
        }).start();
         **/
    }
}