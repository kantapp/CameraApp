package com.kantapp.cameraapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kantapp.cameraapp.Model.Field;
import com.kantapp.cameraapp.Model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserForm extends AppCompatActivity {

    private Button nextBtn;
    private EditText fullname,email,phone;
    private TextView skip;
    private String TAG="Userform.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phoneNumber);
        nextBtn=findViewById(R.id.nextBtn);
        skip=findViewById(R.id.skip);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Field.fullname=fullname.getText().toString().trim();
                Field.phone=phone.getText().toString().trim();
                Field.email=email.getText().toString().trim();

                if(Field.fullname.isEmpty())
                {
                    fullname.setError("Please Enter your name");
                }
                else if(Field.phone.length()!=10)
                {
                    phone.setError("Please Enter Valid Phone No.");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(Field.email).matches())
                {
                    email.setError("Please Enter Valid Email id");
                }
                else
                {
                    SQLite sqLite=new SQLite(UserForm.this);
                    Boolean insertResult= sqLite.addUser(Field.fullname,Field.phone,Field.email);
                    if (insertResult)
                    {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(UserForm.this, "Somthing Went wrong Please Check Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }


}
