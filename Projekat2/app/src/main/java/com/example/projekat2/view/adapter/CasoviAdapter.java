package com.example.projekat2.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekat2.R;
import com.example.projekat2.repository.room.CasEntity;
import com.example.projekat2.util.CasoviDiffCallback;
import com.example.projekat2.view.viewholder.CasViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CasoviAdapter extends RecyclerView.Adapter<CasViewHolder> {

    private List<CasEntity> data;

    public CasoviAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<CasEntity> newData) {
        CasoviDiffCallback diffCallback = new CasoviDiffCallback(data,newData);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        data.clear();
        data.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public CasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_cas,parent,false);
        return new CasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CasViewHolder holder, int position) {
        CasEntity cas = data.get(position);
        holder.bind(cas);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
