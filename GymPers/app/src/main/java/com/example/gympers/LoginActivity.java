package com.example.gympers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 201 ;
    private static final String TAG = "LoginActivity";
    
    
    TextView registration;

    EditText email, password;
    Button signIn;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        auth = FirebaseAuth.getInstance();

        registration = (TextView) findViewById(R.id.registration);

        email = findViewById(R.id.login);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.second_button);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });



        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent firstIntent= new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(firstIntent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();

            }
        });

    }

    private void loginUser() {

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Введите вашу почту!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Введите пароль!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 6) {
            Toast.makeText(this, "Пароль должен содержать более 6 символов", Toast.LENGTH_SHORT).show();
            return;

        }

        //Вход
        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                            Intent firstIntent= new Intent(LoginActivity.this, CategoryActivity.class);
                            startActivity(firstIntent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Ошибка"+task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.e(TAG, "handleSignInResult: "+account.getEmail());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getMessage());

        }

    }

}