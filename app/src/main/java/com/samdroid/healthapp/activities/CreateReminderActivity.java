package com.samdroid.healthapp.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.samdroid.healthapp.AlertReceiver;
import com.samdroid.healthapp.R;
import com.samdroid.healthapp.adapters.ReminderAdapter;
import com.samdroid.healthapp.databinding.ActivityCreateReminderBinding;
import com.samdroid.healthapp.fragments.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class CreateReminderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ActivityCreateReminderBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    Calendar c1;
    Calendar c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateReminderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Create Reminder");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        binding.dateEt.setText(simpleDateFormat.format(calendar.getTime()));

        SimpleDateFormat simpleTimeFormat =  new SimpleDateFormat("hh:mm a",Locale.getDefault());
        binding.timeEt.setText(simpleTimeFormat.format(calendar.getTime()));

//        binding.dateEt.setOnClickListener(v->{
//            DialogFragment datePicker = new DatePickerFragment();
//            datePicker.show(getSupportFragmentManager(), "date picker");
//        });
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
        reference = database.getReference("Users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("Reminder").push();
        if (item.getItemId() == R.id.save) {
            reference.child("reminder").setValue(Objects.requireNonNull(binding.reminderEt.getText()).toString());
            reference.child("label").setValue(Objects.requireNonNull(binding.labelEt.getText()).toString());
            reference.child("time").setValue(Objects.requireNonNull(binding.timeEt.getText()).toString());
            reference.child("date").setValue(Objects.requireNonNull(binding.dateEt.getText()).toString());

            startAlarm(c1);
            finish();


        }
        return super.onOptionsItemSelected(item);
    }

    private void startAlarm(Calendar c) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, year);
        c2.set(Calendar.MONTH, month);
        c2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        String currentDate = simpleDateFormat.format(c2.getTime());
        binding.dateEt.setText(currentDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SimpleDateFormat simpleTimeFormat =  new SimpleDateFormat("hh:mm a",Locale.getDefault());
        c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c1.set(Calendar.MINUTE, minute);
        binding.timeEt.setText(simpleTimeFormat.format(c1.getTime()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}