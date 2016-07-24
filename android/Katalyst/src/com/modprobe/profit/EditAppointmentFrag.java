package com.modprobe.profit;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EditAppointmentFrag extends Fragment{
	View view;
	EditText loc,date1,time1;
    Button bt;
	private EditAppointmentFrag mContext;
    
    
    public EditAppointmentFrag() {
		mContext = this;
	}
    
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.activity_appointment,
				container, false);
		bt = (Button)view.findViewById(R.id.clickme);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String date=date1.getText().toString();
		        final String time=time1.getText().toString();
		        final String location = loc.getText().toString();
		        new AsyncTask<Void, Void, Void>() {
		            @Override
		            protected Void doInBackground(Void... params) {
		                MultipartUtility data= null;
		                try {
		                    data = new MultipartUtility("http://10.242.179.31:8000/getAppointmentDetails/","UTF-8");
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
		                data.addFormField("mobile", AppController.getInstance().prefs.getString("mobile", "9930113199"));
		                data.addFormField("location",location);
		                data.addFormField("date",date);
		                data.addFormField("time",time);
		                try {
							List<String> lis = data.finish();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                
		                return null;
		            }
		            
		            protected void onPostExecute(Void result) {
		            	
		            };
		        }.execute();

				
			}
		});
		loc = (EditText) view.findViewById(R.id.setlocation);
        date1=(EditText)view.findViewById(R.id.setdate);
        time1=(EditText)view.findViewById(R.id.settime);
		return view;
	}
	



}
