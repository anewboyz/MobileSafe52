package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.R.id;
import com.itheima52.mobilesafe.R.layout;
import com.itheima52.mobilesafe.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.R.integer;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SplashActivity extends  Activity {

	private TextView tvVersion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvVersion=(TextView) findViewById(R.id.tv_version);
		tvVersion.setText("版本："+getVersionName());
	}
	
	private String getVersionName()
	{
		//先输入 getPackageManager ctrl+2 再按 L
		PackageManager packageManager = getPackageManager();
		
		try {
			//获取包信息
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
			int versonCode=packageInfo.versionCode;
			String versionName=packageInfo.versionName;
			return versionName;
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}




}
