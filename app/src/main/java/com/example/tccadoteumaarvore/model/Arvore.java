package com.example.tccadoteumaarvore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Arvore implements Serializable {

     private int id;
     private String cientifico;
     private String popular;
     private String luminosidade;
     private String origem;
     private String clima;
     private String ciclovida;
     private String porte;
     private String familia;
     private String adubo;
     private String rega;

    public Arvore() {
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

    public String getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(String luminosidade) {
        this.luminosidade = luminosidade;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
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
