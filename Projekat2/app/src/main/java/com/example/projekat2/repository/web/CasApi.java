package com.example.projekat2.repository.web;

import java.util.List;

import retrofit2.Call;

public class CasApi {

    private CasService service;

    public CasApi() {
        service = ServiceGenerator.createService(CasService.class);
    }

    public Call<List<CasWeb>> getCasovi() {
        return service.getCasovi();
    }
}
