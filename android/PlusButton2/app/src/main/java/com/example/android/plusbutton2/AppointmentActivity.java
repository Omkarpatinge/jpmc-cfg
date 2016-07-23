package com.example.android.plusbutton2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class AppointmentActivity extends AppCompatActivity {
    EditText loc,date1,time1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        loc = (EditText) findViewById(R.id.setlocation);
        date1=(EditText)findViewById(R.id.setdate);
        time1=(EditText)findViewById(R.id.settime);
    }

    public void submitApp(View v) {
        final String date=date1.getText().toString();
        final String time=time1.getText().toString();
        final String location = loc.getText().toString();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                MultiPartUtility data= null;
                try {
                    data = new MultiPartUtility("http://10.242.179.31:8000/getAppointmentDetails/","UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
                data.addFormField("location",location);
                data.addFormField("date",date);
                data.addFormField("time",time);
                return null;
            }
        }.execute();
    }




}
