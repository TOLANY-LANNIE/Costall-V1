package com.example.costall.fragments.meeting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.costall.R;

public class MeetingFragment extends Fragment {

    private MeetingViewModel  meetingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_meeting, container, false);
        return root;
    }
}