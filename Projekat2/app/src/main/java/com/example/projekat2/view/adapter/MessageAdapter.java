package com.example.projekat2.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekat2.R;
import com.example.projekat2.model.Message;
import com.example.projekat2.util.MessageDiffCallback;
import com.example.projekat2.view.viewholder.MessageAbstractViewHolder;
import com.example.projekat2.view.viewholder.MessageRecievedViewHolder;
import com.example.projekat2.view.viewholder.MessageSentViewHolder;
import com.example.projekat2.view.viewholder.WallMsgViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAbstractViewHolder> {


    private static final int SENT_VIEW_TYPE = 0;
    private static final int RECIEVED_VIEW_TYPE = 1;
    private static final int WALL_VIEW_TYPE = 2;


    private String thisIndex;
    private List<Message> data;

    private HashMap<String,String> indexNameMap;

    public MessageAdapter() {
        data = new ArrayList<>();
        indexNameMap = new HashMap<>();
        this.thisIndex = null;
    }

    public MessageAdapter(String thisIndex) {
        this.thisIndex = thisIndex;
        indexNameMap = new HashMap<>();
        data = new ArrayList<>();
    }

    public void setData(List<Message> newData) {
        MessageDiffCallback diffCallback = new MessageDiffCallback(data,newData);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
        data.clear();
        data.addAll(newData);
        result.dispatchUpdatesTo(this);
    }

    public void setUsers(HashMap<String,String> users) {
        indexNameMap = users;
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = data.get(position);
        if(thisIndex==null) {
            return WALL_VIEW_TYPE;
        }else if(msg.getSender().equals(thisIndex)) {
            return SENT_VIEW_TYPE;
        } else {
            return RECIEVED_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public MessageAbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENT_VIEW_TYPE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_message_sent,parent,false);
            return new MessageSentViewHolder(v);
        }else if(viewType==RECIEVED_VIEW_TYPE){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_message_recieved,parent,false);
            return new MessageRecievedViewHolder(v);
        }else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_wall_message,parent,false);
            return new WallMsgViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAbstractViewHolder holder, int position) {
        holder.bind(data.get(position),indexNameMap);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
