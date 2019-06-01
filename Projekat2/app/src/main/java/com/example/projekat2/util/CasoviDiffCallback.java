package com.example.projekat2.util;

import com.example.projekat2.repository.room.CasEntity;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class CasoviDiffCallback extends DiffUtil.Callback {

    private List<CasEntity> oldList;
    private List<CasEntity> newList;

    public CasoviDiffCallback(List<CasEntity> oldList, List<CasEntity> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CasEntity oldItem = oldList.get(oldItemPosition);
        CasEntity newItem = newList.get(newItemPosition);

        if(!oldItem.getTip().equals(newItem.getTip())) {
            return false;
        }
        if(!oldItem.getDan().equals(newItem.getDan())) {
            return false;
        }

        if(!oldItem.getNastavnik().equals(newItem.getNastavnik())) {
            return false;
        }

        if(!oldItem.getTermin().equals(newItem.getTermin())) {
            return false;
        }

        if(!oldItem.getUcionica().equals(newItem.getUcionica())) {
            return false;
        }

        if(!oldItem.getPredmet().equals(newItem.getPredmet())) {
            return false;
        }
        if(!oldItem.getGrupe().equals(newItem.getGrupe())) {
            return false;
        }

        return true;
    }
}
