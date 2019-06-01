package com.example.projekat2.livedata;

import com.example.projekat2.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class UserCollectionLiveData extends LiveData<List<User>> {

    private DatabaseReference reference;
    private ValueEventListener listener;

    public UserCollectionLiveData() {
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();

                if(dataSnapshot.getValue()==null) {
                    return;
                }

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    user.setIndex(ds.getKey());
                    users.add(user);
                }
                setValue(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
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
