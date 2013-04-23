package com.wifiplayer.utils;

import java.io.IOException;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.wifiplayer.R;
import com.wifiplayer.activitys.FunctionActivity;
import com.wifiplayer.activitys.views.EnableCtrPcListView;
import com.wifiplayer.bean.FindedPC;
import com.wifiplayer.net.udp.SearchPc;

/**
 * 搜索可以控制的电脑和手机
 * @author chenkaigang
 *
 */
public class SearchPcDialog {
	/**
	 * 搜索局域网内可以控制的电脑
	 */
	
	public static void search(final Context context, final String ipAddr) {
		final Handler searchHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				FunctionActivity.pd.closePromptDialog();
				List<FindedPC> pcs = (List<FindedPC>) msg.obj;
				if (pcs.isEmpty()) {
					Toast.makeText(context, R.string.no_pc_control, Toast.LENGTH_LONG).show();
					return;
				}
				
				Dialog dialog = new Dialog(context, R.style.no_title_dialog); 
				View view = new EnableCtrPcListView(context, dialog, pcs).getView();
				dialog.setContentView(view);
				dialog.show();
				
				super.handleMessage(msg);
			}
			
		};
		
		new Thread(new Runnable() {
			public void run() {
				try {
					SearchPc.connServer(ipAddr);
					List<FindedPC> pcs = SearchPc.receive();
					Message msg = new Message();
					msg.obj = pcs;
					msg.setTarget(searchHandler);
					msg.sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		FunctionActivity.pd.showPromptDialog(R.string.please_wait);
	}
}
