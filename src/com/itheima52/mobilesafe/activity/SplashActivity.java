package com.itheima52.mobilesafe.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
 
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends  Activity {

	protected static final int CODE_UPDATE_DIALOG = 0;
	protected static final int CODE_URL_DIALOG = 1;
	protected static final int CODE_NET_DIALOG = 2;
	protected static final int CODE_JSON_DIALOG =3;
	protected static final int CODE_ENTER_HOME =4;
	private TextView tvVersion;
	private TextView tvProgress;//下载进度展示
	
	private String mVersonName;//版本名称
	private int mVersonCode;//版本号
	private String mDesc;//版本描述
	private String mDownloadUrl;//下载地址
	
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CODE_UPDATE_DIALOG:
				//System.out.println("mHandler到这里了："+CODE_UPDATE_DIALOG);
				showUpdateDailog();
				break;
			case CODE_URL_DIALOG:
				Toast.makeText(SplashActivity.this, "url错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_NET_DIALOG:
				Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_JSON_DIALOG:
				Toast.makeText(SplashActivity.this, "数据解析错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_ENTER_HOME:
				enterHome();
				break;
			default:
				break;
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		tvVersion=(TextView) findViewById(R.id.tv_version);
		tvVersion.setText("版本："+getVersionName());
		tvProgress=(TextView) findViewById(R.id.tvProgress);
		
		checkVersion();
	}
	/**
	 * 获取本地app版本名称
	 * @return
	 */
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
	
	/**
	 * 获取本地app版本号
	 * @return
	 */
	private int getVersionCode()
	{
		//先输入 getPackageManager ctrl+2 再按 L
		PackageManager packageManager = getPackageManager();
		
		try {
			//获取包信息
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
			int versonCode=packageInfo.versionCode; 
			return versonCode;
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		return -1;
	}
	
	/**
	 * 从服务器获取版本号进行检测
	 */
	private void checkVersion()
	{
		//启动子线程异步加载数据
		final long startTime = System.currentTimeMillis();
		new Thread(){ 
			private HttpURLConnection conn=null;
			
 
			@Override
			public void run() {
				Message msg=Message.obtain();
				try {
					URL url=new URL("http://crm.xuetian.cn/appfile/update.json");
					//ctrl+1 选择Convert …… to filed 转换为全局变量
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");//设置请求方法
					conn.setConnectTimeout(5000);//设置连接超时
					conn.setReadTimeout(5000);//设置响应超时，连接上了，但是服务器迟迟不给响应
					conn.connect();//连接服务器
					
					int responseCode=conn.getResponseCode();//获取响应码
					if(responseCode==200)
					{
						InputStream inputStream = conn.getInputStream();
						String result=StreamUtils.readFromStream(inputStream);
						
						//System.out.println("网络返回："+result);
						//解析json
						JSONObject jo=new JSONObject(result);
						mVersonName = jo.getString("versionName");
						mVersonCode = jo.getInt("versionCode");
						mDesc = jo.getString("description");
						mDownloadUrl = jo.getString("downloadUrl");
						
						//System.out.println("版本描述："+mVersonCode);
						
						if(mVersonCode>getVersionCode())//判断是否有更新
						{//服务器的versionCode大于本地的versionCode
						 //说明有更新，弹出对话框提示升级
							msg.what=CODE_UPDATE_DIALOG;
							//System.out.println("更新："+CODE_UPDATE_DIALOG);
						}
						else {
							//没有版本更新
							msg.what=CODE_ENTER_HOME;
						}
					}
					
				} catch (MalformedURLException e) {
					// url异常
					msg.what=CODE_URL_DIALOG;
					e.printStackTrace();
				} catch (IOException e) {
					// 网络错误异常
					msg.what=CODE_NET_DIALOG;
					e.printStackTrace();
				} catch (JSONException e) {
					// json解析失败
					msg.what=CODE_JSON_DIALOG;
					e.printStackTrace();
				}finally{
					long endTiem=System.currentTimeMillis();
					Long timeUsed=endTiem-startTime;//访问网络话费的时间
					if(timeUsed<2000)
					{
						//强制休眠一段时间，保证闪屏页展示两秒
						try {
							Thread.sleep(2000-timeUsed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					
					mHandler.sendMessage(msg);
					if(conn!=null)
					{
						conn.disconnect();//关闭网络连接
					}
				}
			}
		}.start();
		
	}

	/**
	 * 升级对话框
	 */
	protected void showUpdateDailog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("最新版本："+mVersonName);
		builder.setMessage(mDesc);
		System.out.println("builder到这里了：");
		builder.setPositiveButton("立即更新",new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("立即更新");
				download();
			}
		});
		
		builder.setNegativeButton("以后再说", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				enterHome();
				
			}
		} );
		
		builder.show();
	}
	
	/** 
	 * 下载apk文件
	 */
	protected void download()
	{
		//检测sd卡是否挂载
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			tvProgress.setVisibility(View.VISIBLE);//显示进度
			String target=Environment.getExternalStorageDirectory()+"/update.apk";
			//xUtils
			HttpUtils utils=new HttpUtils();
			System.out.println("下载地址："+mDownloadUrl);
			utils.download(mDownloadUrl, target, new RequestCallBack<File>() {
				
				//文件下载进度
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) { 
					super.onLoading(total, current, isUploading);
					tvProgress.setText("下载进度："+current*100/total+"%");
				}
				//下载成功
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					 //跳转到系统下载页面
					Intent intent=new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setDataAndType(Uri.fromFile(arg0.result),
							"application/vnd.android.package-archive");
					
					startActivityForResult(intent, 0);//如果用户取消安装，会返回结果，回调onActivityResult
					
				}
				//下载失败
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(SplashActivity.this, "下载失败",Toast.LENGTH_SHORT).show();
					
				}
				@Override
				public void onFailure(
						com.lidroid.xutils.exception.HttpException arg0,
						String arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}
		else {
			
			Toast.makeText(SplashActivity.this, "没有找到sdcard", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	/**
	 * 进入主界面
	 */
	private void enterHome()
	{
		Intent intent=new Intent(this,HomeActivity.class);
		startActivity(intent);
		finish();//加上这个以后，点返回不会到闪屏页
	}

}
