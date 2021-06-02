package com.samdroid.healthapp.adapters;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.samdroid.healthapp.R;
import com.samdroid.healthapp.databinding.ItemBloodSugarReadingBinding;
import com.samdroid.healthapp.models.BloodSugarReadings;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BloodSugarReadingsAdapter extends RecyclerView.Adapter<BloodSugarReadingsAdapter.BloodSugarReadingsViewHolder> {
    Context context;
    ArrayList<BloodSugarReadings> bloodSugarReadings;

    public BloodSugarReadingsAdapter(Context context, ArrayList<BloodSugarReadings> bloodSugarReadings) {
        this.context = context;
        this.bloodSugarReadings = bloodSugarReadings;
    }

    @NonNull
    @Override
    public BloodSugarReadingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_blood_sugar_reading, parent, false);
        return new BloodSugarReadingsViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull BloodSugarReadingsViewHolder holder, int position) {
        BloodSugarReadings bloodSugarReadings1 = bloodSugarReadings.get(position);
        String date = bloodSugarReadings1.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        try {
            Date date1 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            String[] dayOfTheWeek = new String[]{"Sunday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday"};
            holder.binding.dayTextView.setText(dayOfTheWeek[day -1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.binding.dateTextView.setText(date);
        holder.binding.timeTextView.setText(bloodSugarReadings1.getTime());
        holder.binding.readings.setText(bloodSugarReadings1.getBloodSugarLevel() + " mmol/l");

    }

    @Override
    public int getItemCount() {
        return bloodSugarReadings.size();
    }

    public class BloodSugarReadingsViewHolder extends RecyclerView.ViewHolder {
        ItemBloodSugarReadingBinding binding;
        public BloodSugarReadingsViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemBloodSugarReadingBinding.bind(itemView);
        }
    }
}
