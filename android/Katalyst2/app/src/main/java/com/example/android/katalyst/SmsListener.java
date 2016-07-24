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
                                if(msgBody.equalsIgnoreCase("*Info")){
                                    String reply="Welcome to Katalyst:\n" +
                                            "*start - Start Chat\n" +
                                            "*end - End Chat\n" +
                                            "*appoint \"location\" \"date\" \"time\" - Book Appointment";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(tomobile, null,reply , null, null);
                                }
                                else if(msgBody.equalsIgnoreCase("*start"))
                                {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(tomobile, null,"Chat has started" , null, null);
                                }
                                else if(msgBody.equalsIgnoreCase("*end"))
                                {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(tomobile, null,"Chat has ended" , null, null);
                                }
                                else if(msgBody.substring(0,8).equalsIgnoreCase("*appoint"))
                                {   String[] appt=msgBody.split(" ");
                                    MultipartUtility data=new MultipartUtility("http://10.242.179.31:8000/getAppointmentDetails/","UTF-8");
                                    data.addFormField("mobile",tomobile.substring(2));
                                    data.addFormField("location",appt[1]);
                                    data.addFormField("date",appt[2]);
                                    data.addFormField("time",appt[3]);
                                    List<String> response = data.finish();
                                }
                                else {
                                    MultipartUtility data=new MultipartUtility("http://10.242.179.31:8000/addQueryMessage/","UTF-8");
                                    data.addFormField("mobile",tomobile);
                                    data.addFormField("text",msgBody);
                                    List<String> response = data.finish();
                                }

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