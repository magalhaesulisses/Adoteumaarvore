package com.example.adoteumaarvore.activity.ui.nativespecies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NativespeciesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NativespeciesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is nativespecies fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}