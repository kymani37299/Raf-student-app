package com.example.projekat2.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.projekat2.R;
import com.example.projekat2.repository.room.CasEntity;
import com.example.projekat2.model.CasFilter;
import com.example.projekat2.view.adapter.CasoviAdapter;
import com.example.projekat2.viewmodel.CasoviViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RasporedFragment extends Fragment {

    private Spinner spGrupa;
    private Spinner spDan;
    private RecyclerView rvCasovi;
    private EditText etFilter;
    private Button btnTrazi;

    private CasoviAdapter casoviAdapter;
    private ArrayAdapter<String> grupeAdapter;
    private ArrayAdapter<String> daniAdapter;

    private CasoviViewModel viewModel;

    private int filterGrupa = 0;
    private int filterDan = 0;
    private String filterText = "";

    public static RasporedFragment newInstance() {
        return new RasporedFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_raspored,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getReferences();
        setupUI();
        setupListeners();
    }

    private void getReferences() {
        View v = getView();
        spGrupa = v.findViewById(R.id.sp_raspored_grupa);
        spDan = v.findViewById(R.id.sp_raspored_dan);
        rvCasovi = v.findViewById(R.id.rv_raspored_casovi);
        etFilter = v.findViewById(R.id.et_raspored_filter);
        btnTrazi = v.findViewById(R.id.btn_raspored_trazi);

        viewModel = ViewModelProviders.of(getActivity()).get(CasoviViewModel.class);
    }

    private void setupUI() {
        rvCasovi.setLayoutManager(new LinearLayoutManager(getActivity()));
        casoviAdapter = new CasoviAdapter();
        rvCasovi.setAdapter(casoviAdapter);

        grupeAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item);
        spGrupa.setAdapter(grupeAdapter);

        daniAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item);
        spDan.setAdapter(daniAdapter);
        daniAdapter.addAll(CasEntity.dani);
    }

    private void setupListeners() {
        viewModel.getCasoviLiveData().observe(this, casovi -> casoviAdapter.setData(casovi));

        CasEntity.grupeLiveData.observe(this, grupe -> {
            grupeAdapter.clear();
            grupeAdapter.addAll(grupe);
        });

        spGrupa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterGrupa = position;
                updateFilter();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spDan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterDan = position;
                updateFilter();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnTrazi.setOnClickListener((v) -> {
            filterText = etFilter.getText().toString().trim();
            updateFilter();
        });
    }

    private void updateFilter() {
        String grupa = (String) spGrupa.getItemAtPosition(filterGrupa);
        String dan = (String) spDan.getItemAtPosition(filterDan);
        viewModel.setFilter(new CasFilter(grupa,dan,filterText,""));
    }
}
