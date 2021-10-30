package com.example.costall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.costall.models.Participants;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Agenda extends AppCompatActivity {
    TextInputEditText agendaName, allocatedTime,discussion,conclusion;
    Spinner mSpinner;
    ArrayList<Agenda> mMeetingParticipants = new ArrayList<>();
    ArrayAdapter<Participants> mParticipantAdapter;
    private boolean mIsCancelling;
    RequestQueue requestQueue;
    private String partID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        Toolbar toolbar = findViewById(R.id.agenda_init_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //instantiate volley request
        requestQueue = Volley.newRequestQueue(this);

        agendaName = findViewById(R.id.agenda_init_txt);
        allocatedTime = findViewById(R.id.time_allocated_init_txt);
        mSpinner = findViewById(R.id.participant_init_spinner);
        Map<String, String> params = new HashMap();
        params.put("sessionID", "MS00000001");

    }
}