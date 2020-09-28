package com.example.receipeasy.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.receipeasy.R;
import com.example.receipeasy.databinding.RecyclerViewRecipeBinding;
import com.example.receipeasy.model.Recipe;

import java.util.ArrayList;

import javax.security.auth.PrivateCredentialPermission;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private final OnRecipeClickListener recipeClickListener;

    private ArrayList<Recipe> recipes;

    public RecipesAdapter(ArrayList<Recipe> userRecipes, OnRecipeClickListener recipeClickListener) {
        this.recipes = userRecipes;
        this.recipeClickListener = recipeClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewRecipeBinding binding = RecyclerViewRecipeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(ArrayList<Recipe> userRecipes) {
        this.recipes= userRecipes;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private RecyclerViewRecipeBinding binding;
        public RecipeViewHolder(@NonNull RecyclerViewRecipeBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(final Recipe recipe) {
            if (recipe.getCategory() == "breakfast"){
                binding.imageCategory.setImageResource(R.drawable.img_breakfast);
            }
            else if (recipe.getCategory() == "dinner"){
                binding.imageCategory.setImageResource(R.drawable.img_dinner);
            }
            else{
                binding.imageCategory.setImageResource(R.drawable.img_brunch);
            }
            binding.textName.setText(recipe.getName());
            binding.textDescription.setText(recipe.getDescription());
            binding.textDuration.setText(String.format("%02dh%02d", recipe.getDurationHours(), recipe.getDurationMinutes()));

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recipeClickListener.onRecipeClicked(recipe);
                }
            });
        }
    }
}
