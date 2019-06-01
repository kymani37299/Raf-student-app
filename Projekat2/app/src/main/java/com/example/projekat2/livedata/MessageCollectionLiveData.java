package com.example.projekat2.livedata;

import com.example.projekat2.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class MessageCollectionLiveData extends LiveData<List<Message>> {

    private DatabaseReference reference;
    private ValueEventListener listener;

    public MessageCollectionLiveData() {
        reference = FirebaseDatabase.getInstance().getReference().child("wall");
        listener = getMessageValueEventListener();
    }

    public MessageCollectionLiveData(String userIndex1,String userIndex2) {
        String key = Message.getMessageKey(userIndex1,userIndex2);
        reference = FirebaseDatabase.getInstance().getReference().child("messages").child(key);
        listener = getMessageValueEventListener();
    }

    private ValueEventListener getMessageValueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Message> messages = new ArrayList<>();

                if(dataSnapshot.getValue()==null) {
                    return;
                }

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Message msg = ds.getValue(Message.class);
                    if(msg!=null) {
                        msg.setId(ds.getKey());
                    }
                    messages.add(msg);
                }

                setValue(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    @Override
    protected void onActive() {
        super.onActive();
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        reference.removeEventListener(listener);
    }

}
