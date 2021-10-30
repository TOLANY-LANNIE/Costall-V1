package com.example.costall.fragments.meeting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeetingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MeetingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the meeting fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}