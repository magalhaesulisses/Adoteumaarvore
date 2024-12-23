package com.example.adoteumarvore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    EditText edtNomeReg, edtSobrenomeReg, edtDataNascimentoReg, edtLoginReg,
             edtEmailReg, edtFoneReg, edtSenhaReg, edtSenhaConfirmacaoReg;

    Button btnLoginReg, btnRegisterReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtNomeReg              = findViewById(R.id.edtNomeReg);
        edtSobrenomeReg         = findViewById(R.id.edtSobrenomeReg);
        edtDataNascimentoReg    = findViewById(R.id.edtDataNascimentoReg);
        edtLoginReg             = findViewById(R.id.edtLoginReg);
        edtEmailReg             = findViewById(R.id.edtEmailReg);
        edtFoneReg              = findViewById(R.id.edtFoneReg);
        edtSenhaReg             = findViewById(R.id.edtSenhaReg);
        edtSenhaConfirmacaoReg  = findViewById(R.id.edtSenhaConfirmacaoReg);

        btnLoginReg             = findViewById(R.id.btnLoginReg);
        btnRegisterReg          = findViewById(R.id.btnRegisterReg);

        btnLoginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}