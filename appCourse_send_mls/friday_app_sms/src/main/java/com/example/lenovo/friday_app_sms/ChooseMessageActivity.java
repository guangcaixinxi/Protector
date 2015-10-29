package com.example.lenovo.friday_app_sms;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.friday_app_sms.bean.FridayLib;
import com.example.lenovo.friday_app_sms.bean.Msg;
import com.example.lenovo.friday_app_sms.fragment.FridayMessFragment;

public class ChooseMessageActivity extends AppCompatActivity {

    private ListView mLvMsgs;
    private FloatingActionButton mFabToSend;
    private ArrayAdapter<Msg> mAdaptor;
    int mFridayId ;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_message);
        mFridayId = getIntent().getIntExtra(FridayMessFragment.ID_FRIDAY,-1);
        mInflater = LayoutInflater.from(this);

        setTitle(FridayLib.getInstance().getFridayById(mFridayId).getName());
        initViews();
        initEvent();
    }

    private void initEvent() {
        mFabToSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SendMsgActivity.toActivity(ChooseMessageActivity.this,mFridayId,-1);
            }
        });
    }

    private void initViews() {
        mLvMsgs = (ListView) findViewById(R.id.id_lv_msgs);
        mFabToSend = (FloatingActionButton)findViewById(R.id.id_fri_toSend);
        mLvMsgs.setAdapter(mAdaptor = new ArrayAdapter<Msg>(this,-1,
                FridayLib.getInstance().getMsgByFridayId(mFridayId)){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = mInflater.inflate(R.layout.item_msg,parent,false);
                }
                TextView content = (TextView)convertView.findViewById(R.id.id_tv_content);
                Button toSend = (Button) convertView.findViewById(R.id.id_btn_toSend);

                content.setText("  "+getItem(position).getContent());
                toSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SendMsgActivity.toActivity(ChooseMessageActivity.this,mFridayId,getItem(position).getId());
                    }
                });
                return convertView;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_message, menu);
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
