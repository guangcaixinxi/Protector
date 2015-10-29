package com.example.lenovo.friday_app_sms.div;

import android.app.PendingIntent;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by lenovo on 2015/10/28.
 */
public class SmsDiv {
    public int sendMsg(String number,String msg,PendingIntent sentPi,PendingIntent deliveryPi)
    {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> contents = smsManager.divideMessage(msg);

        for (String content:contents)
        {
            smsManager.sendTextMessage(number,null,content,sentPi,deliveryPi);
        }
        return contents.size();
    }

    public int sendMsg(HashSet<String> numbers,String msg,PendingIntent sentPi,PendingIntent deliveryPi)
    {
        int result = 0;

        for (String number:numbers)
        {
            int count = sendMsg(number,msg,sentPi,deliveryPi);
            result = count;
        }
        return result;
    }
}
