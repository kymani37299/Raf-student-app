package com.example.projekat2.view.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.projekat2.CasDetailsActivity;
import com.example.projekat2.R;
import com.example.projekat2.repository.room.CasEntity;
import com.example.projekat2.model.CasFilter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CasViewHolder extends RecyclerView.ViewHolder {

    private TextView tvPredmet;
    private TextView tvProfesor;
    private TextView tvUcionica;
    private TextView tvGrupe;
    private TextView tvDan;
    private TextView tvVreme;

    public CasViewHolder(@NonNull View v) {
        super(v);
        tvPredmet = v.findViewById(R.id.tv_cas_predmet);
        tvProfesor = v.findViewById(R.id.tv_cas_profesor);
        tvUcionica = v.findViewById(R.id.tv_cas_ucionica);
        tvGrupe = v.findViewById(R.id.tv_cas_grupe);
        tvDan = v.findViewById(R.id.tv_cas_dan);
        tvVreme = v.findViewById(R.id.tv_cas_vreme);
    }

    public void bind(CasEntity cas) {
        tvPredmet.setText(cas.getPredmet() + " - " + cas.getTip().charAt(0));
        tvProfesor.setText(cas.getNastavnik());
        tvUcionica.setText(cas.getUcionica());
        tvGrupe.setText(cas.getGrupe());
        tvDan.setText(cas.getDan());
        tvVreme.setText(cas.getTermin());

        tvPredmet.setOnClickListener(getOnClick(cas.getPredmet(),""));
        tvProfesor.setOnClickListener(getOnClick(cas.getNastavnik(),""));
        tvUcionica.setOnClickListener(getOnClick("",cas.getUcionica()));
    }

    private View.OnClickListener getOnClick(String textFilter,String ucionica) {
        return v -> {
            Intent i = new Intent(v.getContext(), CasDetailsActivity.class);
            i.putExtra("filter",new CasFilter("","",textFilter,ucionica));
            v.getContext().startActivity(i);
        };
    }


}
