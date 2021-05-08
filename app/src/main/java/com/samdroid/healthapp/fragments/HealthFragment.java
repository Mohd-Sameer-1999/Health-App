package com.samdroid.healthapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samdroid.healthapp.activities.ChooseMeasurementActivity;
import com.samdroid.healthapp.adapters.BloodSugarReadingsAdapter;
import com.samdroid.healthapp.adapters.BpReadingsAdapter;
import com.samdroid.healthapp.databinding.FragmentHealthBinding;
import com.samdroid.healthapp.models.BloodSugarReadings;
import com.samdroid.healthapp.models.BpReadings;

import java.util.ArrayList;
import java.util.Objects;

public class HealthFragment extends Fragment {

    public HealthFragment() {
        // Required empty public constructor
    }

    FragmentHealthBinding binding;
    ArrayList<BpReadings> bpReadings;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference reference1;
    BpReadingsAdapter adapter;
    BloodSugarReadingsAdapter bloodSugarReadingsAdapter;
    ArrayList<BloodSugarReadings> bloodSugarReadings;
    ConcatAdapter concatAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHealthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addMeasurement.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), ChooseMeasurementActivity.class);
            getActivity().startActivity(intent);
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Blood_pressure");
        reference1 = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Blood_sugar");

        bpReadings = new ArrayList<>();
        bloodSugarReadings = new ArrayList<>();
        adapter = new BpReadingsAdapter(getContext(), bpReadings);
        bloodSugarReadingsAdapter = new BloodSugarReadingsAdapter(getContext(), bloodSugarReadings);
        concatAdapter = new ConcatAdapter(adapter, bloodSugarReadingsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        getData();
        binding.recyclerView.setAdapter(concatAdapter);

    }

    private void getData(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        BpReadings readings = snapshot1.getValue(BpReadings.class);
                        bpReadings.add(readings);
                        adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        BloodSugarReadings bloodSugarReadings1 = snapshot1.getValue(BloodSugarReadings.class);
                        bloodSugarReadings.add(bloodSugarReadings1);
                        bloodSugarReadingsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}