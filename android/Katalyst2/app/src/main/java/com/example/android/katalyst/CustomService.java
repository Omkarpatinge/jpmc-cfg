package com.example.android.katalyst;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class CustomService extends IntentService {
    public CustomService(){ super("");}
    @Override
    protected void onHandleIntent(Intent intent) {
    RefreshThread rt=new RefreshThread();
        rt.isStop=false;
        rt.start();
    }

    private class RefreshThread extends Thread{
        public boolean isStop = false;

        public void run(){
            try{
                while(!isStop){
                    Log.v("sfsa","safe");
                        new Runnable() {
                        @Override
                        public void run() {
                            //List refresh logic goes here
                            //yourListView.invalidateViews();


                            new AsyncTask<Void, Void, Void>(){

                                @Override
                                protected Void doInBackground(Void... params) {
                                    try {
                                        MultipartUtility mp = new MultipartUtility("http://10.242.179.31:8000/anyMessage/","UTF-8");
                                        List<String> response = mp.finish();
                                        String str=response.get(0);
                                        if(!str.equalsIgnoreCase("oops")) {
                                            String[] str1 = str.split("/");
                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(str1[0], null, str1[1], null, null);
                                        }



                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    return null;
                                }
                                protected void onPostExecute(Void result) {

                                    Log.e("tag", "msg");
                                };

                            }.execute();


                        }
                    }.run();
                    try{ Thread.sleep(10000); } catch(Exception ex){}
                }
            }catch(Exception e){
            }
        }//run
    }//thread
}
