package com.example.tccadoteumaarvore.api;

import com.example.tccadoteumaarvore.model.Informativo;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InformativoService {

    @GET("api/v1/anl/synoptic/locale/BR?token=96b7b0472c78719c95d5173b521e77f2")
    Call<ArrayList<Informativo>> recuperarInformativo();
}
