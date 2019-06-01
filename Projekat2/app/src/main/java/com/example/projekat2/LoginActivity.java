package com.example.projekat2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekat2.model.User;
import com.example.projekat2.viewmodel.UserViewModel;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText etIndex;
    private EditText etName;
    private Button btnLogin;

    private UserViewModel viewModel;

    private boolean loginPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        getReferences();

        btnLogin.setOnClickListener((v) -> {
            String index = etIndex.getText().toString();
            String name= etName.getText().toString();

            boolean validIndex = Pattern.compile(User.indexRegEx).matcher(index).matches();

            if(!validIndex) {
                showErrorToast();
                return;
            }

            index = index.toLowerCase();

            User user = new User(index,name);
            viewModel.login(user);

            loginPressed = true;
        });
    }

    private void getReferences() {
        etIndex = findViewById(R.id.et_login_index);
        etName = findViewById(R.id.et_login_name);
        btnLogin = findViewById(R.id.btn_login_action);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        viewModel.getCurrentUser().observe(this, user -> {
            if(user!=null) {
                Intent i = new Intent(this,MainActivity.class);
                i.putExtra("index",user.getIndex());
                finish();
                startActivity(i);
            } else if(loginPressed){
                showErrorToast();
            }
            loginPressed = false;
        });
    }

    private void showErrorToast() {
        Toast.makeText(this,"Greska prilokom logovanja",Toast.LENGTH_LONG).show();
    }
}
