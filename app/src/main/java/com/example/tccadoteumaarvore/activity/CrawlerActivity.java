package com.example.tccadoteumaarvore.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tccadoteumaarvore.R;
import com.example.tccadoteumaarvore.api.InformativoService;
import com.example.tccadoteumaarvore.model.Informativo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrawlerActivity extends AppCompatActivity {
    private String textScraped;
    private TextView txtResultScrap;
    //Api References (Cut)
    private Retrofit retrofit;
    ArrayList<Informativo> informativosList;

    //Test
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crawler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtResultScrap = findViewById(R.id.txtResultScrap);
        btn = findViewById(R.id.bntInformativo);

        //OnCreate Activity(Informativo) Body
        retrofit = new Retrofit.Builder()
                //.baseUrl("https://www.jsonkeeper.com/b/") TESTE
                .baseUrl("http://apiadvisor.climatempo.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtResultScrap.setText("Consultado");

                //Cria um objeto RetroFit para consumo de obj web
                InformativoService infoService = retrofit.create(InformativoService.class);
                Call<ArrayList<Informativo>> call = infoService.recuperarInformativo();

                call.enqueue(new Callback<ArrayList<Informativo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Informativo>> call, Response<ArrayList<Informativo>> response) {
                        if (response.isSuccessful()) {
                            informativosList = response.body();
                            Informativo info = informativosList.get(0);
                            txtResultScrap.setText(info.getCountry());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Informativo>> call, Throwable throwable) {
                        Toast.makeText(CrawlerActivity.this, "Ocorreu um erro durante a consulta", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        /** Scrapper Example
         new Thread(new Runnable() {
        @Override public void run() {
        try{
        Document doc = Jsoup.connect("https://www.toledo.pr.gov.br/secretarias/secretaria_meio_ambiente/programas_da_secretaria/viveiro_municipal_de_mudas")
        .timeout(6000).get();

        Elements tableSpecies = doc.select("td[p]");
        textScraped = tableSpecies.text();
        }catch (Exception e){

        }
        runOnUiThread(new Runnable() {
        @Override public void run() {
        txtResultScrap.setText(textScraped);
        }
        });
        }
        }).start();
         **/
    }
}