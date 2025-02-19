package com.example.tccadoteumaarvore.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tccadoteumaarvore.R;
import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.model.Arvore;
import com.example.tccadoteumaarvore.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EspecieActivity extends AppCompatActivity {
    private DatabaseReference reference = ConfigFirebase.getFirebaseRef();
    private TextView txtAdubo;
    private TextView txtCicloVida;
    private TextView txtCientifico;
    private TextView txtClima;
    private TextView txtFamilia;
    private TextView txtLuminosidade;
    private TextView txtOrigem;
    private TextView txtPopular;
    private TextView txtPorte;
    private TextView txtRega;
    private Button btnVoltar;
    private Arvore arvore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_especie);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle dados = getIntent().getExtras();
        String indiceSpinner = dados.getString("position");
        carregarEspecie(indiceSpinner);

        txtAdubo        = findViewById(R.id.especieAdubo);
        txtCicloVida    = findViewById(R.id.especieCicloVida);
        txtCientifico   = findViewById(R.id.especieCientifico);
        txtClima        = findViewById(R.id.especieClima);
        txtFamilia      = findViewById(R.id.especieFamilia);
        txtLuminosidade = findViewById(R.id.especieLuminosidade);
        txtOrigem       = findViewById(R.id.especieOrigem);
        txtPopular      = findViewById(R.id.especiePopular);
        txtPorte        = findViewById(R.id.especiePorte);
        txtRega         = findViewById(R.id.especieRega);
        btnVoltar       = findViewById(R.id.buttonVoltar);

        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }
    public void carregarEspecie(String arvoreRef){

        DatabaseReference referenceArvores = reference.child("arvores").child(arvoreRef);
        referenceArvores.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arvore = snapshot.getValue(Arvore.class);
                carregarInformacoes();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erro ao buscar dados", error.toException());
            }
        });
    }

    public void carregarInformacoes(){
        String adubo, ciclovida, clima, familia,
                luminosidade, origem, porte, rega;

        if (arvore != null){
            adubo       = "Adubo: " + arvore.getAdubo();
            ciclovida   = "Ciclo de Vida: " + arvore.getCiclovida();
            clima       = "Clima: " + arvore.getClima();
            familia     = "Família: " + arvore.getFamilia();
            luminosidade= "Luminosidade: " + arvore.getLuminosidade();
            origem      = "Origem: " + arvore.getOrigem();
            porte       = "Porte: " + arvore.getPorte();
            rega        = "Rega: " + arvore.getRega();

            txtAdubo        .setText(adubo);
            txtCicloVida    .setText(ciclovida);
            txtCientifico   .setText(arvore.getCientifico());
            txtClima        .setText(clima);
            txtFamilia      .setText(familia);
            txtLuminosidade .setText(luminosidade);
            txtOrigem       .setText(origem);
            txtPopular      .setText(arvore.getPopular());
            txtPorte        .setText(porte);
            txtRega         .setText(rega);
        }
    }
}