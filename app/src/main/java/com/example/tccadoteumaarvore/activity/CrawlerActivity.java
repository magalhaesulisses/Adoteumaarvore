package com.example.tccadoteumaarvore.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tccadoteumaarvore.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CrawlerActivity extends AppCompatActivity {
    private String textScraped;

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

        TextView txtResultScrap = findViewById(R.id.txtResultScrap);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.connect("https://www.toledo.pr.gov.br/secretarias/secretaria_meio_ambiente/programas_da_secretaria/viveiro_municipal_de_mudas")
                            .timeout(6000).get();

                    Elements tableSpecies = doc.select("td[p]");
                    textScraped = tableSpecies.text();
                }catch (Exception e){

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtResultScrap.setText(textScraped);
                    }
                });
            }
        }).start();
    }
}