package com.modprobe.profit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class GoalFragment extends Fragment {
	int id;
	List<String> strings;
	public GoalFragment(int id){
		this.id = id;
	}
	View view;
	ListView goalListView;
	ArrayAdapter<String> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		((MainActivity)getActivity()).getSupportActionBar().setTitle("Appointments");
		view = inflater.inflate(R.layout.main_list_fragment, container, false);
		goalListView = (ListView) view.findViewById(R.id.familyListView);
		strings = new ArrayList<String>();
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				MultipartUtility mp;
				try {
					mp = new MultipartUtility("http://10.242.179.31:8000/getGoals/", "UTF-8");
				
					mp.addFormField("id", id+"");
					List<String> ls = mp.finish();
					
					for(String s : ls.get(0).split("~")){
						strings.add(s);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				adapter = new ArrayAdapter<String>(getActivity(),
			              android.R.layout.simple_list_item_1, android.R.id.text1, strings);
				goalListView.setAdapter(adapter);
			};
			
		}.execute();
		Button but = (Button) view.findViewById(R.id.add_family);
		but.setText("Send Feedback");
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, new FeedbackFragment(id)).addToBackStack("add").commit();
				
				
			}
		});
		return view;
	}
	
	
}
	
