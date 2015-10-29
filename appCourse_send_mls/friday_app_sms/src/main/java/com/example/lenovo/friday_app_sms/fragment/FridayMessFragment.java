package com.example.lenovo.friday_app_sms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.lenovo.friday_app_sms.ChooseMessageActivity;
import com.example.lenovo.friday_app_sms.R;
import com.example.lenovo.friday_app_sms.bean.Friday;
import com.example.lenovo.friday_app_sms.bean.FridayLib;

/**
 * Created by lenovo on 2015/10/20.
 */
public class FridayMessFragment extends Fragment
{
    private GridView mGridView;
    private ArrayAdapter<Friday> mAdapter;
    private LayoutInflater mInflater;
    public static final String ID_FRIDAY ="friday_id";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friday_category,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());
        mGridView = (GridView)view.findViewById(R.id.id_gv_friday_category);
        mGridView.setAdapter(mAdapter = new ArrayAdapter<Friday>(getActivity(), -1, FridayLib.getInstance().getFridayList()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_friday, parent, false);
                }
                TextView tv = (TextView) convertView.findViewById(R.id.id_tv_friday_name);
                tv.setText(getItem(position).getName());
                return convertView;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChooseMessageActivity.class);
                intent.putExtra(ID_FRIDAY,mAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
    }
}
