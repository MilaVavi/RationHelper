package com.mila.rationhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mila.rationhelper.Adapters.SugarRecordAdapter;
import com.mila.rationhelper.Database.RecordViewModel;
import com.mila.rationhelper.Database.SugarRecordEntity;
import com.mila.rationhelper.Helpers.Constants;

import java.util.Date;

public class SugarLevelsActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private RecyclerView measurmentsRecycler;
    private EditText newMeasurmentET;
    private RecordViewModel recordViewModel;
    private SugarRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar_levels);

        findReferences();
    }

    private void findReferences(){
        measurmentsRecycler = findViewById(R.id.mesurmentsRecycler);
        newMeasurmentET = findViewById(R.id.newMeasurmentET);

        recordViewModel = new ViewModelProvider(this).get(RecordViewModel.class);

        fillViews();
    }


    private void fillViews(){
        adapter = new SugarRecordAdapter(this);

        measurmentsRecycler.setLayoutManager(new LinearLayoutManager(this));

        measurmentsRecycler.setAdapter(adapter);
        updateViews();
    }

    private void updateViews(){
        // Update the cached copy of the objects in the adapter.
        recordViewModel.getLastRecords().observe(this, adapter::setMeasurments);
        Log.d(TAG,"updating views");
    }

    public void addMeasurmentButtonListener(View view){
        recordViewModel.insertRecord(new SugarRecordEntity(Double.valueOf(String.valueOf(newMeasurmentET.getText())), Constants.DATE_FORMAT.format(new Date(System.currentTimeMillis()))));
        updateViews();
        newMeasurmentET.clearFocus();
        newMeasurmentET.setText(null);
    }
}