package com.example.lenovo.friday_app_sms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.friday_app_sms.bean.Friday;
import com.example.lenovo.friday_app_sms.bean.FridayLib;
import com.example.lenovo.friday_app_sms.bean.Msg;
import com.example.lenovo.friday_app_sms.div.SmsDiv;
import com.example.lenovo.friday_app_sms.view.FlowLayout;

import java.net.URI;
import java.util.HashSet;

public class SendMsgActivity extends AppCompatActivity {

    public static final String KEY_FRIDAY_ID="mFridayId";
    public static final String KEY_MSG_ID="msgId";
    private  static  final int CODE_REQURST =1;

    private int mFridayId;
    private int msgId;

    private Msg msg;
    private Friday mFriday;

    private EditText medMsg;
    private Button mBtnAdd;
    private FlowLayout mFlContacts;
    private FloatingActionButton mFabSend;
    private View layoutLoading;

    private HashSet<String> mContactNames = new HashSet<>();
    private HashSet<String> mContactNums =new HashSet<>();

    private LayoutInflater mInflate;

    public static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    public static final String ACTION_DELIVER_MSG = "ACTION_DELIVER_MSG";

    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private BroadcastReceiver mSendBroadcastReciver;
    private BroadcastReceiver mDeliverBroadcastReciver;

    private SmsDiv mSmsDiv = new SmsDiv();

    private int mMsgSendCount = 0;
    private int mTotalCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        mInflate = LayoutInflater.from(this);

        initDatas();
        initViews();
        initEvent();
        initReciver();
    }

    private void initReciver() {
        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi =  PendingIntent.getBroadcast(this,0,sendIntent,0);
        Intent deliverIntent = new Intent(ACTION_DELIVER_MSG);
        mDeliverPi =  PendingIntent.getBroadcast(this,0,deliverIntent,0);

        registerReceiver(mSendBroadcastReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                mMsgSendCount++;
                if (getResultCode() == RESULT_OK) {
                    Log.e("TAG", "短信发送成功" + (mMsgSendCount + "/" + mTotalCount));
                } else {
                    Log.e("TAG", "短信发送失败");
                }
                Toast.makeText(SendMsgActivity.this,(mMsgSendCount + "/" + mTotalCount)+"短信发送成功",Toast.LENGTH_SHORT).show();

                if (mMsgSendCount == mTotalCount) {
                    finish();
                }
            }
        }, new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(mDeliverBroadcastReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("TAG", "联系人已经成功收到我们的短信");
            }
        }, new IntentFilter(ACTION_SEND_MSG));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendBroadcastReciver);
        unregisterReceiver(mDeliverBroadcastReciver);
    }

    private void initEvent() {
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_REQURST);
            }
        });

        mFabSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(mContactNums.size() == 0)
                {
                    Toast.makeText(SendMsgActivity.this,"请先选择联系人",Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = medMsg.getText().toString();
                if (TextUtils.isEmpty(msg)){
                    Toast.makeText(SendMsgActivity.this,"短信内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                layoutLoading.setVisibility(View.VISIBLE);
                mTotalCount = mSmsDiv.sendMsg(mContactNums,msg,mSendPi,mDeliverPi);
                mMsgSendCount = 0;
            }
        });
    }

    private void initViews() {
        medMsg = (EditText) findViewById(R.id.id_et_content);
        mBtnAdd = (Button)findViewById(R.id.id_btn_add);
        mFlContacts = (FlowLayout)findViewById(R.id.id_fl_contacts);
        mFabSend = (FloatingActionButton)findViewById(R.id.id_fab_send);
        layoutLoading = (View)findViewById(R.id.id_layout_loading);

        layoutLoading.setVisibility(View.GONE);
        if(msgId != -1){
            msg = FridayLib.getInstance().getMsgById(msgId);
            medMsg.setText(msg.getContent());
        }
    }

    private void initDatas() {
        mFridayId = getIntent().getIntExtra(KEY_FRIDAY_ID,-1);
        msgId = getIntent().getIntExtra(KEY_MSG_ID,-1);

        mFriday = FridayLib.getInstance().getFridayById(mFridayId);
        setTitle(mFriday.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_REQURST)
        {
            if (resultCode == RESULT_OK)
            {
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                String number= getContactNumber(cursor);
                if (!TextUtils.isEmpty(number))
                {
                    mContactNames.add(contactName);
                    mContactNums.add(number);

                    addTag(contactName);
                }
            }
        }
    }

    private void addTag(String contactName) {
        TextView view = (TextView) mInflate.inflate(R.layout.tag, mFlContacts, false);
        view.setText(contactName);
        mFlContacts.addView(view);
    }


    public String getContactNumber(Cursor cursor) {
        int NumberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number = null;

        if(NumberCount > 0){
            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +"="+contactId,null,null);
            phoneCursor.moveToFirst();
            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }
        cursor.close();
        return number;
    }

    public static void toActivity(Context context,int mFridayId,int msgId){
        Intent intent = new Intent(context,SendMsgActivity.class);
        intent.putExtra(KEY_FRIDAY_ID,mFridayId);
        intent.putExtra(KEY_MSG_ID,msgId);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_msg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
