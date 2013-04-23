package com.wifiplayer.activitys.views;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wifiplayer.R;
import com.wifiplayer.utils.IPAddressTool;
import com.wifiplayer.utils.SearchPcDialog;
/**
 * 连接电脑类
 * @author chenkaigang
 *
 */
public class ConnPc {

	private Context context;
	private Dialog dialog;
	
	
	public ConnPc(Context context, Dialog dialog) {
		super();
		this.context = context;
		this.dialog = dialog;
	}

	public static final String SEARCH_PC = "192.168.1.255";

	public View getView() {
		/* 初始化控件 */
		View view = LayoutInflater.from(context).inflate(R.layout.view_conn_pc_btn, null);
		LinearLayout searchPcBtn = (LinearLayout) view.findViewById(R.id.searchPcButton);//搜索局域网内可以控制的电脑
		LinearLayout connPcUseIPBtn = (LinearLayout) view.findViewById(R.id.connPcUseIPButton);//连接指定ip的电脑
		LinearLayout cancelBtn = (LinearLayout) view.findViewById(R.id.cancelButton);//取消按钮
		
		searchPcBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				SearchPcDialog.search(context, SEARCH_PC);
			}
		});
		
		connPcUseIPBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				showConnPcUseIP();
			}
		});
		
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		return view;

	}
	
	/**
	 * 显示通过输入ip地址连接电脑的视图
	 */
	private void showConnPcUseIP() {
		final Dialog dialog = new Dialog(context, R.style.no_title_dialog);
		View view = LayoutInflater.from(context).inflate(R.layout.view_conn_pc_use_ip, null);
		dialog.setContentView(view);
		dialog.show();
		
		Button okBtn = (Button) view.findViewById(R.id.okButton);
		Button cancelBtn = (Button) view.findViewById(R.id.cancelButton);
		final EditText ipAddrEditText = (EditText) view.findViewById(R.id.ipAddrEditText);
		
		okBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String ipAddr = ipAddrEditText.getText().toString();
				boolean isIP = IPAddressTool.isIPAdress(ipAddr);
				if (!isIP) {
					ipAddrEditText.setText(R.string.ip_err);
					return;
				}
				dialog.cancel();
				SearchPcDialog.search(context, ipAddr);
			}
		});
		
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				
			}
		});
		
	}
}
