package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 主界面
 * @author Administrator
 *
 */
public class HomeActivity extends Activity {

	private GridView gvHome;
	private String[] mItems=new String[]{"手机防盗", "通讯卫士", "软件管理", "进程管理",
			"流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
	
	private int[] mPics=new int[] {R.drawable.home_safe,R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		gvHome=(GridView) findViewById(R.id.gv_home);
		gvHome.setAdapter(new HomeAdapter());
		
		//设置监听
		gvHome.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				switch (position) {
				case 8: 
					// 设置中心
					startActivity(new Intent(HomeActivity.this,
							SettingActivity.class));
					break;

				default:
					break;
				}
				
			}
			
		});
		
	}
	
	class HomeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mItems.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mItems[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view=View.inflate(HomeActivity.this, R.layout.home_list_item, null);
			ImageView ivItem=(ImageView) view.findViewById(R.id.iv_item);
			TextView tvItem=(TextView) view.findViewById(R.id.tv_item);
			
			ivItem.setImageResource(mPics[arg0]);
			tvItem.setText(mItems[arg0]);
			
			return view;
		}
		
	};
}
