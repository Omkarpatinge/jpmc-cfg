package com.modprobe.profit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ChatViewFragment extends Fragment {
	private ListView mListView;
	private Button mButtonSend;
	private EditText mEditTextMessage;
	private RelativeLayout mProgressBar;
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String TAG = "MainActivity";
	private int currentFormat = 0;
	String message;
	private int id,idc;
	private QueryMessage new_message;
	private ChatMessageAdapter mAdapter;
	public ChatViewFragment(int id) {
		this.id = id;
		idc=id;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_chat_view,
				container, false);
		mProgressBar = (RelativeLayout) rootView
				.findViewById(R.id.progressBar1);
		mListView = (ListView) rootView.findViewById(R.id.listView);
		mButtonSend = (Button) rootView.findViewById(R.id.btn_send);
		
		mEditTextMessage = (EditText) rootView.findViewById(R.id.text_message);
		
		final ArrayList<QueryMessage> messages = new ArrayList<QueryMessage>();
		
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				try {
					MultipartUtility mp = null;
					if(AppController.getInstance().prefs.getBoolean("mentor", false))
							mp = new MultipartUtility("http://10.242.179.31:8000/getChatHistoryMentor/", "UTF-8");
					else
						mp = new MultipartUtility("http://10.242.179.31:8000/getChatHistoryMentee/", "UTF-8");
					mp.addFormField("id", id+"");
					List<String> ls = mp.finish();
					String ss = ls.get(0);
					for(String s : ss.split("~")){
						String msg = s.split(",")[0];
						int num = Integer.parseInt(s.split(",")[1]);
						messages.add(new QueryMessage(	num, msg, num, num, num));
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			protected void onPostExecute(Void result) {
				mAdapter = new ChatMessageAdapter(getActivity(), messages);
				mListView.setAdapter(mAdapter);
				
				RefreshThread refreshThread = new RefreshThread();
			    refreshThread.isStop = false;
			    refreshThread.start();
			};
			
		}.execute();
		
		
		
		
		mButtonSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				message = mEditTextMessage.getText().toString();
				if (TextUtils.isEmpty(message)) {
					return;
				}
				mEditTextMessage.setText("");
				new AsyncTask<Void, Void, Void>() {

					protected Void doInBackground(Void[] arg0) {

						try {
							doFileUpload(null, Constants.TEXT, message);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;

					};

					protected void onPostExecute(Void result) {
						mAdapter.data.add(new_message);
						mAdapter.notifyDataSetChanged();
					};

				}.execute();

				// mProgressBar.setVisibility(View.VISIBLE);
			}
		});


		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.chat_view_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.see_goals) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.beginTransaction()
					.replace(R.id.container,
							new GoalFragment(idc))
					.addToBackStack("PrescriptionFragment").commit();
			return true;
		}
		
		if (id == R.id.edit) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			fm.beginTransaction()
					.replace(R.id.container,
							new EditAppointmentFrag())
					.addToBackStack("PrescriptionFragment").commit();
			return true;
		}
		

		return super.onOptionsItemSelected(item);
	}

	

	
	public void doFileUpload(File f, int type, String data)
			throws JSONException {
		Log.e("CHAT VIEW", "Started");
		String charset = "UTF-8";
		String requestURL = null;
		if(AppController.getInstance().prefs.getBoolean("mentor", false))
		requestURL = Constants.BAE_URL + "addQueryMessageMentor/";
		else
		requestURL = Constants.BAE_URL + "addQueryMessageMentee/";

		MultipartUtility multipart;
		try {
			multipart = new MultipartUtility(requestURL, charset);
			
			multipart.addFormField("id", id+"");
			multipart.addFormField("text", data);
			List<String> response = multipart.finish(); // response from server.
//				//multipart.addFormField("data", data);
//			
////			
//
////			int message_id = Integer.parseInt(response.get(0));
			if(AppController.getInstance().prefs.getBoolean("mentor", false))
				new_message = new QueryMessage(1, data, type, 1, 2);
			else
				new_message = new QueryMessage(1, data, type, 2, 2);
			

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	private class RefreshThread extends Thread{
        public boolean isStop = false;

        public void run(){
            try{
                while(!isStop){                                             
                    getActivity().runOnUiThread(new Runnable() {                   
                        @Override
                        public void run() {
                               //List refresh logic goes here   
                                //yourListView.invalidateViews(); 
                        	final ArrayList<QueryMessage> messages = new ArrayList<QueryMessage>();
                        	
                        	new AsyncTask<Void, Void, Void>(){

                    			@Override
                    			protected Void doInBackground(Void... params) {
                    				try {
                    					MultipartUtility mp = null;
                    					if(AppController.getInstance().prefs.getBoolean("mentor", false))
                    							mp = new MultipartUtility("http://10.242.179.31:8000/getChatHistoryMentor/", "UTF-8");
                    					else
                    						mp = new MultipartUtility("http://10.242.179.31:8000/getChatHistoryMentee/", "UTF-8");
                    					mp.addFormField("id", id+"");
                    					List<String> ls = mp.finish();
                    					String ss = ls.get(0);
                    					for(String s : ss.split("~")){
                    						String msg = s.split(",")[0];
                    						int num = Integer.parseInt(s.split(",")[1]);
                    						messages.add(new QueryMessage(	num, msg, num, num, num));
                    					}
                    					
                    				} catch (IOException e) {
                    					// TODO Auto-generated catch block
                    					e.printStackTrace();
                    				}
                    				return null;
                    			}
                    			protected void onPostExecute(Void result) {
                    				mAdapter.clear();
                    				mAdapter.addAll(messages);
                                    mAdapter.notifyDataSetChanged();
                                       Log.e("tag", "msg");
                    			};
                    			
                    		}.execute();
                        	
                    		
                        }
                    });                         
                    try{ Thread.sleep(2000); } catch(Exception ex){}
                }
        }catch(Exception e){
        }
      }//run
    }//thread

}
