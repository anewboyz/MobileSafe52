package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

/**
 * Ö÷½çÃæ
 * @author Administrator
 *
 */
public class HomeActivity extends Activity {

	private GridView gvHome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		gvHome=(GridView) findViewById(R.id.gv_home);
		gvHome.setAdapter(new HomeAdapter());
	}
	
	class HomeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
}
