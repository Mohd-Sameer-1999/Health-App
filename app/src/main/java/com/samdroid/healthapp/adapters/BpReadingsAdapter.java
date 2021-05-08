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
import com.samdroid.healthapp.databinding.ItemBpReadingBinding;
import com.samdroid.healthapp.models.BpReadings;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BpReadingsAdapter extends RecyclerView.Adapter<BpReadingsAdapter.BpReadingViewHolder>{

    Context context;
    ArrayList<BpReadings> bpReadings;

    public BpReadingsAdapter(Context context, ArrayList<BpReadings> bpReadings) {
        this.context = context;
        this.bpReadings = bpReadings;
    }

    @NonNull
    @Override
    public BpReadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bp_reading, parent, false);
        return new BpReadingViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull BpReadingViewHolder holder, int position){
        BpReadings bpReadings1 = bpReadings.get(position);
        String date = bpReadings1.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        try {
            Date date1 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            Log.i("Day of the week ", String.valueOf(day));
            String[] dayOfTheWeek = new String[]{"Sunday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday"};
            holder.binding.dayTextView.setText(dayOfTheWeek[day -1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.binding.dateTextView.setText(date);
        holder.binding.timeTextView.setText(bpReadings1.getTime());
        holder.binding.readings.setText(bpReadings1.getUpperBound() + "/" + bpReadings1.getLowerBound() + " mmHg");
    }

    @Override
    public int getItemCount() {
        return bpReadings.size();
    }

    public class BpReadingViewHolder extends RecyclerView.ViewHolder {
        ItemBpReadingBinding binding;

        public BpReadingViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemBpReadingBinding.bind(itemView);
        }
    }


}
