package com.example.projekat2.repository.web;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CasService {

    @GET("raspored/json.php")
    Call<List<CasWeb>> getCasovi();
}
