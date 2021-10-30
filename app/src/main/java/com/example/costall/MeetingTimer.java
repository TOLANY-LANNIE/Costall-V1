package com.example.costall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.costall.helper.Functions;
import com.example.costall.models.Participants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MeetingTimer extends AppCompatActivity {

    private int seconds, hours, minutes, secs;
    private boolean running, wasRunning;
    private ArrayList<Participants> participants;
    private Participants aParticipant;
    private double participantcost = 0.00;
    private double individualcostpersecond;
    private int totalSeconds;
    private double cumulativecost =0.00;

    DecimalFormat df;


    Handler handler;

    private TextView timeView;
    private TextView costView;
    private Button start, stop, reset, end;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    String currentTime;
    String meetingName;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_timer);


        timeView = findViewById(R.id.timeView);
        costView = findViewById(R.id.realTimeCostTV);

        requestQueue = Volley.newRequestQueue(this);

       // String meetingTitle;
        SharedPreferences preferences = getSharedPreferences("meetings", Context.MODE_PRIVATE);
        meetingName = preferences.getString("meetingName", "");
        meetingName.replaceAll(" ", "%20");

        start = findViewById(R.id.buttonStart);
        reset = findViewById(R.id.resetButton);
        stop = findViewById(R.id.pauseButton);
        end = findViewById(R.id.endMeetingButton);

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCost();
            }
        });


        getParticipants();

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                currentTime =  new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                reset.setEnabled(true);
            }
        });

        participants = new ArrayList<>();
        handler = new Handler();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                hours = 0;
                seconds = 0 ;
                minutes = 0 ;
                //MilliSeconds = 0 ;

                timeView.setText("00:00:00");
                costView.setText("R " + "0.00");
            }
        });


//        String participantID = aParticipant.getId();


        if (savedInstanceState != null) {
            savedInstanceState.getInt("seconds");
            savedInstanceState.getBoolean("running");
            savedInstanceState.getBoolean("wasRunning");
        }


    }

    public void getParticipants(){
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Functions.GET_PARTICIPANTS_URL+meetingName, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("participants");
                    for(int i = 0; i< array.length(); i++)
                    {
                        JSONObject jsonObject = array.getJSONObject(i);
                        String participantFirstName = jsonObject.getString("Name");
                        String participantSurname = jsonObject.getString("Surname");
                        String participantRole = jsonObject.getString("role_Name");
                        String participantEmail = jsonObject.getString("email");
                        String participantSalary = jsonObject.getString("MonthlySalary");
                        String participantID = jsonObject.getString("Participation_ID");

                        aParticipant = new Participants(participantID, participantFirstName, participantSurname, participantRole, participantSalary, participantEmail, " ");

                        participants.add(aParticipant);
                        individualcostpersecond = Double.parseDouble(participantSalary)/(22*8*60*60);
                        participantcost +=  individualcostpersecond;
                    }
                 //   adapter.notifyDataSetChanged();
                }
                catch (Exception w)
                {
                    Toast.makeText(MeetingTimer.this,w.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MeetingTimer.this ,error.toString(),Toast.LENGTH_LONG).show();
                Log.e("error", error.toString());
            }
        }) ;
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);
    }

public void onStart (View view) {
        running = true;
}

public void onStop (View view) {
        running = false;
}

public void onReset (View view) {
        running = false;
        seconds = 0;
}

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning) {
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("seconds" , seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }


    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            hours = seconds /3600;

            seconds = (int) (UpdateTime / 1000);
            totalSeconds = seconds;
            cumulativecost = participantcost*totalSeconds;

            minutes = seconds / 60;

            seconds = seconds % 60;

            //milliSeconds = (int) (UpdateTime % 1000);

            String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);

            df = new DecimalFormat("#####.##");

            timeView.setText(time);
            costView.setText(String.format("R. %s", df.format(cumulativecost)));

            handler.postDelayed(this, 1000);
        }

    };

    public void updateCost() {
        TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);
        for (int i=0; i<participants.size(); i++) {
            double indcost = individualcostpersecond*totalSeconds;
            String finalCost = String.format("%.2f", indcost);
            participants.get(i).setMeetingCost(finalCost);
            Toast.makeText(MeetingTimer.this, String.valueOf(participants.get(i).getMeetingCost()), Toast.LENGTH_SHORT).show();
        }

        StringRequest request = new StringRequest(Request.Method.POST, Functions.URL_UPDATE_PARTICIPANT_COST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MeetingTimer.this, "Participant Cost Updated", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MeetingTimer.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                for (int x = 0; x<participants.size(); x++) {
                    params.put("participationID", participants.get(x).getId());
                    params.put("participationCost", participants.get(x).getMeetingCost());
                }
                return params;
            }
        };

        requestQueue.add(request);

        Intent goHome = new Intent(MeetingTimer.this, MeetingSessionList.class);
        startActivity(goHome);
        finish();

    }
}