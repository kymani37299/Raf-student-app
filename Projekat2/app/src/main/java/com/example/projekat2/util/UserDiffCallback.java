package com.example.projekat2.util;

import com.example.projekat2.model.User;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class UserDiffCallback extends DiffUtil.Callback {

    private List<User> oldList;
    private List<User> newList;

    public UserDiffCallback(List<User> oldList,List<User> newList) {
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
        User oldUser = oldList.get(oldItemPosition);
        User newUser = newList.get(newItemPosition);
        return oldUser.getIndex().equals(newUser.getIndex());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        User oldUser = oldList.get(oldItemPosition);
        User newUser = newList.get(newItemPosition);
        return oldUser.getName().equals(newUser.getName());
    }
}
