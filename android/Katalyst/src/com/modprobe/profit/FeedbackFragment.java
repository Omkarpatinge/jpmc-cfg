package com.modprobe.profit;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class FeedbackFragment extends Fragment {
	
	View view;
	int forwhom;
	public FeedbackFragment(int x) {
		// TODO Auto-generated constructor stub
		forwhom = x;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.feedback,
				container, false);
		final EditText feedback=(EditText)view.findViewById(R.id.feedback);
        final RatingBar ratingbar=(RatingBar)view.findViewById(R.id.ratingBar1);
        Button button=(Button)view.findViewById(R.id.sendfeedbackbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting the rating and displaying it on the toast
                final String rating=String.valueOf(ratingbar.getRating());
                final String text=feedback.getText().toString();
                double dd = Double.parseDouble(rating);
                final int n = (int)dd;
                final String str=n+"";
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        MultipartUtility data= null;
                        try {
                            data = new MultipartUtility("http://10.242.179.31:8000/getFeedback/","UTF-8");
                            data.addFormField("mobile", AppController.getInstance().prefs.getString("mobile", "9930113199"));
                            data.addFormField("text",text);
                            data.addFormField("rating",str);
                            data.addFormField("forwhom", ""+forwhom);
                            List<String> lis = data.finish();
                            
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    protected void onPostExecute(Void result) {
                    	
                    };
                    
                }.execute();
                }


        });
		return view;
	}
}
