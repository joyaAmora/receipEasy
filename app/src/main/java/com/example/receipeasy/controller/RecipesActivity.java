package com.example.receipeasy.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.receipeasy.databinding.ActivityRecipesBinding;
import com.example.receipeasy.model.Recipe;
import com.example.receipeasy.model.RecipesService;
import com.example.receipeasy.model.User;
import com.example.receipeasy.model.UserService;

import java.util.ArrayList;
import java.util.Random;

public class RecipesActivity extends AppCompatActivity {
    private ActivityRecipesBinding binding;
    private final static int ADD_RECIPE_REQUEST = 1;
    private final static int EDIT_RECIPE_REQUEST = 2;

    public final static String KEY_EDIT_RECIPE_EXTRA = "ca.jphaneuf.edit-recipe-extra";

    private int getCurrentUserId() {
        return UserService.getInstance().getCurrentUser().getId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRecipe = new Intent(RecipesActivity.this, RecipeActivity.class);
                startActivityForResult(addRecipe, ADD_RECIPE_REQUEST);
                startActivity(addRecipe);
            }
        });

        binding.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Recipe> recipes = RecipesService.getInstance().getAll(getCurrentUserId());
                int recipesCount = recipes.size();
                if (recipesCount> 0) {
                    Random random = new Random();
                    Recipe recipe = recipes.get(random.nextInt(recipesCount));

                    Intent editRecipeIntent = new Intent(RecipesActivity.this, RecipeActivity.class);
                    editRecipeIntent.putExtra(KEY_EDIT_RECIPE_EXTRA, recipe);
                    startActivityForResult(editRecipeIntent, EDIT_RECIPE_REQUEST);
                }
                else {
                    Toast.makeText(RecipesActivity.this, "No recipe!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.getInstance().logOut();

                Intent logOutIntent = new Intent(RecipesActivity.this, LoginActivity.class);
                startActivity(logOutIntent);
                finish();
            }
        });

        refreshRecipes();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK & (requestCode == ADD_RECIPE_REQUEST || requestCode == EDIT_RECIPE_REQUEST)){
            refreshRecipes();
        }
    }

    private void refreshRecipes(){
        User currentUser = UserService.getInstance().getCurrentUser();
        binding.textRecipes.setText(RecipesService.getInstance().getAll(getCurrentUserId()).toString());
    }
}