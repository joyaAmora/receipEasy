package com.example.receipeasy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.receipeasy.databinding.ActivityRecipeBinding;
import com.example.receipeasy.model.Recipe;
import com.example.receipeasy.model.RecipesService;
import com.example.receipeasy.model.UserService;

public class RecipeActivity extends AppCompatActivity {
    private ActivityRecipeBinding binding;
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
            Log.d("RecipeEasy", "onCreate: " + recipe.toString());
        }
        else {
            binding.buttonDelete.setVisibility(View.GONE);
            binding.buttonSave.setText("Add");
        }

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = null;
                int categoryID =binding.radioGroup.getCheckedRadioButtonId();
                if (categoryID  != -1) {
                    category = (String)findViewById(categoryID).getTag();
                }

                String name = binding.editTextPersonName.getText().toString();

                int durationHour = binding.timePickerRecipe.getHour();
                int durationMin = binding.timePickerRecipe.getMinute();
                String description = binding.editTextTextMultiLine.getText().toString();
                int userId = UserService.getInstance().getCurrentUser().getId();
                if(RecipesService.getInstance().add(category, name, durationHour, durationMin, description, userId)) {
                    setResult(RESULT_OK);
                    finish();
                }
                else {
                    Toast.makeText(RecipeActivity.this, "Invalid infos...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}