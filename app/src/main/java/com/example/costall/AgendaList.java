package com.example.costall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.costall.adapter.AgendaAdapter;
import com.example.costall.helper.Functions;
import com.example.costall.models.AgendaItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendaList extends AppCompatActivity {

    private JsonObjectRequest mRequest;
    private RequestQueue mRequestQueue;
    private List<AgendaItem> mAgendaItems;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_list);
        Toolbar toolbar = findViewById(R.id.agenda_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAgendaItems = new ArrayList<>();

        FloatingActionButton fab = findViewById(R.id.fab_agenda);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgendaList.this, AgendaInit.class);
                startActivity(intent);
            }
        });
        mRequestQueue = Volley.newRequestQueue(this);
        mRecyclerView = findViewById(R.id.agendaInitListView);
        loadAgenda();


    }

    private void setupRecyclerview(List<AgendaItem> agendaItems) {
        AgendaAdapter agendaAdapter = new AgendaAdapter(this, mAgendaItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(agendaAdapter);
    }

    private void loadAgenda() {
        Map<String, String> params = new HashMap();
        SharedPreferences pref = getSharedPreferences("MeetingSession", Context.MODE_PRIVATE);
        params.put("sessionID", "MS00000001");
        final JSONObject parameters = new JSONObject(params);

        mRequest = new JsonObjectRequest(Request.Method.POST,
                Functions.URL_GET_AGENDA_LIST, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("agendaItems");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                AgendaItem item = new AgendaItem();

                                item.setAgendaID(jsonObject.optString("Agenda_Item_ID"));
                                item.setAgendaDescription(jsonObject.optString("Agenda_Description"));
                                item.setParticipantID(jsonObject.optString("Participation_ID"));
                                item.setAllocatedTime(jsonObject.optString("Delivery_duration"));
                                item.setDiscussion(jsonObject.optString("Discussion"));
                                item.setConclusion(jsonObject.optString("Conclusion"));
                                item.setName(jsonObject.optString("Name"));
                                item.setSurname(jsonObject.optString("Surname"));
                                Log.i("agenda", item.toString());
                                mAgendaItems.add(item);
                            }
                            setupRecyclerview(mAgendaItems);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setupRecyclerview(mAgendaItems);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(mRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}