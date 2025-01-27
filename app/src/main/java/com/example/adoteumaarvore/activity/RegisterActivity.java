package com.example.adoteumaarvore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adoteumaarvore.R;
import com.example.adoteumaarvore.config.ConfigFirebase;
import com.example.adoteumaarvore.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class RegisterActivity extends AppCompatActivity {
    private EditText edtNomeReg, edtSobrenomeReg, edtDataNascimentoReg, edtLoginReg,
             edtEmailReg, edtFoneReg, edtSenhaReg, edtSenhaConfirmacaoReg;
    private Button  btnRegisterReg;
    private TextView txtEntrarReg;
    private FirebaseAuth auth;
    private Usuario user;

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

        //References
        edtNomeReg              = findViewById(R.id.edtNomeReg);
        edtSobrenomeReg         = findViewById(R.id.edtSobrenomeReg);
        edtDataNascimentoReg    = findViewById(R.id.edtDataNascimentoReg);
        edtLoginReg             = findViewById(R.id.edtLoginReg);
        edtEmailReg             = findViewById(R.id.edtEmailReg);
        edtFoneReg              = findViewById(R.id.edtFoneReg);
        edtSenhaReg             = findViewById(R.id.edtSenhaReg);
        edtSenhaConfirmacaoReg  = findViewById(R.id.edtSenhaConfirmacaoReg);
        btnRegisterReg          = findViewById(R.id.btnRegisterReg);
        txtEntrarReg            = findViewById(R.id.txtEntrarReg);

        txtEntrarReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnRegisterReg.setOnClickListener(new View.OnClickListener() {

            private int validateCampos(){
                //Strings
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
            }

            public void cadastarUsuario(){
                auth = ConfigFirebase.getFirebaseAuth();
                auth.createUserWithEmailAndPassword(user.getEmail(), user.getSenha()).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(i);
                        }else{
                            String excpt;
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e){
                                excpt = "Senha deve conter letras e números!";
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                excpt = "Digite um e-mail válido!";
                            } catch (FirebaseAuthUserCollisionException e){
                                excpt = "E-mail já cadastrado!";
                            } catch (Exception e) {
                                excpt = "Ocorreu um erro ao realizar o cadastro: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(RegisterActivity.this, excpt, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onClick(View v) {
                if (validateCampos() == 1){

                    //Create new User Instance
                    user = new Usuario();
                    user.setNome(edtNomeReg.getText().toString());
                    user.setSobrenome(edtSobrenomeReg.getText().toString());
                    //user.setDatanascimento(edtDataNascimentoReg);
                    user.setLogin(edtLoginReg.getText().toString());
                    user.setEmail(edtEmailReg.getText().toString());
                    user.setFone(edtFoneReg.getText().toString());
                    user.setSenha(edtSenhaReg.getText().toString());

                    cadastarUsuario();
                }
            }
        });

    }
}