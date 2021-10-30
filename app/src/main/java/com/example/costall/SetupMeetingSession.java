package com.example.costall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.costall.helper.Functions;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SetupMeetingSession extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextInputEditText meetingTitle, meetingGoal,date,startTime,endTime, meetingType;
    private boolean mIsCancelling;
    RequestQueue requestQueue;
    TimePickerDialog mTimePickerDialog;
    Calendar mCalendar;
    int mCurrentHour, mCurrentMinute;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MeetingSession" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_meeting);
      Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //set current time
        mCalendar = Calendar.getInstance();
        mCurrentHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mCurrentMinute = mCalendar.get(Calendar.MINUTE);

        meetingTitle= findViewById(R.id.meeting_title_txt);
        meetingGoal = findViewById(R.id.meeting_goal_txt);
        meetingType =findViewById(R.id.meeting_type_txt);

        date = findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
        startTime = findViewById(R.id.start_time_txt);
        endTime = findViewById(R.id.end_time_txt);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimePickerDialog = new TimePickerDialog(SetupMeetingSession.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                startTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
                                //mStart.setText(hourOfDay+" : "+minutes);
                            }
                        }, mCurrentHour, mCurrentMinute, false);
                mTimePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimePickerDialog = new TimePickerDialog(SetupMeetingSession.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                endTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
                                //mStart.setText(hourOfDay+" : "+minutes);
                            }
                        }, mCurrentHour, mCurrentMinute, false);
                mTimePickerDialog.show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create) {
            createSession();
            return true;
        }else if(id==R.id.action_cancel){
            mIsCancelling = true;
            //if user clicks cancel the activity will exit and return back to NoteListActivity
            finish();
        } else if(id==R.id.action_venue){
            Intent mainActivity = new Intent(SetupMeetingSession.this
                    ,Venue.class);
            startActivity(mainActivity);
        } else if(id==R.id.action_agenda){
            Intent agendaActivity = new Intent(SetupMeetingSession.this
                    ,AgendaInit.class);
            startActivity(agendaActivity);
        } else if(id==R.id.action_resource){
            Intent resourceActivity = new Intent(SetupMeetingSession.this
                    ,Resource.class);
            startActivity(resourceActivity);
        } else if(id==R.id.action_participants) {
            Intent participantActivity = new Intent(SetupMeetingSession.this, ViewParticipants.class);
            startActivity(participantActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    private void createSession() {
        if(meetingTitle.length()==0){
            meetingTitle.setError("Meeting Title Required");
            meetingTitle.requestFocus();
            return;
        }else if(meetingGoal.length()==0){
            meetingGoal.setError("Meeting Goal Required");
            meetingGoal.requestFocus();
            return;
        } else if(meetingType.length()==0){
            meetingType.setError("Meeting Type Required");
            meetingType.requestFocus();
            return;
        } else if(date.length()==0){
            date.setError("Meeting Date Required");
            date.requestFocus();
            return;
        } else if(startTime.length()==0){
            meetingType.setError("Start Time Required");
            meetingType.requestFocus();
            return;
        }
        else if(endTime.length()==0){
            endTime.setError("End Time Required");
            endTime.requestFocus();
            return;
        }else {

            SharedPreferences pref = getSharedPreferences("LoginSession", Context.MODE_PRIVATE);

            //text from activity to string
            final String meetingTitleString = meetingTitle.getText().toString().trim();
            final String meetingGoalString = meetingGoal.getText().toString().trim();
            final String meetingOrganiser = pref.getString("userEmail", "");
            final String meetingTypeString = meetingType.getText().toString().trim();
            final String dateString = date.getText().toString().trim();
            final String startTimeString = startTime.getText().toString().trim();
            final String endTimeString = endTime.getText().toString().trim();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("meetingTitle", meetingTitleString);
            editor.putString("meetingGoal", meetingGoalString);
            editor.putString("meetingOrganiser", meetingOrganiser);
            editor.putString("meetingType", meetingTypeString);
            editor.putString("meetingDate", dateString);
            editor.putString("startTime", startTimeString);
            editor.putString("endTime", endTimeString);
            editor.apply();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Functions.URL_CREATE_SESSION,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getString("message").isEmpty()){
                                    Toast.makeText(getApplicationContext(), jsonObject
                                                    .getString("error"),
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), jsonObject
                                                    .getString("message"),
                                            Toast.LENGTH_SHORT).show();

                                    String meetingID = jsonObject.getString("meetingID");
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("meetingID", meetingID);
                                    editor.apply();
                                    //createMeetingEvent();

                                   /* Intent intent = new Intent(SetupMeetingSession.this, ViewParticipants.class);
                                    startActivity(intent);*/
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("meeting_title", meetingTitleString);
                    params.put("meeting_goal", meetingGoalString);
                    params.put("organizer", meetingOrganiser);
                    params.put("date", dateString);
                    params.put("startTime",startTimeString);
                    params.put("endTime", endTimeString);
                    params.put("meeting_type", meetingTypeString);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        date.setText(currentDate);
    }


}