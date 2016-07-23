package com.example.android.katalyst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.List;

public class SmsListener extends BroadcastReceiver {

    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            final Bundle bundle = intent.getExtras();           //---get the SMS message passed in---

            String msg_from;
            if (bundle != null){


                new AsyncTask<Void, Void, Void>(){

                    @Override
                    protected Void doInBackground(Void... params) {
                        // TODO Auto-generated method stub
                        MultipartUtility mp;
                        try{
                            SmsMessage[] msgs = null;
                            Object[] pdus = (Object[]) bundle.get("pdus");
                            msgs = new SmsMessage[pdus.length];
                            for(int i=0; i<msgs.length; i++){
                                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                                String msgBody = msgs[i].getMessageBody();
                                String tomobile=msgs[i].getOriginatingAddress();
                                Log.v("SmsListener.java",msgBody);
                                MultipartUtility data=new MultipartUtility("http://10.242.179.31:8000/getSms/","UTF-8");
                                data.addFormField("mymobile","9029415675");
                                data.addFormField("tomobile",tomobile);
                                data.addFormField("text",msgBody);
                                List<String> response = data.finish();
                                String reply=response.get(0);
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(tomobile, null, reply, null, null);
                                Log.v("response from server",response.get(0));
                            }
                        }catch(Exception e){
                            Log.d("Exception caught",e.getMessage());
                        }
                        return null;
                    }

                    protected void onPostExecute(Void result) {
                    };

                }.execute();

            }
        }
    }
}