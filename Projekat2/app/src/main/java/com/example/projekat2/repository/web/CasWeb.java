package com.example.projekat2.repository.web;

import com.google.gson.annotations.SerializedName;

public class CasWeb {

    @SerializedName("predmet")
    private String predmet;

    @SerializedName("tip")
    private String tip;

    @SerializedName("nastavnik")
    private String nastavnik;

    @SerializedName("grupe")
    private String grupe;

    @SerializedName("dan")
    private String dan;

    @SerializedName("termin")
    private String termin;

    @SerializedName("ucionica")
    private String ucionica;

    public CasWeb(String predmet, String tip, String nastavnik, String grupe, String dan, String termin,String ucionica) {
        this.predmet = predmet;
        this.tip = tip;
        this.nastavnik = nastavnik;
        this.grupe = grupe;
        this.dan = dan;
        this.termin = termin;
        this.ucionica = ucionica;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getNastavnik() {
        return nastavnik;
    }

    public void setNastavnik(String nastavnik) {
        this.nastavnik = nastavnik;
    }

    public String getGrupe() {
        return grupe;
    }

    public void setGrupe(String grupe) {
        this.grupe = grupe;
    }

    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public String getUcionica() {
        return ucionica;
    }

    public void setUcionica(String ucionica) {
        this.ucionica = ucionica;
    }
}
