package com.example.projekat2.repository;

import android.app.Application;

import com.example.projekat2.model.CasFilter;
import com.example.projekat2.repository.room.CasDB;
import com.example.projekat2.repository.room.CasDao;
import com.example.projekat2.repository.room.CasEntity;
import com.example.projekat2.repository.web.CasApi;
import com.example.projekat2.repository.web.CasWeb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CasoviRepository {

    private CasApi api;

    private ExecutorService executor;
    private CasDao casDao;

    public CasoviRepository(Application application) {
        api = new CasApi();
        casDao = CasDB.getDb(application).getCasDao();
        executor = Executors.newCachedThreadPool();
        fetchData();
    }

    private void fetchData() {
        api.getCasovi().enqueue(new Callback<List<CasWeb>>() {
            @Override
            public void onResponse(Call<List<CasWeb>> call, Response<List<CasWeb>> response) {
                List<CasEntity> casovi = new ArrayList<>();
                int increment = 0;
                for(CasWeb casWeb : response.body()) {
                    CasEntity cas = new CasEntity(casWeb.getPredmet(),casWeb.getTip(),
                            casWeb.getNastavnik(),casWeb.getGrupe(),casWeb.getDan(),
                            casWeb.getTermin(),casWeb.getUcionica());
                    cas.setId(increment++);
                    casovi.add(cas);
                }
                insertCasovi(casovi);
            }

            @Override
            public void onFailure(Call<List<CasWeb>> call, Throwable t) {
            }
        });
    }

    public void insertCasovi(List<CasEntity> casovi) {
        executor.submit(() -> {casDao.insertAll(casovi);});
    }

    public LiveData<List<CasEntity>> getCasovi(CasFilter filter) {
        return casDao.selectFiltered(filter.getGrupa(),filter.getDan(),filter.getTextFilter(),filter.getUcionica());
    }

    public LiveData<List<CasEntity>> getCasovi() {
        return casDao.getAll();
    }

    public LiveData<CasEntity> getCasById(int id){
        return casDao.getById(id);
    }




}
