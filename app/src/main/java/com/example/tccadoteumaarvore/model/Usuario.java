package com.example.tccadoteumaarvore.model;

import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    private String uui;
    private String nome;
    private String sobrenome;
    private String login;
    private String email;
    private String fone;
    private String senha;

    public Usuario() {
    }

    public void salvarUsuario(){
        DatabaseReference reference = ConfigFirebase.getFirebaseRef();
        reference.child("usuarios").child(this.uui).setValue(this);
    }

    public String getUui() {
        return uui;
    }

    public void setUui(String uui) {
        this.uui = uui;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
