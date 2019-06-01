package com.example.projekat2.repository;

import com.example.projekat2.livedata.MessageCollectionLiveData;
import com.example.projekat2.model.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageRepository {

    private DatabaseReference messageReference;
    private DatabaseReference wallReference;

    public MessageRepository() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        messageReference = db.getReference().child("messages");
        wallReference = db.getReference().child("wall");
    }

    public void addWallMessage(Message message) {
        wallReference.push().setValue(message);
    }

    public MessageCollectionLiveData getWallMessages() {
        return new MessageCollectionLiveData();
    }

    public void addMessage(Message message) {
        String key = message.getId();
        messageReference.child(key).push().setValue(message);
    }

    public MessageCollectionLiveData getMessagesLiveData(String userIndex1,String userIndex2) {
        return new MessageCollectionLiveData(userIndex1,userIndex2);
    }
}
