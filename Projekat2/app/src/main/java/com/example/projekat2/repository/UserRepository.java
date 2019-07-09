package com.example.projekat2.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.projekat2.livedata.UserCollectionLiveData;
import com.example.projekat2.livedata.UserLiveData;
import com.example.projekat2.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository {

    private DatabaseReference usersReference;

    private UserCollectionLiveData usersLiveData;
    private UserLiveData currentUserLiveData;

    private SharedPreferences sharedPreferences;

    public UserRepository(Application application) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        usersReference = db.getReference().child("users");
        usersLiveData = new UserCollectionLiveData();
        currentUserLiveData = new UserLiveData();
        sharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);
    }

    public UserCollectionLiveData getAllUsers() {
        return usersLiveData;
    }

    public UserLiveData getUser(String index) {
        return new UserLiveData(index);
    }

    public UserLiveData getCurrentUser() {
        return currentUserLiveData;
    }

    public void createUser(User user) {
        usersReference.child(user.getIndex()).setValue(user);
    }

    public void login(String index) {
        if(index==null) {
            return;
        }
//        UserLiveData u = new UserLiveData(index);

        currentUserLiveData.setIndex(index);

//        if(u.getValue() == null || u.getValue().getName().equals(name)) {
//            usersReference.child(index).setValue(new User(index,name));
//            currentUserLiveData.setIndex(index);
////            SharedPreferences.Editor editor = sharedPreferences.edit();
////            editor.putString("index",index);
////            editor.commit();
//        }
    }

}
