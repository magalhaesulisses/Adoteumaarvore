package com.example.tccadoteumaarvore.model;

import com.example.tccadoteumaarvore.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

public class Plantio {

    private String uui;
    private Double posLatitude;
    private Double posLongitude;
    private String apelido;
    private String especie;
    private String sobre;
    private String uuiUser;

    public Plantio() {
    }

    public void salvarPlantio(){
        DatabaseReference reference = ConfigFirebase.getFirebaseRef();
        reference.child("plantios").child(this.uui).setValue(this);
    }

    public String getUui() {
        return uui;
    }

    public void setUui(String uui) {
        this.uui = uui;
    }

    public Double getPosLatitude() {
        return posLatitude;
    }

    public void setPosLatitude(Double posLatitude) {
        this.posLatitude = posLatitude;
    }

    public Double getPosLongitude() {
        return posLongitude;
    }

    public void setPosLongitude(Double posLongitude) {
        this.posLongitude = posLongitude;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public String getUuiUser() {
        return uuiUser;
    }

    public void setUuiUser(String uuiUser) {
        this.uuiUser = uuiUser;
    }
}
