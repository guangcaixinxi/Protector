package com.example.lenovo.friday_app_sms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/10/20.
 */
public class FridayLib {
    public static FridayLib mInstance;
    private List<Friday> fridayList = new ArrayList<Friday>();
    private List<Msg> msgs = new ArrayList<Msg>();

    private FridayLib(){
        fridayList.add(new Friday(1,"抢劫"));
        fridayList.add(new Friday(2,"疾病"));
        fridayList.add(new Friday(3,"小偷"));

        msgs.add(new Msg(1,1,"快来救我！在上班途中遇到危险"));
        msgs.add(new Msg(2,1,"快来救我！老毛病又犯了"));
        msgs.add(new Msg(3,1,"快来救我！！！家里有贼！！！"));
    }

    //对外提供访问方法。
    public List<Friday> getFridayList() {
        return new ArrayList<Friday>(fridayList);
    }

    public Friday getFridayById(int frId){
        for (Friday friday:fridayList){
            if (friday.getId() == frId)
                return friday;
        }
        return null;
    }

    public List<Msg> getMsgByFridayId(int fridayId){
        List<Msg> msgsl = new ArrayList<Msg>();
        for (Msg msg:msgs){
            if (msg.getFridayId()== fridayId)
             msgsl.add(msg);
        }
        return  msgsl;
    }
    public Msg getMsgById(int id){
        for (Msg msg:msgs){
            if (msg.getId() == id)
                return msg;
        }
        return  null;
    }
    public static FridayLib getInstance(){
        if(mInstance == null){
            synchronized (FridayLib.class){
                if (mInstance == null){
                    mInstance = new FridayLib();
                }
            }
        }
        return mInstance;
    }

}
