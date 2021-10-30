package com.example.costall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.costall.helper.Functions;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FinalRegisterActivity extends AppCompatActivity {

    String firstname, surname, dob, emailAddress, profession, salary, cellphone, organisation, department, username, password;
    RequestQueue requestQueue;
    private Button btnRegister, btnLinkToLogin, btnContinue, btnBack;
    private TextInputLayout inputEmail, inputPassword, inputUsername;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "UserRegistration" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);
        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        inputUsername = findViewById(R.id.edit_name);
        inputEmail = findViewById(R.id.edit_email);
        inputPassword = findViewById(R.id.edit_password);
        btnRegister = findViewById(R.id.button_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        firstname = sharedPreferences.getString("name", "");
        surname = sharedPreferences.getString("surname", "");
        dob = sharedPreferences.getString("dob", "");
        emailAddress = Objects.requireNonNull(inputEmail.getEditText()).getText().toString();
        editor.putString("email", emailAddress);
        profession = sharedPreferences.getString("position", "");
        salary = sharedPreferences.getString("salary", "");
        cellphone = sharedPreferences.getString("cellphone", "");
        organisation = sharedPreferences.getString("organization", "");
        department = sharedPreferences.getString("department", "");
        username = Objects.requireNonNull(inputUsername.getEditText()).getText().toString();
        editor.putString("username", username);
        password = Objects.requireNonNull(inputPassword.getEditText()).getText().toString();
            editor.putString("password", password);
            editor.apply();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Functions.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FinalRegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams()  throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("name", firstname);
                params.put("surname", surname);
                params.put("dob", dob);
                if (Functions.isValidEmailAddress(emailAddress)) {
                    params.put("email", emailAddress);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email address!", Toast.LENGTH_SHORT).show();
                }
                params.put("profession", profession);
                params.put("salary", salary);
                params.put("cellnumber", cellphone);
                params.put("organisation", organisation);
                params.put("department", department);
                params.put("username", username);
                params.put("password", password);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
}