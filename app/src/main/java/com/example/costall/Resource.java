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

import java.util.HashMap;
import java.util.Map;

public class Resource extends AppCompatActivity {
    private TextInputEditText resourceName, usage,cost;
    private boolean mIsCancelling;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        Toolbar toolbar = findViewById(R.id.resource_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        resourceName = findViewById(R.id.resourceName_txt);
        usage = findViewById(R.id.usage_text);
        cost =findViewById(R.id.resource_cost_text);

        requestQueue = Volley.newRequestQueue(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.resource_menu, menu);
        return true;
    }

    private void addResource() {
        if(resourceName.length()==0){
            resourceName.setError("Resource Name Required");
            resourceName.requestFocus();
            return;
        }else if(usage.length()==0){
            usage.setError("Usage Required");
            usage.requestFocus();
            return;
        } else if(cost.length()==0){
            cost.setError("Resource Cost Required");
            cost.requestFocus();
            return;
        } else {
            String resource = resourceName.getText().toString().trim();
            String usageString = usage.getText().toString().trim();
            String costString = cost.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Functions.URL_ADD_RESOURCE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Resource.this, "Resource successfully added", Toast.LENGTH_SHORT);
                    Intent resourceAct = new Intent (Resource.this, ResourceList.class);
                    startActivity(resourceAct);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                Toast.makeText(Resource.this, "Resource addition unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    SharedPreferences pref = getSharedPreferences("MeetingSession", Context.MODE_PRIVATE);

                  Map<String, String> params = new HashMap<>();
                  params.put("session", pref.getString("meetingID", ""));
                  params.put("resourceName", resource);
                  params.put("purpose", usageString);
                  params.put("cost", costString);
                  return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
           addResource();
            return true;
        }else if(id==R.id.action_cancel){
            mIsCancelling = true;
            //if user clicks cancel the activity will exit and return back to NoteListActivity
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}