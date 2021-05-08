package com.samdroid.healthapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Switch;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samdroid.healthapp.R;
import com.samdroid.healthapp.databinding.ActivityBpReadingBinding;
import com.samdroid.healthapp.fragments.DatePickerFragment;
import com.samdroid.healthapp.fragments.TimePickerFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.zip.Inflater;

public class BpReadingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ActivityBpReadingBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBpReadingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Blood pressure");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        binding.dateEt.setText(simpleDateFormat.format(calendar.getTime()));

        SimpleDateFormat simpleTimeFormat =  new SimpleDateFormat("HH:mm aaa",Locale.ENGLISH);
        binding.timeEt.setText(simpleTimeFormat.format(calendar.getTime()));

        binding.dateEt.setOnClickListener(v->{
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });
        binding.timeEt.setOnClickListener(v->{
            DialogFragment timePicker =  new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        reference = database.getReference("Users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("Blood_pressure").push();
        if (item.getItemId() == R.id.save) {
            reference.child("upperBound").setValue(Objects.requireNonNull(binding.upperBoundEt.getText()).toString());
            reference.child("lowerBound").setValue(Objects.requireNonNull(binding.lowerBoundEt.getText()).toString());
            reference.child("comment").setValue(Objects.requireNonNull(binding.commentEt.getText()).toString());
            reference.child("time").setValue(Objects.requireNonNull(binding.timeEt.getText()).toString());
            reference.child("date").setValue(Objects.requireNonNull(binding.dateEt.getText()).toString());
            Intent intent = new Intent(BpReadingActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        String currentDate = simpleDateFormat.format(c.getTime());
        binding.dateEt.setText(currentDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat simpleTimeFormat =  new SimpleDateFormat("HH:mm aaa",Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        binding.timeEt.setText(simpleTimeFormat.format(c.getTime()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(BpReadingActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        return super.onSupportNavigateUp();
    }
}