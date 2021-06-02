package com.samdroid.healthapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samdroid.healthapp.activities.CreateReminderActivity;
import com.samdroid.healthapp.adapters.ReminderAdapter;
import com.samdroid.healthapp.databinding.FragmentReminderBinding;
import com.samdroid.healthapp.models.Reminder;

import java.util.ArrayList;
import java.util.Objects;

public class ReminderFragment extends Fragment {

    public ReminderFragment() {
        // Required empty public constructor
    }

    FragmentReminderBinding binding;
    ReminderAdapter reminderAdapter;
    ArrayList<Reminder> reminders;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReminderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.createReminder.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CreateReminderActivity.class);
            requireActivity().startActivity(intent);
        });

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("Reminder");

        reminders = new ArrayList<>();
        reminderAdapter = new ReminderAdapter(getContext(), reminders);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        showReminders();
        binding.recyclerView.setAdapter(reminderAdapter);

    }

    private void showReminders(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Reminder reminder = snapshot1.getValue(Reminder.class);
                        reminders.clear();
                        reminders.add(reminder);
                        reminderAdapter.notifyDataSetChanged();
                        Log.i("Reminder", String.valueOf(reminders.size()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}