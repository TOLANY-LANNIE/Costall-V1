package com.example.costall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.costall.helper.Functions;
import com.example.costall.models.Meeting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinMeeting extends AppCompatActivity {
    String meetingName, meetingDat, meetingType, meetStart, meetEnd;
    RequestQueue requestQueue;
    MeetingListAdapter adapter;
    ListView meetingsView;
    List<Meeting> meetingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_session_list);

        meetingsView = findViewById(R.id.meetingListView);

        requestQueue = Volley.newRequestQueue(this);

        init();
        meetingList = new ArrayList<>();
        adapter = new MeetingListAdapter(this, R.layout.activity_join_meeting, meetingList);
        meetingsView.setAdapter(adapter);
    }

    public void init() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Functions.URL_GET_MEETING_SESSION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(MeetingSessionList.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("rows");
                    for (int i = 0; i<array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        meetingName = jsonObject.getString("Meeting_Title");
                        meetingType = jsonObject.getString("Meeting_Type");
                        meetingDat = jsonObject.getString("Date");
                        meetStart = jsonObject.getString("StartTime");
                        meetEnd = jsonObject.getString("EndTime");
                        meetingList.add(new Meeting(meetingName, meetingType, meetingDat, meetStart, meetEnd));
                        //  Toast.makeText(MeetingSessionList.this, meetingList.get(i).getMeetingTitle(), Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("error", error.toString());
                // dialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> params = new HashMap<String, String>();
                SharedPreferences newPref = getSharedPreferences("LoginSession", Context.MODE_PRIVATE);
                String id = newPref.getString("userRegId", "");
                params.put("regID", id);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    private class MeetingListAdapter extends ArrayAdapter<Meeting> {
        private final Activity context;
        int resource;
        List<Meeting> meetingList;

        public MeetingListAdapter(Activity context, int resource, List<Meeting> meetingList) {
            super(context, resource, meetingList);
            this.context = context;
            this.resource = resource;
            this.meetingList = meetingList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(resource,null,false);
            if (convertView == null) {
                TextView meetingTitle = view.findViewById(R.id.meetingTitleTextView);
                TextView meetingType = view.findViewById(R.id.meetingTypeTextView);
                TextView meetingDate = view.findViewById(R.id.meetingDateTextView);
                Button startmeeting = view.findViewById(R.id.startMeetingButton);

                startmeeting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(JoinMeeting.this, MeetingTimer.class);
                    //    intent.putExtra("meetingTitle", )
                        startActivity(intent);
                    }
                });
                Meeting meeting = meetingList.get(position);

                String mTitle = meeting.getMeetingTitle();
                SharedPreferences preferences = getSharedPreferences("meetings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("meetingName", mTitle);
                editor.apply();

                String mType = meeting.getMeetingType();
                String mDate = meeting.getMeetingDate();

                meetingTitle.setText(mTitle);
                meetingType.setText(mType);
                meetingDate.setText(mDate);
            }
            return view;
        }
    }
}