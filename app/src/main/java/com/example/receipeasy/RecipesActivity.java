package com.example.receipeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.receipeasy.databinding.ActivityRecipesBinding;
import com.example.receipeasy.databinding.ActivitySignupBinding;
import com.example.receipeasy.model.Recipe;

public class RecipesActivity extends AppCompatActivity {
    private ActivityRecipesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRecipe = new Intent(RecipesActivity.this, RecipeActivity.class);
                startActivity(addRecipe);
            }
        });
    }
}