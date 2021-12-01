package com.example.gympers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();


        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(intent);
        finish();

        if (auth.getCurrentUser() !=null){
            startActivity(new Intent(SplashScreen.this, CategoryActivity.class));
            finish();
        }

    }
}