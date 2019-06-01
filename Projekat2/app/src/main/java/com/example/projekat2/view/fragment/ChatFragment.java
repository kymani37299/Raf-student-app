package com.example.projekat2.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekat2.R;
import com.example.projekat2.view.adapter.UserAdapter;
import com.example.projekat2.viewmodel.UserViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends Fragment {

    private RecyclerView rvUsers;
    private UserAdapter adapter;

    private UserViewModel viewModel;


    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvUsers = getView().findViewById(R.id.rv_chat_users);
        rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UserAdapter();
        rvUsers.setAdapter(adapter);

        Intent i = getActivity().getIntent();
        String index = i.getStringExtra("index");

        viewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        viewModel.getUser(index).observe(this, user -> adapter.setCurrentUser(user));
        viewModel.getAllUsers().observe(this, users -> adapter.setData(users));
    }

}
