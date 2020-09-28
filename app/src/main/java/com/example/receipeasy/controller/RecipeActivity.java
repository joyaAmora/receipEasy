package com.example.receipeasy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.receipeasy.databinding.ActivityRecipeBinding;
import com.example.receipeasy.model.Recipe;
import com.example.receipeasy.model.RecipesService;
import com.example.receipeasy.model.UserService;

public class RecipeActivity extends AppCompatActivity {
    private ActivityRecipeBinding binding;
    private int recipeId;

    private int getUserId(){
        return UserService.getInstance().getCurrentUser().getId();
    }

    private String getCategory() {
        String category = null;
        int categoryID = binding.radioGroupCategory.getCheckedRadioButtonId();
        if (categoryID  != -1) {
            category = (String)findViewById(categoryID).getTag();
        }
        return category;
    }

    public String getName() {
        return binding.editTextPersonName.getText().toString();
    }

    public int getDurationHours() {
        return binding.timePickerRecipe.getHour();
    }

    public int getDurationMinutes() {
        return binding.timePickerRecipe.getMinute();
    }

    public String getDescription() {
        return binding.editTextTextMultiLine.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.timePickerRecipe.setIs24HourView(true);
        binding.timePickerRecipe.setHour(0);
        binding.timePickerRecipe.setMinute(0);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Recipe recipe = extras.getParcelable(RecipesActivity.KEY_EDIT_RECIPE_EXTRA);

            recipeId = recipe.getId();

            ((RadioButton)binding.radioGroupCategory.findViewWithTag(recipe.getCategory())).setChecked(true);
            binding.editTextPersonName.setText(recipe.getName());
            binding.timePickerRecipe.setHour(recipe.getDurationHours());
            binding.timePickerRecipe.setMinute(recipe.getDurationMinutes());
            binding.editTextTextMultiLine.setText(recipe.getDescription());

            binding.buttonSave.setOnClickListener(editClickListener);
            binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecipesService.getInstance().delete(recipeId, getUserId());
                }
            });
        }
        else {
            binding.buttonDelete.setVisibility(View.GONE);
            binding.buttonSave.setText("Add");
            binding.buttonSave.setOnClickListener(addClickListener);
        }
    }

    private View.OnClickListener addClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(RecipesService.getInstance().add(getCategory(), getName(), getDurationHours(), getDurationMinutes(), getDescription(), getUserId())) {
                finishOk();
            }
            else {
                Toast.makeText(RecipeActivity.this, "Invalid infos...", Toast.LENGTH_LONG).show();
            }
        }
    };

    private void finishOk() {
        setResult(RESULT_OK);
        finish();
    }

    private View.OnClickListener editClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(RecipesService.getInstance().edit(recipeId,getCategory(), getName(), getDurationHours(), getDurationMinutes(), getDescription(), getUserId())) {
                finishOk();
            }
            else {
                Toast.makeText(RecipeActivity.this, "Invalid infos...", Toast.LENGTH_LONG).show();
            }
        }
    };
}