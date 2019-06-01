package com.example.projekat2.repository.room;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "casovi")
public class CasEntity {
    @Ignore
    public static final MutableLiveData<List<String>> grupeLiveData = new MutableLiveData<>();

    @Ignore
    private static final ArrayList<String> grupeAll = new ArrayList<>();

    @Ignore
    public static final String dani[] = {"","PON","UTO","SRE","CET","PET"};

    static {
        grupeAll.add("");
        grupeLiveData.setValue(grupeAll);
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String predmet;
    private String tip;
    private String nastavnik;
    private String grupe;
    private String dan;
    private String termin;
    private String ucionica;

    public CasEntity(String predmet, String tip, String nastavnik, String grupe, String dan, String termin, String ucionica) {
        this.predmet = predmet;
        this.tip = tip;
        this.nastavnik = nastavnik;
        this.grupe = grupe;
        this.dan = dan;
        this.termin = termin;
        this.ucionica = ucionica;

        String grupearr[] = grupe.split(", ");

        for(int i=0;i<grupearr.length;i++) {
            if(!grupeAll.contains(grupearr[i])) {
                addGrupa(grupearr[i]);
            }
        }

    }

    @Ignore
    public void addGrupa(String grupa) {
        grupeAll.add(grupa);
        grupeLiveData.postValue(grupeAll);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
