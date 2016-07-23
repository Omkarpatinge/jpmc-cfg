package com.example.android.feedback;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText feedback=(EditText)findViewById(R.id.feedback);
        final RatingBar ratingbar=(RatingBar)findViewById(R.id.ratingBar1);
        Button button=(Button)findViewById(R.id.sendfeedbackbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting the rating and displaying it on the toast
                final String rating=String.valueOf(ratingbar.getRating());
                final int n=Integer.parseInt(feedback.getText().toString());
                final String str=n+"";
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        MultiPartUtility data= null;
                        try {
                            data = new MultiPartUtility("http://10.242.179.31:8000/getFeedback/","UTF-8");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        data.addFormField("text",str);
                        data.addFormField("rating",rating);
                        return null;
                    }
                }.execute();
                }


        });

    }
}
