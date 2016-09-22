package com.itheima52.mobilesafe.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.itheima52.mobilesafe.R;


public class SettingItemView extends RelativeLayout {
	
	private static final String NAMESPACE="http://schemas.android.com/apk/res/com.itheima52.mobilesafe";
	private TextView tvTitle;
	private TextView tvDesc;
	private CheckBox cbStatus;
	private String mTitle;
	private String mDescOn;
	private String mDescOff;
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(); 
	}
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 根据属性名称，获取属性的值
		mTitle=attrs.getAttributeValue(NAMESPACE,"title");
		mDescOn=attrs.getAttributeValue(NAMESPACE,"desc_on");
		mDescOn=attrs.getAttributeValue(NAMESPACE,"desc_off");
		initView();
	}
	public SettingItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	private void initView(){
		//将自定义好的布局文件给当前的SettingItemView
		
		View.inflate(getContext(), R.layout.view_seting_item, this);
		tvTitle= (TextView) findViewById(R.id.tv_title);
		tvDesc=(TextView) findViewById(R.id.tv_desc);
		cbStatus=(CheckBox) findViewById(R.id.cb_status);
		
		setTitle(mTitle);//设置标题
	}

	public void setTitle(String title)
	{
		tvTitle.setText(title);
	}
	
	public void setDesc(String desc)
	{
		tvDesc.setText(desc);
	}
	/**
	 * 返回勾选状态
	 * @return
	 */
	public boolean isChecked()
	{
		return cbStatus.isChecked();
	}
	public void setChecked(boolean check)
	{
		cbStatus.setChecked(check);
		
		//根据选择的状态更新文本描述
		if(check)
		{
			setDesc(mDescOn);
		}
		else
		{
			setDesc(mDescOff);
		}
	}
	
	
	

}
