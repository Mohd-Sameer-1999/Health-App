package com.samdroid.healthapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samdroid.healthapp.R;
import com.samdroid.healthapp.databinding.ItemReminderBinding;
import com.samdroid.healthapp.models.Reminder;

import java.util.ArrayList;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    Context context;
    ArrayList<Reminder> reminderArrayList;

    public ReminderAdapter(Context context, ArrayList<Reminder> reminderArrayList) {
        this.context = context;
        this.reminderArrayList = reminderArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder reminder = reminderArrayList.get(position);
        holder.binding.labelTv.setText(reminder.getLabel());
        holder.binding.reminderTv.setText(reminder.getReminder());
        holder.binding.timeTc.setText(reminder.getTime());
    }

    @Override
    public int getItemCount() {
        return reminderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemReminderBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemReminderBinding.bind(itemView);
        }
    }
}
