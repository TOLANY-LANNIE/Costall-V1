package com.example.costall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.costall.helper.Functions;
import com.example.costall.models.Participants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewParticipants extends AppCompatActivity {
    EditText participantName, participantSurname, travelCosts, reserveCosts;
    Spinner roleSpinner;
    RadioGroup radioGroup;
    RadioButton isAttending;
    Button cancelButton, saveButton;
    FloatingActionButton newParticipantFab;
   // AlertDialog dialog;
    ListView participantsListView;

    ListAdapter adapter;

    List<Participants> participantsArrayList;

    RequestQueue requestQueue;

    private String meetingName;



    TextView saveText, meetingTitle, meetingType, meetingDate, meetingTime;

    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);

        participantsArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        meetingTitle = findViewById(R.id.tvMeetingTitle);
        meetingType = findViewById(R.id.tvMeetingType);
        meetingDate = findViewById(R.id.tvMeetingDate);
        meetingTime = findViewById(R.id.tvMeetingStartTime);

        pref = getSharedPreferences("MeetingSession", Context.MODE_PRIVATE);
        meetingTitle.setText(pref.getString("meetingTitle", ""));
        meetingType.setText(pref.getString("meetingType", ""));
        meetingDate.setText(pref.getString("meetingDate", ""));
        meetingTime.setText(pref.getString("startTime", ""));


            meetingName = pref.getString("meetingTitle", "");
            meetingName = meetingName.replaceAll(" ", "%20");
           // Toast.makeText(ViewParticipants.this, meetingName, Toast.LENGTH_SHORT).show();

        participantsListView = findViewById(R.id.participantsListView);
        newParticipantFab = findViewById(R.id.floatingActionButton);
      //  Intent intent = getIntent();
        adapter = new ListAdapter(this, R.layout.participant_list_item, participantsArrayList);
        participantsListView.setAdapter(adapter);


        newParticipantFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(ViewParticipants.this, NewParticipant.class);
            startActivity(intent);
            }
        });
        getParticipants();

        saveText = findViewById(R.id.saveBtnParticipant);

        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createInviteEmail();
               Intent goHome = new Intent(ViewParticipants.this, MainActivity.class);
               startActivity(goHome);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.participant_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
       if(id==R.id.action_cancel){
            finish();
        } else if (id ==R.id.action_save) {
          createInviteEmail();
       }
        return super.onOptionsItemSelected(item);
    }

    public void getParticipants() {
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

                        participantsArrayList.add(new Participants(participantID, participantFirstName, participantSurname, participantRole, participantSalary, participantEmail, "0"));
                    }
                 adapter.notifyDataSetChanged();
                }
                catch (Exception w)
                {
                    Toast.makeText(ViewParticipants.this,w.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewParticipants.this,error.toString(),Toast.LENGTH_LONG).show();
                Log.e("error", error.toString());
            }
        }) ;
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);
    }

    public void createInviteEmail() {

        SharedPreferences newPref = getSharedPreferences("LoginSession", Context.MODE_PRIVATE);

        String subject = "Meeting Invitation: " + pref.getString("meetingTitle", "");
        String body = "Dear Sir/Madam \n" + System.lineSeparator() +
                "You have been invited to the following meeting:\n" +System.lineSeparator() +
                "Meeting Title: " + pref.getString("meetingTitle", "") + System.lineSeparator() +
                "Meeting Goal: " + pref.getString("meetingGoal", "") + System.lineSeparator() +
                "Meeting Date: " + pref.getString("meetingDate", "") + System.lineSeparator() +
                "Meeting Start Time: " + pref.getString("startTime", "") + System.lineSeparator() +

                "\nSincerely," + System.lineSeparator() +
                newPref.getString("userFirstName", "") + " " + newPref.getString("userSurname", "");

       //String [] emails;
       ArrayList<String> emailList = new ArrayList<>();
       for (int i=0; i<participantsArrayList.size(); i++) {
           emailList.add(participantsArrayList.get(i).getEmail());
       }
        Object[] to =  emailList.toArray();
        Intent email = new Intent(android.content.Intent.ACTION_SENDTO);
        for (int x =0 ; x <to.length; x++) {
            email.putExtra(Intent.EXTRA_EMAIL, emailList.toArray(new String [emailList.size()]));
        }
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, body);
        if (email.resolveActivity(getPackageManager()) != null) {
            startActivity(email);
        } else {
            Toast.makeText(ViewParticipants.this, "You do not have any email application on your device!", Toast.LENGTH_SHORT).show();
        }
    }

    static class ListAdapter extends ArrayAdapter<Participants> {
        private final Activity context;

        int resource;
        List<Participants> participantsArrayList;


        public ListAdapter(Activity context, int resource, List<Participants> participantsArrayList) {
            super(context, resource, participantsArrayList);
            this.context = context;
            this.resource = resource;
            this.participantsArrayList = participantsArrayList;
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(resource,null,false);
            if (convertView ==null) {
               TextView participantName = view.findViewById(R.id.tvParticipantName);
               TextView participantRole = view.findViewById(R.id.tvParticipantRole);
               Participants participant = participantsArrayList.get(position);
               String firstName = participant.getName();
               String lastName = participant.getSurname();
               String role = participant.getRole();
               String fullName = firstName + " " + lastName;
               participantName.setText(fullName);
               participantRole.setText(role);
           }
            return view;

        }
    }
}