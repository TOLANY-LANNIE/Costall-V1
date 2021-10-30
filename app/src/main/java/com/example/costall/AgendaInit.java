package com.example.costall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.costall.helper.Functions;
import com.example.costall.models.Participants;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgendaInit extends AppCompatActivity {
    TextInputEditText agendaName, allocatedTime;
    Spinner mSpinner;
    ArrayList<Participants> mMeetingParticipants = new ArrayList<>();
    ArrayAdapter<Participants> mParticipantAdapter;
    private boolean mIsCancelling;
    RequestQueue requestQueue;
    private String partID;
    String sessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_init);
        Toolbar toolbar = findViewById(R.id.agenda_init_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = getSharedPreferences("MeetingSession", Context.MODE_PRIVATE);

        //instantiate volley request
        requestQueue = Volley.newRequestQueue(this);

        agendaName = findViewById(R.id.agenda_init_txt);
        allocatedTime = findViewById(R.id.time_allocated_init_txt);
        mSpinner = findViewById(R.id.participant_init_spinner);
        Map<String, String> params = new HashMap();
        sessionID = pref.getString("meetingID", "");
       // sessionName = sessionName.replaceAll(" ", "%20");
        params.put("session", sessionID);

        final JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Functions.GET_PARTICIPANTS_URL+sessionID, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("participants");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject =  jsonArray.getJSONObject(i);
                                String participantID =jsonObject.getString("Participation_ID");
                                String name=jsonObject.getString("Name");
                                String surname=jsonObject.getString("Surname");
                                String meetingRole=jsonObject.getString("role_Name");

                                Participants p = new Participants(participantID,name,surname,meetingRole);
                                mMeetingParticipants.add(p);
                                mParticipantAdapter = new ArrayAdapter<Participants>(AgendaInit.this
                                        ,android.R.layout.simple_list_item_1,mMeetingParticipants);
                                mParticipantAdapter.setDropDownViewResource(android
                                        .R.layout.simple_dropdown_item_1line
                                );
                                mSpinner.setAdapter(mParticipantAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Participants participant = (Participants) mSpinner.getSelectedItem();
                setParticipant(participant);
                partID = participant.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(getApplicationContext(),"No Participant Selected",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setParticipant(Participants participant) {
        partID =participant.getId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.agenda_init_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            addAgenda();
            return true;
        }else if(id==R.id.action_cancel){
            mIsCancelling = true;
            //if user clicks cancel the activity will exit and return back to AgendaListActivity
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addAgenda() {

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Participants participant = (Participants) mSpinner.getSelectedItem();
                setParticipant(participant);
                partID = participant.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(agendaName.length()==0){
            agendaName.setError("AgendaItem Description Required");
            agendaName.requestFocus();
            return;
        }else if(allocatedTime.length()==0){
            allocatedTime.setError("Allocated Minutes Required");
            allocatedTime.requestFocus();
            return;
        }else{
            final String agenda = agendaName.getText().toString().trim();
            final String time = allocatedTime.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Functions.URL_ADD_AGENDA_INIT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(getApplicationContext(),
                                        "AgendaItem Successfully Saved",
                                        Toast.LENGTH_SHORT).show();
                                Intent resourceActivity = new Intent(AgendaInit.this
                                        ,AgendaList.class);
                                startActivity(resourceActivity);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("session", sessionID);
                    params.put("agendaDescription", agenda);
                    params.put("presenter",partID);
                    params.put("agendaDuration", time);
                    return params;
                }
            };
            requestQueue.add(stringRequest);


        }
    }


}