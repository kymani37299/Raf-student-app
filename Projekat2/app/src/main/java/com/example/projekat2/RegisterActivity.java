package com.example.projekat2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekat2.model.User;
import com.example.projekat2.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private enum RegisterState {
        NO_INDEX,NO_PASSWORD,NO_NAME,
        INDEX_EXSISTS,INVALID_INDEX,INVALID_PASSWORD,
        INVALID_NAME,NO_ACTION;
    }

    private Button btnRegister;

    private EditText etIndex;
    private EditText etPassword;
    private EditText etName;

    private TextInputLayout tilIndex;
    private TextInputLayout tilPassword;
    private TextInputLayout tilName;

    private UserViewModel viewModel;

    private RegisterState state = RegisterState.NO_ACTION;
    private boolean registerClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        getReferences();
        setupActions();
    }

    private void getReferences() {
        btnRegister = findViewById(R.id.btn_register_action);
        etIndex = findViewById(R.id.et_register_index);
        etPassword = findViewById(R.id.et_register_password);
        etName = findViewById(R.id.et_register_name);
        tilIndex = findViewById(R.id.til_register_index);
        tilName = findViewById(R.id.til_register_name);
        tilPassword = findViewById(R.id.til_register_password);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void showError() {
        tilName.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
        tilIndex.setErrorEnabled(false);

        if(state==RegisterState.NO_INDEX) {
            tilIndex.setError("This field is required!");
            tilIndex.setErrorEnabled(true);
        }else if(state==RegisterState.NO_PASSWORD) {
            tilPassword.setError("This field is required!");
            tilPassword.setErrorEnabled(true);
        }else if(state==RegisterState.NO_NAME) {
            tilName.setError("This field is required!");
            tilName.setErrorEnabled(true);
        }else if(state==RegisterState.INVALID_INDEX) {
            tilIndex.setError("Index should be in format like rn7-16");
            tilIndex.setErrorEnabled(true);
        }else if(state==RegisterState.INVALID_PASSWORD) {
            tilPassword.setError("Password too short!");
            tilPassword.setErrorEnabled(true);
        }else if(state==RegisterState.INVALID_NAME) {
            tilName.setError("Invalid name!");
            tilName.setErrorEnabled(true);
        }else if(state==RegisterState.INDEX_EXSISTS) {
            tilIndex.setError("Account with this index exists,please login!");
            tilIndex.setErrorEnabled(true);
        }
    }

    private boolean validateFields() {
        String index = etIndex.getText().toString();
        String password = etPassword.getText().toString();
        String name = etName.getText().toString();

        if(index.isEmpty()) {
            state = RegisterState.NO_INDEX;
            return false;
        }

        if(password.isEmpty()) {
            state = RegisterState.NO_PASSWORD;
            return false;
        }

        if(name.isEmpty()) {
            state = RegisterState.NO_NAME;
            return false;
        }

        Pattern pattern = Pattern.compile(User.indexRegEx);
        if(!pattern.matcher(index).find()) {
            state = RegisterState.INVALID_INDEX;
            return false;
        }

        if(password.length()<6) {
            state = RegisterState.INVALID_PASSWORD;
            return false;
        }

        return true;
    }

    private void makeUser() {
        if(validateFields()) {
            String index = etIndex.getText().toString().toLowerCase();
            String password = etPassword.getText().toString();
            String name = etName.getText().toString();
            User user = new User(index,password,name);
            viewModel.createUser(user);
            Toast.makeText(this,"Account created! Please log in!",Toast.LENGTH_SHORT);
            finish();
        } else {
            showError();
        }
    }

    private void setupActions() {
        btnRegister.setOnClickListener(v -> {
            String index = etIndex.getText().toString();
            registerClicked = true;
            viewModel.login(index);
        });

        viewModel.getCurrentUser().observe(this,user -> {
            if(user!=null) {
                state = RegisterState.INDEX_EXSISTS;
                showError();
            } else {
                if(registerClicked) makeUser();
            }
            state = RegisterState.NO_ACTION;
            registerClicked = false;
        });
    }
}
