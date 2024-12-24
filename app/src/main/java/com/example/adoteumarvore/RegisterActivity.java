package com.example.adoteumarvore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        btnRegisterReg.setOnClickListener(new View.OnClickListener() {
            private int validateCampos(){
                String strNome              = edtNomeReg.getText().toString();
                String strSobrenome         = edtSobrenomeReg.getText().toString();
                String strDataNascimento    = edtDataNascimentoReg.getText().toString();
                String strLogin             = edtLoginReg.getText().toString();
                String strEmail             = edtEmailReg.getText().toString();
                String strFone              = edtFoneReg.getText().toString();
                String strSenha             = edtSenhaReg.getText().toString();
                String strSenhaConfirmacao  = edtSenhaConfirmacaoReg.getText().toString();

                if (strNome.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Informe um nome!", Toast.LENGTH_LONG).show();
                    return 0;
                }
                if (strSobrenome.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Informe um sobrenome!", Toast.LENGTH_LONG).show();
                    return 0;
                }
                if (strLogin.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Informe um login!", Toast.LENGTH_LONG).show();
                    return 0;
                }
                if (strDataNascimento.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Informe uma data de nascimento!", Toast.LENGTH_LONG).show();
                    return 0;
                }
                if (strEmail.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Informe um email!", Toast.LENGTH_LONG).show();
                    return 0;
                }
                if (strFone.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Informe um celular!", Toast.LENGTH_LONG).show();
                    return 0;
                }
                if (strSenha.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Informe uma senha!", Toast.LENGTH_LONG).show();
                    return 0;
                }
                if (strSenhaConfirmacao.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Confirme sua senha!", Toast.LENGTH_LONG).show();
                    return 0;
                }
                if (!strSenha.equals(strSenhaConfirmacao)){
                    Toast.makeText(RegisterActivity.this, "As senhas informadas não coincidem!", Toast.LENGTH_LONG).show();
                    return 0;
                }
                else
                    return 1;
            };
            @Override
            public void onClick(View v) {
                if (validateCampos() == 1){
                    //
                }
            }
        });
    }
}