package com.example.projekat2.livedata;

import com.example.projekat2.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class UserLiveData extends LiveData<User> {

    private DatabaseReference reference;
    private ValueEventListener listener;

    public UserLiveData() {
        setValue(null);
    }

    public UserLiveData(String index) {
        setIndex(index);
    }

    public void setIndex(String index) {
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(index);

        if(this.hasActiveObservers() && listener!=null){
            reference.removeEventListener(listener);
        }

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user!=null) {
                    user.setIndex(dataSnapshot.getKey());
                }
                setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };

        if(this.hasActiveObservers()) {
            reference.addValueEventListener(listener);
        }

    }

    @Override
    protected void onActive() {
        super.onActive();
        if(reference!=null && listener!=null)
            reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if(reference!=null && listener!=null)
            reference.removeEventListener(listener);
    }
}
