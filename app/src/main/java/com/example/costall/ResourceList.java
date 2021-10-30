package com.example.costall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.costall.adapter.ResourceAdapter;
import com.example.costall.helper.Functions;
import com.example.costall.models.ResourceObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceList extends AppCompatActivity {
    private JsonObjectRequest mRequest;
    private RequestQueue mRequestQueue;
    private List<ResourceObject> mList;
    private RecyclerView mRecyclerView;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_list);
        Toolbar toolbar = findViewById(R.id.resource_list_toolbar);
        setSupportActionBar(toolbar);

        mList = new ArrayList<>();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResourceList.this, Resource.class);
                startActivity(intent);
            }
        });
        mRequestQueue = Volley.newRequestQueue(this);
        mRecyclerView = findViewById(R.id.resourceListView);

        loadResources();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void loadResources() {
        Map<String, String> params = new HashMap();
        params.put("sessionID", "MS00000001");
        final JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Functions.URL_GET_RESOURCE_LIST, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("resources");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject =  jsonArray.getJSONObject(i);

                                String sessionID=jsonObject.optString("Session_ID");;
                                String resourceName=jsonObject.optString("Resource_Name");;
                                String purpose =jsonObject.optString("Purpose");;
                                String cost=jsonObject.optString("Cost");;

                                ResourceObject resourceObject = new ResourceObject();
                                resourceObject.setResourceName(jsonObject.optString("Resource_Name"));
                                resourceObject.setPurpose(jsonObject.optString("Purpose"));
                                resourceObject.setCost(jsonObject.optString("Cost"));
                                mList.add(resourceObject);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setupRecyclerview(mList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(jsonObjectRequest);

    }

    private void setupRecyclerview(List<ResourceObject> list) {
        ResourceAdapter resourceAdapter = new ResourceAdapter(this,list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(resourceAdapter);
    }


}