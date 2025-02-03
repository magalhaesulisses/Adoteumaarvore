package com.example.tccadoteumaarvore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Arvore implements Serializable {

     private int id;
     private String cientifico;
     private String popular;
     private Date datacadastro;
     private String luminosidade;
     private ArrayList<String> origem;
     private ArrayList<String> clima;
     private String ciclovida;
     private String porte;
     private String familia;
     private String adubo;
     private String rega;
     private Doador doador;

    public Doador getDoador() {
        return doador;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }

    public Arvore() {
    }

    public ArrayList<String> getOrigem() {
        return origem;
    }

    public void setOrigem(ArrayList<String> origem) {
        this.origem = origem;
    }

    public ArrayList<String> getClima() {
        return clima;
    }

    public void setClima(ArrayList<String> clima) {
        this.clima = clima;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCientifico() {
        return cientifico;
    }

    public void setCientifico(String cientifico) {
        this.cientifico = cientifico;
    }

    public String getPopular() {
        return popular;
    }

    public void setPopular(String popular) {
        this.popular = popular;
    }

    public Date getDatacadastro() {
        return datacadastro;
    }

    public void setDatacadastro(Date datacadastro) {
        this.datacadastro = datacadastro;
    }

    public String getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(String luminosidade) {
        this.luminosidade = luminosidade;
    }

    public String getCiclovida() {
        return ciclovida;
    }

    public void setCiclovida(String ciclovida) {
        this.ciclovida = ciclovida;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getAdubo() {
        return adubo;
    }

    public void setAdubo(String adubo) {
        this.adubo = adubo;
    }

    public String getRega() {
        return rega;
    }

    public void setRega(String rega) {
        this.rega = rega;
    }
}
