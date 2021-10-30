package com.example.costall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.costall.helper.Functions;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

    private Button btnLogin, btnLinkToRegister, btnForgotPass;
    private TextInputLayout inputUsername, inputPassword;

    String registrationID, userID, userFirstName, userSurname, userEmail;

    SharedPreferences preferences;
    public static final String MyPREFERENCES = "LoginSession";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputUsername = findViewById(R.id.edit_email);
        inputPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.button_continue3);
        btnLinkToRegister = findViewById(R.id.button_register);
        btnForgotPass = findViewById(R.id.button_reset);
  /*      Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        init();
    }

    private void init() {
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide Keyboard
                Functions.hideSoftKeyboard(LoginActivity.this);

                String username = Objects.requireNonNull(inputUsername.getEditText()).getText().toString().trim();
                String password = Objects.requireNonNull(inputPassword.getEditText()).getText().toString().trim();

                // Check for empty data in the form
                if (!username.isEmpty() && !password.isEmpty()) {
                        // login user
                        LoginActivity.this.loginProcess(username, password);

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Please enter the credentials!", Toast.LENGTH_LONG).show();
                    inputUsername.setError("Please fill in");
                    inputPassword.setError("Please fill in");
                }
            }
        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivityPersonal.class);
                LoginActivity.this.startActivity(i);
            }
        });

        // Forgot Password Dialog
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                LoginActivity.this.forgotPasswordDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void forgotPasswordDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.reset_password, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("Forgot Password")
                .setCancelable(false)
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        final TextInputLayout mEditEmail = dialogView.findViewById(R.id.edit_email);

        Objects.requireNonNull(mEditEmail.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mEditEmail.getEditText().getText().length() > 0){
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setEnabled(false);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = mEditEmail.getEditText().getText().toString();

                        if (!email.isEmpty()) {
                            if (Functions.isValidEmailAddress(email)) {
                                LoginActivity.this.resetPassword(email);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(LoginActivity.this.getApplicationContext(), "Email is not valid!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this.getApplicationContext(), "Fill all values!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        alertDialog.show();
    }

    private void loginProcess(final String username, final String password) {


        StringRequest strReq = new StringRequest(Request.Method.POST,
                Functions.LOGIN_URL, response -> {
            Log.d(TAG, "Login Response: " + response);
            Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
            try {
                JSONObject object = new JSONObject(response);
                JSONArray array = object.getJSONArray("info");
                for (int i = 0; i<array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    registrationID = jsonObject.getString("registration_id");
                    userID = jsonObject.getString("User_ID");
                    userEmail = jsonObject.getString("Email");
                    userFirstName = jsonObject.getString("Name");
                    userSurname = jsonObject.getString("Surname");

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userFirstName", userFirstName);
                    editor.putString("userSurname", userSurname);
                    editor.putString("userEmail", userEmail);
                    editor.putString("userRegId", registrationID);
                    editor.putString("userID", userID);
                    editor.apply();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent (LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(LoginActivity.this.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                // hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };

        // Adding request to request queue
        requestQueue.add(strReq);

        //MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void resetPassword(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_reset_pass";

        //showDialog("Please wait...");

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Functions.RESET_PASS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Reset Password Response: " + response);
                //hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);

                    Toast.makeText(LoginActivity.this.getApplicationContext(), jObj.getString("message"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Reset Password Error: " + error.getMessage());
                Toast.makeText(LoginActivity.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("tag", "forgot_pass");
                params.put("email", email);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        // Adding request to volley request queue
        strReq.setRetryPolicy(new DefaultRetryPolicy(5 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
        //MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}