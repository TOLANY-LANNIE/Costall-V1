package com.example.costall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.HashMap;
import java.util.Map;

public class Venue extends AppCompatActivity {
    private TextInputEditText venue,location,cost;
    private boolean mIsCancelling;
    RequestQueue requestQueue;
    String meetingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);
     Toolbar toolbar = findViewById(R.id.venue_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        venue = findViewById(R.id.venueName_txt);
        location = findViewById(R.id.location_text);
        cost =findViewById(R.id.venue_cost_text);

        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.venue_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            addVenue();
            return true;
        }else if(id==R.id.action_cancel){
            mIsCancelling = true;
            //if user clicks cancel the activity will exit and return back to NoteListActivity
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addVenue() {
        if(venue.length()==0){
            venue.setError("Venue Name Required");
            venue.requestFocus();
            return;
        }else if(location.length()==0){
            location.setError("Location Required");
            location.requestFocus();
            return;
        } else if(cost.length()==0){
            cost.setError("Venue Cost Required");
            cost.requestFocus();
            return;
        }else {

            SharedPreferences pref = getSharedPreferences("MeetingSession", Context.MODE_PRIVATE);
            meetingName = pref.getString("meetingID", "");
          //  meetingName = meetingName.replaceAll(" ", "%20");
            //text from activity to string
            final String venueName = venue.getText().toString().trim();
            final String locationName = location.getText().toString().trim();
            final String costString = cost.getText().toString().trim();
            final String session = meetingName;

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Functions.URL_ADD_VENUE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), jsonObject
                                                .getString("message"),
                                        Toast.LENGTH_SHORT).show();
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
                    params.put("name", venueName);
                    params.put("location", locationName);
                    params.put("cost", costString);
                    params.put("session", session);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}