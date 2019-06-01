package com.example.projekat2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private void clearSharedPrefs() {
        SharedPreferences.Editor editor = this.getSharedPreferences(getPackageName(),Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        clearSharedPrefs();

        SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        Intent intent;
        String index = sharedPreferences.getString("index",null);
        if(index==null) {
            intent = new Intent(this,LoginActivity.class);
        } else {
            intent = new Intent(this,MainActivity.class);
            intent.putExtra("index",index);
        }
        startActivity(intent);
        finish();
    }
}
