package com.example.costall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.costall.models.Meeting;

public class GetMeetingDetails extends AppCompatActivity {

    Meeting meeting;
    TextView meetingName, meetingDate, meetingStart, meetingEnd, meetingType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_details);
        meeting = (Meeting)getIntent().getSerializableExtra("meeting");

        meetingName = findViewById(R.id.detail_MeetingTitle);
        meetingDate = findViewById(R.id.detail_MeetingDate);
        meetingStart = findViewById(R.id.detail_StartTime);
        meetingEnd = findViewById(R.id.detail_EndTime);
        meetingType = findViewById(R.id.detail_MeetingType);

        meetingName.setText(meeting.getMeetingTitle());
        meetingDate.setText(meeting.getMeetingDate());
        meetingType.setText(meeting.getMeetingType());
        meetingEnd.setText(meeting.getMeetingEnd());
        meetingStart.setText(meeting.getMeetingStart());


    }
}