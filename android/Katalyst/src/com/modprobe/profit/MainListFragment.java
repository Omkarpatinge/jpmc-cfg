package com.modprobe.profit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainListFragment extends Fragment {

	View view;
	ListView familyListView;
	List<String> strings;
	ArrayAdapter<String> adapter;
	public MainListFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		((MainActivity)getActivity()).getSupportActionBar().setTitle("Appointments");
		view = inflater.inflate(R.layout.main_list_fragment, container, false);
		familyListView = (ListView) view.findViewById(R.id.familyListView);
		strings = new ArrayList<String>();
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				MultipartUtility mp=null;
				try {
					if(AppController.getInstance().prefs.getBoolean("mentor", false))
						mp = new MultipartUtility("http://10.242.179.31:8000/getMyChatsMentor/", "UTF-8");
					else
						mp = new MultipartUtility("http://10.242.179.31:8000/getMyChatsMentee/", "UTF-8");
				mp.addFormField("mobile", AppController.getInstance().prefs.getString("mobile", "9930113199"));
				List<String> response = mp.finish();
				String arr[] = response.get(0).split("~");
				for(String s: arr)	strings.add(s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				adapter = new ArrayAdapter<String>(getActivity(),
			              android.R.layout.simple_list_item_1, android.R.id.text1, strings);
				familyListView.setAdapter(adapter);
			};
			
		}.execute();
		
		familyListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Log.e("pressed", String.valueOf(arg2));
				int id = Integer.parseInt(((String) familyListView.getItemAtPosition(arg2)).split(" ")[2]);
//				Subuser fam = family.get(arg2);
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new ChatViewFragment(id)).addToBackStack("query").commit();
				//Go to chat screen
				
			}
		});
		
		Button but = (Button) view.findViewById(R.id.add_family);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				
				//Go to start new appt screen
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new EditAppointmentFrag()).addToBackStack("add").commit();
				
				
				
			}
		});
		return view;
	}
	
	

}
