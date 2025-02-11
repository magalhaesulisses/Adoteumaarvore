package com.example.tccadoteumaarvore.activity.ui.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tccadoteumaarvore.api.InformativoService;
import com.example.tccadoteumaarvore.databinding.FragmentInformationBinding;
import com.example.tccadoteumaarvore.model.Informativo;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InformationFragment extends Fragment{
    private FragmentInformationBinding binding;
    //API
    private Retrofit retrofit;
    ArrayList<Informativo> informativosList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInformationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrofit = new Retrofit.Builder()
                //.baseUrl("https://www.jsonkeeper.com/b/") TESTE
                .baseUrl("https://apiadvisor.climatempo.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        binding.btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    InformativoService infoService = retrofit.create(InformativoService.class);
                    Call<ArrayList<Informativo>> call = infoService.recuperarInformativo();

                    call.enqueue(new Callback<ArrayList<Informativo>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Informativo>> call, Response<ArrayList<Informativo>> response) {
                            if (response.isSuccessful()) {
                                informativosList = response.body();
                                Informativo info = informativosList.get(0);
                                binding.txtInformation.setText(info.getText());
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<Informativo>> call, Throwable throwable) {
                            Toast.makeText(getContext(), "Ocorreu um erro durante a consulta", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}