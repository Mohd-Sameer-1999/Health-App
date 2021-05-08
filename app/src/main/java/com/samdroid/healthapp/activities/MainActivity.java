package com.samdroid.healthapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import com.samdroid.healthapp.R;
import com.samdroid.healthapp.databinding.ActivityMainBinding;

import static androidx.navigation.Navigation.findNavController;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       navController = findNavController(MainActivity.this, R.id.fragment_container_view);
        NavigationUI.setupWithNavController(binding.bottomNavigationMenu, navController);
        NavigationUI.setupActionBarWithNavController(MainActivity.this, navController);
        binding.bottomNavigationMenu.setItemIconTintList(null);

    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }
}