package com.mila.rationhelper.Database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecordViewModel extends AndroidViewModel {
    private Repository repository;

    public RecordViewModel (Application application) {
        super(application);
        repository = new Repository(application);
    }

    // insert and delete
    public void insertRecord(SugarRecordEntity record) {
        LocalDatabase.databaseWriteExecutor.execute(() -> repository.insertRecord(record));
    }

    public void deleteAllRecords(){
        LocalDatabase.databaseWriteExecutor.execute(() -> repository.deleteAllRecords());
    }


    // getters
    public LiveData<List<SugarRecordEntity>> getAllRecords(){return repository.getAllRecords();}

    public LiveData<List<SugarRecordEntity>> getLastRecords(){return repository.getLastRecords();}

}
