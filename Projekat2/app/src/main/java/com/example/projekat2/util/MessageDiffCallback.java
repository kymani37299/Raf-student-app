package com.example.projekat2.util;

import com.example.projekat2.model.Message;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class MessageDiffCallback extends DiffUtil.Callback {

    private List<Message> oldList;
    private List<Message> newList;

    public MessageDiffCallback(List<Message> oldList,List<Message> newList) {
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
        Message oldMsg = oldList.get(oldItemPosition);
        Message newMsg = newList.get(newItemPosition);
        return oldMsg.getId().equals(newMsg.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Message oldMsg = oldList.get(oldItemPosition);
        Message newMsg = newList.get(newItemPosition);
        return oldMsg.getContent().equals(newMsg.getContent()) && oldMsg.getTime().equals(newMsg.getTime()) && oldMsg.getType() == newMsg.getType();
    }
}
