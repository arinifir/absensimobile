package com.example.sipjti.ui.jadwal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JadwalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JadwalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is jadwal fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}