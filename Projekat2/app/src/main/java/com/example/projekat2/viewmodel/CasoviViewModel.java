package com.example.projekat2.viewmodel;

import android.app.Application;
import com.example.projekat2.model.CasFilter;
import com.example.projekat2.repository.CasoviRepository;
import com.example.projekat2.repository.room.CasEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class CasoviViewModel extends AndroidViewModel {

    private CasoviRepository repo;

    private MutableLiveData<CasFilter> filter;
    private LiveData<List<CasEntity>> data;

    public CasoviViewModel(@NonNull Application application) {
        super(application);
        repo = new CasoviRepository(application);
        filter = new MutableLiveData<>();
        data = Transformations.switchMap(filter, filter -> repo.getCasovi(filter));
        filter.setValue(new CasFilter("","","",""));
    }

    public void setFilter(CasFilter filter) {
        this.filter.setValue(filter);
    }

    public LiveData<List<CasEntity>> getCasoviLiveData() {
        return data;
    }

}
