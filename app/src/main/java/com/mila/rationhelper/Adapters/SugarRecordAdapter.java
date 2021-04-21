package com.mila.rationhelper.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mila.rationhelper.Database.SugarRecordEntity;
import com.mila.rationhelper.Helpers.Constants;
import com.mila.rationhelper.R;

import java.text.DecimalFormat;
import java.util.List;

public class SugarRecordAdapter extends RecyclerView.Adapter <SugarRecordAdapter.RecordViewHolder>
{
    private String TAG = getClass().getSimpleName();

    static class RecordViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView record, date;
        private ConstraintLayout recordLAyout;

        private RecordViewHolder(View itemView)
        {
            super(itemView);
            record = itemView.findViewById(R.id.sugarLevelAdapterLevel);
            date = itemView.findViewById(R.id.sugarLevelAdapterDate);
            recordLAyout = itemView.findViewById(R.id.recordLayout);
        }
    }


    private final LayoutInflater inflater;

    private List<SugarRecordEntity> records; // Cached copy

    public SugarRecordAdapter (Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SugarRecordAdapter.RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d(TAG,"on create view holder");

        View itemView = inflater.inflate(R.layout.sugar_record_list_item, parent, false);

        return new SugarRecordAdapter.RecordViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(SugarRecordAdapter.RecordViewHolder holder, int position)
    {
        Log.d(TAG,"on bind view holder");

        if (records.size() > 0) {
            DecimalFormat df2 = new DecimalFormat("#.##");
            SugarRecordEntity current = records.get(position);

            holder.record.setText(df2.format(current.getSugarLevel()));
            Log.d(TAG, "Set level: "+current.getSugarLevel());
            holder.date.setText(current.getMeasuredDate());
            Log.d(TAG,"Set date: "+current.getMeasuredDate());

            holder.recordLAyout.setBackgroundColor(Constants.measurmentsChoseBG(current.getSugarLevel()));
        }
    }

    public void setMeasurments(List<SugarRecordEntity> records)
    {
        Log.d(TAG, "Got records!");
        this.records = records;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount()
    {
        Log.d(TAG,"getItemCount()");
        return records == null ? 0 : records.size();
    }
}