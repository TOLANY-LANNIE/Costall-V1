package com.example.costall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.costall.models.User;

public class RegisterActivityCompany extends AppCompatActivity {

    Button button_continue3;
    Button goToLogin;
    User user;

    TextView textInputCompany;
    TextView textInputDepartment;
    TextView textInputPosition;
    TextView textInputSalary;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "UserRegistration" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        user = (User) getIntent().getSerializableExtra("user");
       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/
        button_continue3 = (Button)findViewById(R.id.button_continue3);
        goToLogin= (Button)findViewById(R.id.button_go_to_login);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        textInputCompany = (TextView) findViewById(R.id.textInputCompany);
        textInputDepartment = (TextView) findViewById(R.id.textInputDepartment);
        textInputPosition = (TextView) findViewById(R.id.textInputPosition);
        textInputSalary = (TextView) findViewById(R.id.textInputSalary);
        button_continue3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {

                String company = textInputCompany.getText().toString();
                String department = textInputDepartment.getText().toString();
                String position = textInputPosition.getText().toString();


                String salaryText = textInputSalary.getText().toString();
                {

                }
                if (company.isEmpty() || department.isEmpty() || position.isEmpty() || salaryText.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please provide valid data", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("organization", company);
                    editor.putString("department", department);
                    editor.putString("position", position);
                    editor.putString("salary", salaryText);
                    editor.apply();
                    Intent intent = new Intent(RegisterActivityCompany.this, FinalRegisterActivity.class);
                    startActivity(intent);
                }

            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Intent i = new Intent(RegisterActivityCompany.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}