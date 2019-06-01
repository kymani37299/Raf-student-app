package com.example.projekat2.model;

import java.io.Serializable;

public class CasFilter implements Serializable {

    private String grupa;
    private String dan;
    private String textFilter;
    private String ucionica;

    public CasFilter(String grupa,String dan,String textFilter,String ucionica) {
        this.grupa = grupa;
        this.dan = dan;
        this.textFilter = textFilter;
        this.ucionica = ucionica;
    }

    public String getGrupa() {
        return grupa;
    }

    public String getDan() {
        return dan;
    }

    public String getTextFilter() {
        return textFilter;
    }

    public String getUcionica() {
        return ucionica;
    }
}
