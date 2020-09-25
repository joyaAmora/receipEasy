package com.example.receipeasy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.receipeasy.databinding.ActivitySignupBinding;
import com.example.receipeasy.model.UserService;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password  = binding.editTextPassword.getText().toString();
                String passwordConfirmation = binding.editTextPasswordConfirmation.getText().toString();

                if (password.compareTo(passwordConfirmation) == 0){
                    String username = binding.editTextEmailAddress.getText().toString();

                    if (UserService.getInstance().signUp(username, password)){
                        Intent signedUp = new Intent(SignUpActivity.this, RecipesActivity.class);
                        startActivity(signedUp);

                        finishAffinity();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Invalid infos...", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

    }
}