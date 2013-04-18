package com.wifiplayer.activitys.utils;


import com.wifiplayer.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
/**
 * 此类的功能是在耗时比较长的时候提高用户体验
 * @author Administrator
 *
 */
@SuppressLint("HandlerLeak")
public class PromptDialog {

	private Context context;
	private Dialog promptDialog;
	private Handler handler;
	public final String closeDialog = "closeDialog";
	
	public PromptDialog(Context context) {
		super();
		this.context = context;
		receiveHandler();
	}
	/**
	 * 展示用户提示框
	 * @return 当前提示框对象
	 */
	public void showPromptDialog(String str){
		promptDialog = new Dialog(context,R.style.no_title_no_bg_dialog);
		View view = LayoutInflater.from(context).inflate(R.layout.prompt_dialog, null); 
		TextView toastTextView = (TextView) view.findViewById(R.id.toastTextView);
		toastTextView.setText(str);
		promptDialog.setContentView(view);
		promptDialog.show();
		receiveHandler();
	}
	
	/**
	 * 展示用户提示框
	 * @return 当前提示框对象
	 */
	public void showPromptDialog(int str){
		String showMsg = context.getResources().getString(str);
		showPromptDialog(showMsg);
	}
	
	/**
	 * 等待关闭dialog
	 */
	private void receiveHandler(){
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				String str = msg.getData().getString("closeDialog");
				if(str.equals("closeDialog")){
					if(promptDialog!=null){
						try {
							promptDialog.cancel();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
		};
		
	}
	/**
	 * 发送关闭dialog命令
	 * @param str
	 */
	public void closePromptDialog(){
		new SendHandlerMsg().sendHandler(handler, "closeDialog", closeDialog);
	}
}
