package com.example.voicetext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


public class IncomingCallBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = "PHONE STATE";
    private static String mLastState;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(TAG,"onReceive()");

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(state.equals(TelephonyManager.EXTRA_STATE_IDLE) || state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Log.d(TAG,"통화종료 or 벨 울릴때");
        } else{
            Intent serviceIntent = new Intent(context, CallingService.class);
            context.startService(serviceIntent);
        }

/*
        if(state.equals(TelephonyManager.EXTRA_STATE_IDLE))
        {
            //통화 종료 후 구현 ...
        }
        else if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            //통화 벨 울릴 시 구현 ...
        }
        else if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
        {
            //통화 중 상태일 때 구현 ...
        }
        else if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL))
        {
            //전화를 걸때 상태 구현 ...
        }*/

    }
}