package com.example.adoteumaarvore.activity.ui.newplant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewPlantViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NewPlantViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is newplant fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}