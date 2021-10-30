package com.example.costall;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.costall.helper.Functions;

import java.util.HashMap;
import java.util.Map;

public class NewParticipant extends AppCompatActivity {
    EditText participantName, participantSurname, travelCosts, reserveCosts;
    Spinner roleSpinner;
    RadioGroup radioGroup;
    RadioButton isAttending;
    Button cancelButton, saveButton;
    RequestQueue requestQueue;


    private static final String CREATE_PARTICIPANT_URL = "http://192.168.137.1/Cost/public/addParticipants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_participant);

        final String[] partRole = new String[1];
        final Intent intent = getIntent();
        requestQueue = Volley.newRequestQueue(this);
        String[] participantRoles = { "Chairperson", "Secretary", "Consultant", "Participant", "Timekeeper"};
        SharedPreferences pref = getSharedPreferences("MeetingSession", Context.MODE_PRIVATE);

        participantName = findViewById(R.id.editTextFirstName);
        participantSurname = findViewById(R.id.editTextLastName);
        roleSpinner = findViewById(R.id.dropDown);
        radioGroup = findViewById(R.id.radioGroup2);

        travelCosts = findViewById(R.id.editTextTravelCosts2);
        reserveCosts = findViewById(R.id.editTextReservationCosts);
        cancelButton = findViewById(R.id.cancelAdd);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
        saveButton = findViewById(R.id.addParticipantBtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, participantRoles);
        roleSpinner.setAdapter(adapter);
        roleSpinner.setSelection(0);

        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                partRole[0] = parent.getItemAtPosition(position).toString();
                if (!partRole[0].equals("Consultant")) {
                    travelCosts.setVisibility(View.GONE);
                    reserveCosts.setVisibility(View.GONE);
                } else {
                    travelCosts.setVisibility(View.VISIBLE);
                    reserveCosts.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String partName = participantName.getText().toString();
                final String partSurname = participantSurname.getText().toString();
                final String participateRole = partRole[0];
                int selectedId = radioGroup.getCheckedRadioButtonId();
                isAttending = findViewById(selectedId);

                String response = isAttending.getText().toString();
                final String attendingStatus;
                if ("No".equals(response)) {
                    attendingStatus = "0";
                } else {
                    attendingStatus = "1";
                }
                String travelCost = travelCosts.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Functions.URL_CREATE_PARTICIPANT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Participant Successfully Added", Toast.LENGTH_SHORT).show();
                        //dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Participant Does Not Exist!", Toast.LENGTH_SHORT).show();
                        Log.e("error", error.toString());
                       // dialog.dismiss();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("firstName", partName);
                        params.put("lastName", partSurname);
                        params.put("roleName", participateRole);
                        params.put("sessionName", pref.getString("meetingTitle", ""));
                        params.put("attending_meeting", attendingStatus);
                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);
           //     dialog.dismiss();

                Intent i = new Intent(NewParticipant.this, ViewParticipants.class);
                startActivityForResult(i, 1);
            }
        });

    }
}