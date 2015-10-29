package com.example.mp3demo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.media.MediaPlayer;
import java.io.File;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity

{ // ��������

	private Button start = null;

	private Button stop = null;

	private TextView state = null;

	private MediaPlayer mp3;

	@Override
	protected void onCreate(Bundle savedInstanceState)

	{

		super.onCreate(savedInstanceState);

		super.setContentView(R.layout.activity_main);

		// ȡ�ø���ť���

		start = (Button) super.findViewById(R.id.start);

		stop = (Button) super.findViewById(R.id.stop);

		state = (TextView) super.findViewById(R.id.state);

		// Ϊÿ����ť���õ����¼�

		start.setOnClickListener(new OnClickListenerStart());

		stop.setOnClickListener(new OnClickListenerStop());

		mp3 = new MediaPlayer(); // ����һ��MediaPlayer����

		// ��res���½�һ��raw�ļ��а�һ�׸�ŵ����ļ����в���Ӣ������

		mp3 = MediaPlayer.create(MainActivity.this, R.raw.a);

	}

	// ����ť�����¼���ʵ������

	// ��ʼ����

	private class OnClickListenerStart implements OnClickListener {

		// implementsOnClickListenerΪʵ��OnClickListener�ӿ�

		@Override
		// ��дonClic�¼�
		public void onClick(View v)

		{

			// ִ�еĴ��룬���п������쳣��һ�������쳣������������catchִ�С����򲻻�ִ��catch���������

			try

			{

				if (mp3 != null)

				{

					mp3.stop();

				}

				mp3.prepare(); // ���뵽׼��״̬

				mp3.start(); // ��ʼ����

				state.setText("Playing"); // �ı������ϢΪ��Playing������ͬ

			} catch (Exception e)

			{

				state.setText(e.toString());// ���ַ�������ʽ����쳣

				e.printStackTrace(); // �ڿ���̨��control���ϴ�ӡ���쳣

			}

		}

	}

	// ֹͣ����

	private class OnClickListenerStop implements OnClickListener {

		@Override
		public void onClick(View v)

		{

			try

			{

				if (mp3 != null)

				{

					mp3.stop();

					state.setText("stop");

				}

			} catch (Exception e)

			{

				state.setText(e.toString());

				e.printStackTrace();

			}

		}

	}

}