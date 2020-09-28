package com.example.receipeasy.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

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

public class RecipesActivity extends AppCompatActivity implements OnRecipeClickListener {
    private ActivityRecipesBinding binding;
    private final static int ADD_RECIPE_REQUEST = 1;
    private final static int EDIT_RECIPE_REQUEST = 2;

    public final static String KEY_EDIT_RECIPE_EXTRA = "ca.jphaneuf.edit-recipe-extra";
    private RecipesAdapter recipesAdapter;

    private ArrayList<Recipe> getUserRecipes() {
        return RecipesService.getInstance().getAll(getCurrentUserId());
    }

    private int getCurrentUserId() {
        return UserService.getInstance().getCurrentUser().getId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerRecipes.setHasFixedSize(true);
        binding.recyclerRecipes.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.recyclerRecipes.setLayoutManager(new LinearLayoutManager(this));

        recipesAdapter = new RecipesAdapter(getUserRecipes(), this);


        binding.recyclerRecipes.setAdapter(recipesAdapter);

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRecipe = new Intent(RecipesActivity.this, RecipeActivity.class);
                startActivityForResult(addRecipe, ADD_RECIPE_REQUEST);
                startActivity(addRecipe);
            }
        });
/*
        ;*/

        binding.buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.getInstance().logOut();

                Intent logOutIntent = new Intent(RecipesActivity.this, LoginActivity.class);
                startActivity(logOutIntent);
                finish();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK & (requestCode == ADD_RECIPE_REQUEST || requestCode == EDIT_RECIPE_REQUEST)){
            recipesAdapter.setRecipes(getUserRecipes());
            recipesAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent editRecipeIntent = new Intent(RecipesActivity.this, RecipeActivity.class);
        editRecipeIntent.putExtra(KEY_EDIT_RECIPE_EXTRA, recipe);
        startActivityForResult(editRecipeIntent, EDIT_RECIPE_REQUEST);

    }
}