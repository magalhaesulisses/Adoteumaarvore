package com.example.tccadoteumaarvore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tccadoteumaarvore.R;
import com.example.tccadoteumaarvore.activity.ui.profile.ProfileFragment;
import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.example.tccadoteumaarvore.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class MainActivity extends AppCompatActivity {

    private EditText useremail;
    private EditText userpassword;
    private Usuario usuario;
    private FirebaseAuth auth;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        useremail = findViewById(R.id.useremail);
        userpassword = findViewById(R.id.userpassword);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = useremail.getText().toString();
                String textPword = userpassword.getText().toString();

                if (textEmail.isEmpty() || textPword.isEmpty()){
                    if (textEmail.isEmpty()){
                        Toast.makeText(MainActivity.this, "Informe o email!", Toast.LENGTH_LONG).show();
                    }
                    if (textPword.isEmpty()){
                        Toast.makeText(MainActivity.this, "Informe a senha!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    usuario = new Usuario();
                    usuario.setEmail(textEmail);
                    usuario.setSenha(textPword);
                    validarLogin();
                }
            }
        });
    }

    public void validarLogin(){
        auth = ConfigFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Intent i = new Intent(MainActivity.this, PrincipalActivity.class);
                    startActivity(i);

                    //Intent Teste
                    /**
                    Intent i = new Intent(MainActivity.this, CrawlerActivity.class);
                    startActivity(i);
                    **/

                }else{
                    String excpt;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        excpt = "E-mail não corresponde a um usuário cadastrado!";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        excpt = "E-mail e senha não correspondem a um usuário cadastrado!";
                    } catch (Exception e) {
                        excpt = "Ocorreu um erro ao realizar o login: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, excpt, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}