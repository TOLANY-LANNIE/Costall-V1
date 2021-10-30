package com.example.costall.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.costall.JoinMeeting;
import com.example.costall.R;
import com.example.costall.SetupMeetingSession;
public class HomeFragment extends Fragment implements  View.OnClickListener {
    CardView join, schedule;
    private HomeViewModel mHomeViewModel;
    private View mView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mHomeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        mView= inflater.inflate(R.layout.fragment_home, container, false);

        //initializing the cards
        join = (CardView) mView.findViewById(R.id.join_meeting);
        schedule = (CardView) mView.findViewById(R.id.schedule_meeting);


        //add click listeners to the cards
        join.setOnClickListener(this);
        schedule.setOnClickListener(this);




        return mView;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            //case R.id.join_meeting: intent = new Intent(getActivity(), SetupMeeting.class); startActivity(intent); break;
            case R.id.schedule_meeting: intent = new Intent(getActivity(), SetupMeetingSession.class);
            startActivity(intent); break;
            case R.id.join_meeting: intent = new Intent(getActivity(), JoinMeeting.class);
            startActivity(intent); break;
            //case R.id.meetings_history: intent = new Intent(getActivity(), MeetingSetup.class); break;
            //case R.id.to_do_list: intent = new Intent(getActivity(), MeetingSetup.class); break;
            default:break;

        }

    }
}