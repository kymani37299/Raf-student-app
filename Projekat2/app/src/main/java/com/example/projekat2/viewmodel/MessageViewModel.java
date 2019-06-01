package com.example.projekat2.viewmodel;

import com.example.projekat2.livedata.MessageCollectionLiveData;
import com.example.projekat2.model.Message;
import com.example.projekat2.repository.MessageRepository;

import androidx.lifecycle.ViewModel;

public class MessageViewModel extends ViewModel {

    private MessageRepository repo;

    public MessageViewModel() {
        repo = new MessageRepository();
    }

    public void addMessage(Message message) {
        repo.addMessage(message);
    }

    public void addWallMessage(Message message) {
        repo.addWallMessage(message);
    }

    public MessageCollectionLiveData getMessagesLiveData(String userIndex1,String userIndex2) {
        return repo.getMessagesLiveData(userIndex1,userIndex2);
    }

    public MessageCollectionLiveData getWallMessagesLiveData() {
        return repo.getWallMessages();
    }
}
