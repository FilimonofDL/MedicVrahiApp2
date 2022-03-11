package com.medic.medicvrahiapp.model;


import java.util.ArrayList;
import java.util.List;

public class User {
    public  static User vibraniUser = new User();

    public User() {
    }


    private String imia = "Имя";
    private String othestvo = "Отчество";
    private String familia = "Фамилия";
    private String telefon = "+";
    private String pass = "";
    private String googleUID = "null";

    public String getImia() {
        return imia;
    }

    public void setImia(String imia) {
        this.imia = imia;
    }

    public String getOthestvo() {
        return othestvo;
    }

    public void setOthestvo(String othestvo) {
        this.othestvo = othestvo;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGoogleUID() {
        return googleUID;
    }

    public void setGoogleUID(String googleUID) {
        this.googleUID = googleUID;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public User(String imia, String othestvo, String familia, String telefon,  String googleUID, List<String> tokens) {

        this.imia = imia;
        this.othestvo = othestvo;
        this.familia = familia;
        this.telefon = telefon;
        this.googleUID = googleUID;
        this.tokens = tokens;
    }

    private List<String> tokens=new ArrayList<>();
}