package com.example.gympers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gympers.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    EditText email, password;
    Button signUp;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        email = findViewById(R.id.login_reg);
        password = findViewById(R.id.password_reg);

        signUp = findViewById(R.id.reg_btn);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();

            }
        });

    }

    private void createUser() {

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Введите вашу почту!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Введите пароль!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 6){
            Toast.makeText(this, "Пароль должен содержать более 6 символов", Toast.LENGTH_SHORT).show();
            return;
        }

        //Создаем аккаунт
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserModel userModel = new UserModel(userEmail,userPassword);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);
                            Intent firstIntent= new Intent(RegistrationActivity.this, CategoryActivity.class);
                            startActivity(firstIntent);

                            Toast.makeText(RegistrationActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RegistrationActivity.this, "Ошибка"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

}