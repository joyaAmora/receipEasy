package com.example.receipeasy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.receipeasy.api.UserRequestListener;
import com.example.receipeasy.databinding.ActivitySignupBinding;
import com.example.receipeasy.model.User;
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

                    UserService.getInstance().signUp(username, password, new UserRequestListener() {
                        @Override
                        public void onResponse(boolean success) {
                            if (success){
                                Intent signedUp = new Intent(SignUpActivity.this, RecipesActivity.class);
                                startActivity(signedUp);

                                finishAffinity();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Invalid infos...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, SignUpActivity.this);
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

    }
}