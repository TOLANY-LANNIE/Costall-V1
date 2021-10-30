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

public class RegisterActivityPersonal extends AppCompatActivity {

    Button button_continue1;
    User user;
    Button goToLogin;

    TextView textInputName;
    TextView editTextSurname;
    TextView editTextTextDateOfBirth;
    TextView textInputCellNumber;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "UserRegistration" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        button_continue1 = (Button) findViewById(R.id.button_continue1);
        user = new User();
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        goToLogin= (Button)findViewById(R.id.button_go_to_login);

        textInputName = (TextView) findViewById(R.id.textInputName);
        editTextSurname = (TextView) findViewById(R.id.editTextSurname);
        editTextTextDateOfBirth = (TextView) findViewById(R.id.editTextTextDateOfBirth);
        textInputCellNumber = (TextView) findViewById(R.id.textInputCellNumber);
        button_continue1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = String.valueOf(textInputName.getText());
                String surname = String.valueOf(editTextSurname.getText());
                String dob = String.valueOf(editTextTextDateOfBirth.getText());
                String cell = String.valueOf(textInputCellNumber.getText());

                if (name.isEmpty() || surname.isEmpty() || dob.isEmpty() || cell.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please provide valid data", Toast.LENGTH_SHORT).show();
                }else{


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("surname", surname);
                    editor.putString("dob", dob);
                    editor.putString("cellphone", cell);
                    editor.apply();
                    Intent intent = new Intent(RegisterActivityPersonal.this, RegisterActivityCompany.class);

                    startActivity(intent);
                }
            }
        });
        goToLogin.setOnClickListener(view -> {
            Intent i = new Intent(RegisterActivityPersonal.this, LoginActivity.class);
            startActivity(i);
            finish();
        });
    }
}