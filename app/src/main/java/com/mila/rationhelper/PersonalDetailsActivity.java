package com.mila.rationhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mila.rationhelper.Helpers.Constants;

public class PersonalDetailsActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private Spinner genredSpinner, activitySpinner;
    private EditText heightET, ageET, weightET;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        findReferences();
    }

    private void findReferences(){
        genredSpinner = findViewById(R.id.genderSpinner);
        heightET = findViewById(R.id.heightET);
        ageET = findViewById(R.id.ageET);
        activitySpinner = findViewById(R.id.activitySpinner);
        weightET = findViewById(R.id.weightET);

        populateViews();
    }

    private void populateViews(){
        ArrayAdapter genderAdapter = new ArrayAdapter(this,R.layout.spinner_selected_item, Constants.GENDERS);
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_list_item);
        genredSpinner.setAdapter(genderAdapter);
        int genderSelection = sharedPreferences.getInt(Constants.PREFERENCES_GENDER, Constants.DEFAULT_GENDER);
        genredSpinner.setSelection(genderSelection);

        ArrayAdapter activityAdapter = new ArrayAdapter(this,R.layout.spinner_selected_item, Constants.PHYSICAL_ACTIVITIES);
        activityAdapter.setDropDownViewResource(R.layout.spinner_dropdown_list_item);
        activitySpinner.setAdapter(activityAdapter);
        int activitySelection = sharedPreferences.getInt(Constants.PREFERENCES_ACTIVITY, Constants.DEFAULT_ACTIVITY_LEVEL);
        activitySpinner.setSelection(activitySelection);

        int height = sharedPreferences.getInt(Constants.PREFERENCES_HEIGHT, Constants.DEFAULT_HEIGHT);
        if (height != Constants.DEFAULT_HEIGHT)
            heightET.setText(String.valueOf(height));

        int age = sharedPreferences.getInt(Constants.PREFERENCES_AGE, Constants.DEFAULT_AGE);
        if (age != Constants.DEFAULT_AGE)
            ageET.setText(String.valueOf(age));

        int weight = sharedPreferences.getInt(Constants.PREFERENCES_WEIGHT, Constants.DEFAULT_WEIGHT);
        if (weight != Constants.DEFAULT_WEIGHT)
            weightET.setText(String.valueOf(weight));
    }

    public void saveButtonListener(View view){
        String gender, height, age, activity, weight;

        gender = genredSpinner.getSelectedItem().toString();
        height = heightET.getText().toString();
        age = ageET.getText().toString();
        activity = activitySpinner.getSelectedItem().toString();
        weight = weightET.getText().toString();

        Log.d(TAG, "Saving...");
        Log.d(TAG, "\tGender: \t"+gender);
        Log.d(TAG, "\tHeight: \t"+height);
        Log.d(TAG, "\tAge: \t\t"+age);
        Log.d(TAG, "\tActivity: \t"+activity);
        Log.d(TAG, "\tWeight: \t"+weight);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.PREFERENCES_GENDER, genredSpinner.getSelectedItemPosition());
        editor.putInt(Constants.PREFERENCES_ACTIVITY, activitySpinner.getSelectedItemPosition());
        editor.putInt(Constants.PREFERENCES_HEIGHT, Integer.parseInt(heightET.getText().toString()));
        editor.putInt(Constants.PREFERENCES_AGE, Integer.parseInt(ageET.getText().toString()));
        editor.putInt(Constants.PREFERENCES_WEIGHT, Integer.parseInt(weightET.getText().toString()));
        boolean result = editor.commit();
        Log.d(TAG, "Changes committed to shared preferences: "+result);

        backToMenu();
    }

    private void backToMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}