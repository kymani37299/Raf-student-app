package com.example.projekat2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.projekat2.model.Message;
import com.example.projekat2.model.MessageType;
import com.example.projekat2.view.adapter.MessageAdapter;
import com.example.projekat2.viewmodel.MessageViewModel;
import com.example.projekat2.viewmodel.UserViewModel;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvMessages;
    private EditText etText;
    private ImageView ivSend;

    private MessageViewModel messageViewModel;
    private UserViewModel userViewModel;

    private MessageAdapter adapter;
    private LinearLayoutManager layoutManager;

    private String thisUserIndex;
    private String otherUserIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getReferences();
        setupUI();
        setupListeners();
    }

    private void getReferences() {
        rvMessages = findViewById(R.id.rv_chat_messages);
        etText = findViewById(R.id.et_chat_text);
        ivSend = findViewById(R.id.iv_chat_send);

        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Intent i = getIntent();
        thisUserIndex = i.getStringExtra("this");
        otherUserIndex = i.getStringExtra("other");

        adapter = new MessageAdapter(thisUserIndex);
        layoutManager = new LinearLayoutManager(this);
    }

    private void setupUI() {
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setAdapter(adapter);

    }

    private void setupListeners() {
        ivSend.setOnClickListener((v) -> {
            String text = etText.getText().toString();
            etText.setText("");
            Message msg = new Message(thisUserIndex,otherUserIndex, MessageType.TEXT,text);
            messageViewModel.addMessage(msg);
            hideKeyboard();

        });

        messageViewModel.getMessagesLiveData(thisUserIndex,otherUserIndex).observe(this,(messages) -> {
            adapter.setData(messages);
            layoutManager.scrollToPosition(adapter.getItemCount()-1);

        });

        userViewModel.getUser(otherUserIndex).observe(this,(user)-> setTitle(user.getName()));
    }

    // Copy-paste from stack overflow
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
