package com.example.tccadoteumaarvore.model;

import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

public class Doacao {
    private String uui;
    private String userid;
    private String nome;
    private String telefone;
    private String especie;
    private String quantidade;

    public Doacao() {
    }

    public void salvarDoacao(){
        DatabaseReference reference = ConfigFirebase.getFirebaseRef();
        reference.child("doacoes").child(this.uui).setValue(this);
    }

    public String getUui() {
        return uui;
    }

    public void setUui(String uui) {
        this.uui = uui;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
}
