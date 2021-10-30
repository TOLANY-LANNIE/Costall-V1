package com.example.costall.fragments.action_items;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActionItemsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ActionItemsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is action items fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}