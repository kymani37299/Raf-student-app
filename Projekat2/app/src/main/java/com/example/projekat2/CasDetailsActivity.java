package com.example.projekat2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.projekat2.model.CasFilter;
import com.example.projekat2.view.adapter.CasoviAdapter;
import com.example.projekat2.viewmodel.CasoviViewModel;

public class CasDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cas_details);

        getSupportActionBar().hide();

        RecyclerView rvCasovi = findViewById(R.id.rv_cas_details_casovi);
        rvCasovi.setLayoutManager(new LinearLayoutManager(this));
        CasoviAdapter adapter = new CasoviAdapter();
        rvCasovi.setAdapter(adapter);

        CasoviViewModel viewModel = ViewModelProviders.of(this).get(CasoviViewModel.class);
        viewModel.getCasoviLiveData().observe(this,(casovi)->{
            adapter.setData(casovi);
        });

        Intent i = getIntent();
        CasFilter filter = (CasFilter) i.getSerializableExtra("filter");
        viewModel.setFilter(filter);
    }
}
