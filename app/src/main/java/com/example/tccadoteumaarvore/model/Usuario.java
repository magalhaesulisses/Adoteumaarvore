package com.example.tccadoteumaarvore.model;

import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {
    private String uui;
    private String nome;
    private String sobrenome;
    //private Date datanascimento;
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

    @Exclude
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

    /*
    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }
     */

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
