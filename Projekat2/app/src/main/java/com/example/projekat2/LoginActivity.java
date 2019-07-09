package com.example.projekat2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekat2.model.User;
import com.example.projekat2.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText etIndex;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvNoAccount;

    private TextInputLayout tilIndex;
    private TextInputLayout tilPassword;

    private UserViewModel viewModel;

    private enum LoginState {
        NO_ACTION,NO_ACCOUNT,INVALID_INDEX,INVALID_PASSWORD
    }


    private LoginState state = LoginState.NO_ACTION;
    private boolean loginPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        getReferences();
        setupActions();
    }

    private void getReferences() {
        etIndex = findViewById(R.id.et_login_index);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login_action);
        tvNoAccount = findViewById(R.id.tv_login_no_account);
        tilIndex = findViewById(R.id.til_login_index);
        tilPassword = findViewById(R.id.til_login_password);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        viewModel.getCurrentUser().observe(this, user -> {
            if(validateUser(user)) {
                Intent i = new Intent(this,MainActivity.class);
                i.putExtra("index",user.getIndex());
                finish();
                startActivity(i);
            }
            if(loginPressed && state!=LoginState.NO_ACTION) {
                showError();
            }
            state = LoginState.NO_ACTION;
            loginPressed = false;
        });
    }

    private boolean validateUser(User user) {
        if(user==null) {
            state = loginPressed ? LoginState.NO_ACCOUNT : LoginState.NO_ACTION;
            return false;
        }
        String index = etIndex.getText().toString().toLowerCase();
        Pattern pattern = Pattern.compile(User.indexRegEx);
        if(!pattern.matcher(index).find()) {
            state = LoginState.INVALID_INDEX;
            return false;
        }

        String password = etPassword.getText().toString();

        if(user.getIndex().equals(index) && user.getPassword().equals(password)) {
            return true;
        }
        state = LoginState.INVALID_PASSWORD;
        return false;
    }

    private void showError() {
        tilIndex.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
        if(state==LoginState.INVALID_INDEX) {
            tilIndex.setError("Invalid index format");
            tilIndex.setErrorEnabled(true);
        } else if(state==LoginState.INVALID_PASSWORD) {
            tilPassword.setError("Invalid password");
            tilPassword.setErrorEnabled(true);
        } else if(state==LoginState.NO_ACCOUNT) {
            tilIndex.setError("Account doesn't exist");
            tilIndex.setErrorEnabled(true);
        }
    }

    private void setupActions() {
        btnLogin.setOnClickListener(v -> {
            String index = etIndex.getText().toString().toLowerCase();

            Pattern pattern = Pattern.compile(User.indexRegEx);
            if(!pattern.matcher(index).find()) {
                state = LoginState.INVALID_INDEX;
                showError();
                return;
            }
            loginPressed = true;
            viewModel.login(index);
        });

        tvNoAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        });
    }
}
