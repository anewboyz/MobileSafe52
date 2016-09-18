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
	private TextView tvProgress;//���ؽ���չʾ
	
	private String mVersonName;//�汾����
	private int mVersonCode;//�汾��
	private String mDesc;//�汾����
	private String mDownloadUrl;//���ص�ַ
	
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CODE_UPDATE_DIALOG:
				//System.out.println("mHandler�������ˣ�"+CODE_UPDATE_DIALOG);
				showUpdateDailog();
				break;
			case CODE_URL_DIALOG:
				Toast.makeText(SplashActivity.this, "url����", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_NET_DIALOG:
				Toast.makeText(SplashActivity.this, "�������", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_JSON_DIALOG:
				Toast.makeText(SplashActivity.this, "���ݽ�������", Toast.LENGTH_SHORT).show();
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
		tvVersion.setText("�汾��"+getVersionName());
		tvProgress=(TextView) findViewById(R.id.tvProgress);
		
		checkVersion();
	}
	/**
	 * ��ȡ����app�汾����
	 * @return
	 */
	private String getVersionName()
	{
		//������ getPackageManager ctrl+2 �ٰ� L
		PackageManager packageManager = getPackageManager();
		
		try {
			//��ȡ����Ϣ
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
	 * ��ȡ����app�汾��
	 * @return
	 */
	private int getVersionCode()
	{
		//������ getPackageManager ctrl+2 �ٰ� L
		PackageManager packageManager = getPackageManager();
		
		try {
			//��ȡ����Ϣ
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
	 * �ӷ�������ȡ�汾�Ž��м��
	 */
	private void checkVersion()
	{
		//�������߳��첽��������
		final long startTime = System.currentTimeMillis();
		new Thread(){ 
			private HttpURLConnection conn=null;
			
 
			@Override
			public void run() {
				Message msg=Message.obtain();
				try {
					URL url=new URL("http://crm.xuetian.cn/appfile/update.json");
					//ctrl+1 ѡ��Convert ���� to filed ת��Ϊȫ�ֱ���
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");//�������󷽷�
					conn.setConnectTimeout(5000);//�������ӳ�ʱ
					conn.setReadTimeout(5000);//������Ӧ��ʱ���������ˣ����Ƿ������ٳٲ�����Ӧ
					conn.connect();//���ӷ�����
					
					int responseCode=conn.getResponseCode();//��ȡ��Ӧ��
					if(responseCode==200)
					{
						InputStream inputStream = conn.getInputStream();
						String result=StreamUtils.readFromStream(inputStream);
						
						//System.out.println("���緵�أ�"+result);
						//����json
						JSONObject jo=new JSONObject(result);
						mVersonName = jo.getString("versionName");
						mVersonCode = jo.getInt("versionCode");
						mDesc = jo.getString("description");
						mDownloadUrl = jo.getString("downloadUrl");
						
						//System.out.println("�汾������"+mVersonCode);
						
						if(mVersonCode>getVersionCode())//�ж��Ƿ��и���
						{//��������versionCode���ڱ��ص�versionCode
						 //˵���и��£������Ի�����ʾ����
							msg.what=CODE_UPDATE_DIALOG;
							//System.out.println("���£�"+CODE_UPDATE_DIALOG);
						}
						else {
							//û�а汾����
							msg.what=CODE_ENTER_HOME;
						}
					}
					
				} catch (MalformedURLException e) {
					// url�쳣
					msg.what=CODE_URL_DIALOG;
					e.printStackTrace();
				} catch (IOException e) {
					// ��������쳣
					msg.what=CODE_NET_DIALOG;
					e.printStackTrace();
				} catch (JSONException e) {
					// json����ʧ��
					msg.what=CODE_JSON_DIALOG;
					e.printStackTrace();
				}finally{
					long endTiem=System.currentTimeMillis();
					Long timeUsed=endTiem-startTime;//�������绰�ѵ�ʱ��
					if(timeUsed<2000)
					{
						//ǿ������һ��ʱ�䣬��֤����ҳչʾ����
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
						conn.disconnect();//�ر���������
					}
				}
			}
		}.start();
		
	}

	/**
	 * �����Ի���
	 */
	protected void showUpdateDailog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("���°汾��"+mVersonName);
		builder.setMessage(mDesc);
		System.out.println("builder�������ˣ�");
		builder.setPositiveButton("��������",new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("��������");
				download();
			}
		});
		
		builder.setNegativeButton("�Ժ���˵", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				enterHome();
				
			}
		} );
		
		builder.show();
	}
	
	/** 
	 * ����apk�ļ�
	 */
	protected void download()
	{
		//���sd���Ƿ����
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			tvProgress.setVisibility(View.VISIBLE);//��ʾ����
			String target=Environment.getExternalStorageDirectory()+"/update.apk";
			//xUtils
			HttpUtils utils=new HttpUtils();
			System.out.println("���ص�ַ��"+mDownloadUrl);
			utils.download(mDownloadUrl, target, new RequestCallBack<File>() {
				
				//�ļ����ؽ���
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) { 
					super.onLoading(total, current, isUploading);
					tvProgress.setText("���ؽ��ȣ�"+current*100/total+"%");
				}
				//���سɹ�
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					 //��ת��ϵͳ����ҳ��
					Intent intent=new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setDataAndType(Uri.fromFile(arg0.result),
							"application/vnd.android.package-archive");
					
					startActivityForResult(intent, 0);//����û�ȡ����װ���᷵�ؽ�����ص�onActivityResult
					
				}
				//����ʧ��
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(SplashActivity.this, "����ʧ��",Toast.LENGTH_SHORT).show();
					
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
			
			Toast.makeText(SplashActivity.this, "û���ҵ�sdcard", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	/**
	 * ����������
	 */
	private void enterHome()
	{
		Intent intent=new Intent(this,HomeActivity.class);
		startActivity(intent);
		finish();//��������Ժ󣬵㷵�ز��ᵽ����ҳ
	}

}
