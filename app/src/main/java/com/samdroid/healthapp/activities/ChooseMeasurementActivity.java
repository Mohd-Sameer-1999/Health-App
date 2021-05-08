package com.samdroid.healthapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.samdroid.healthapp.R;
import com.samdroid.healthapp.databinding.ActivityChooseMeasurementBinding;
import com.samdroid.healthapp.models.BloodSugarReadings;

public class ChooseMeasurementActivity extends AppCompatActivity {

    ActivityChooseMeasurementBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityChooseMeasurementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bloodPressure.setOnClickListener(v -> {
            Intent intent =  new Intent(ChooseMeasurementActivity.this, BpReadingActivity.class);
            startActivity(intent);
        });
        binding.bloodSugar.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseMeasurementActivity.this, BloodSugarActivity.class);
            startActivity(intent);
        });
    }
}