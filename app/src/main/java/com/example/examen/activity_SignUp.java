package com.example.examen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class activity_SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText username, userEmail, userPassword, vuserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.txtCreateUsername);
        userEmail = findViewById(R.id.txtCreateUserEmail);
        userPassword = findViewById(R.id.txtCreateUserPassword);
        vuserPassword = findViewById(R.id.txtConfirmUserPassword);

        findViewById(R.id.btnCreateUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario(view);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void registrarUsuario(View view){
        String userName = username.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        String confirmPassword = vuserPassword.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException invalidUserException) {
                        Toast.makeText(getApplicationContext(), "Error: El usuario ya existe", Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthInvalidCredentialsException invalidCredentialsException) {
                        Toast.makeText(getApplicationContext(), "Error: Credenciales no válidas", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        String errorMessage = e.getMessage();
                        Toast.makeText(getApplicationContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}