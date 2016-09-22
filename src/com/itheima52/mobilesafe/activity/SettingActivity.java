package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {

	private SettingItemView sivUpdate;//设置升级
	private SharedPreferences mPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setting);
		mPref=getSharedPreferences("config",MODE_PRIVATE);
		
		sivUpdate=(SettingItemView) findViewById(R.id.siv_update);
		sivUpdate.setTitle("自动更新设置");
		sivUpdate.setDesc("自动更新已开启");
		
		boolean autoUpdate=mPref.getBoolean("auto_update", true);
		if(autoUpdate)
		{
			sivUpdate.setChecked(true);
		}
		else {
			sivUpdate.setChecked(false);
		}
		
		sivUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(sivUpdate.isChecked())
				{
					//设置不勾选
					sivUpdate.setChecked(false);
					//更新sp
					mPref.edit().putBoolean("auto_update", false).commit();
				}
				else {
					//设置勾选
					sivUpdate.setChecked(true);
					//更新sp
					mPref.edit().putBoolean("auto_update", true).commit();
				}
				
			}
		});
	}
}
