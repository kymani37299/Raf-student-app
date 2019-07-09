package com.example.projekat2.viewmodel;

import android.app.Application;

import com.example.projekat2.livedata.UserCollectionLiveData;
import com.example.projekat2.livedata.UserLiveData;
import com.example.projekat2.model.User;
import com.example.projekat2.repository.UserRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repo;


    public UserViewModel(Application application) {
        super(application);
        repo = new UserRepository(application);
    }

    public void login(String index) {
        repo.login(index);
    }

    public UserLiveData getUser(String index) {
        return repo.getUser(index);
    }

    public UserLiveData getCurrentUser() {
        return repo.getCurrentUser();
    }

    public UserCollectionLiveData getAllUsers() {
        return repo.getAllUsers();
    }

    public void createUser(User user) {
        repo.createUser(user);
    }
}
