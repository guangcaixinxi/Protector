package com.example.lenovo.friday_app_sms;

import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.friday_app_sms.fragment.FridayMessFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] mTitles = new String[]{"求助短信",""};

    private Button start=null;
    private Button stop=null;
    private TextView state=null;
    private MediaPlayer mp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtons();
        initViews();
    }

    private void initButtons() {
        //取得各按钮组件
        start=(Button) super.findViewById(R.id.start);
        stop=(Button) super.findViewById(R.id.stop);
        state=(TextView)super.findViewById(R.id.state);
        //为每个按钮设置单击事件
        start.setOnClickListener(new OnClickListenerStart());
        stop.setOnClickListener(new OnClickListenerStop());
        mp3= new MediaPlayer();    //创建一个MediaPlayer对象
        //在res下新建一个raw文件夹把一首歌放到此文件夹中并用英文命名
        mp3 = MediaPlayer.create(MainActivity.this,R.raw.a);
    }

    //各按钮单击事件的实现如下
    //开始播放
    private class OnClickListenerStart implements View.OnClickListener {
    //implementsOnClickListener为实现OnClickListener接口
    @Override
    //重写onClic事件
    public void onClick(View v)
    {
        //执行的代码，其中可能有异常。一旦发现异常，则立即跳到catch执行。否则不会执行catch里面的内容
        try
            {
                if (mp3!=null)
                {
                    mp3.stop();
                }
                mp3.prepare();         //进入到准备状态
                mp3.start();          //开始播放
                state.setText("");  //改变输出信息为“Playing”，下同
            } catch (Exception e)
            {
                state.setText(e.toString());//以字符串的形式输出异常
                e.printStackTrace();  //在控制台（control）上打印出异常
            }
        }
    }
    //停止播放
    private class OnClickListenerStop implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                if (mp3 != null) {
                    mp3.stop();
                    state.setText("");
                }
            } catch (Exception e) {
                state.setText(e.toString());
                e.printStackTrace();
            }
        }
    }
    private void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new FridayMessFragment();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
