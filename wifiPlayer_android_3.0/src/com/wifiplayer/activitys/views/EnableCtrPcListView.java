package com.wifiplayer.activitys.views;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.UnknownHostException;
import java.util.List;

import com.wifiplayer.R;
import com.wifiplayer.activitys.FunctionActivity;
import com.wifiplayer.adapters.EnableCtrlPCAdapter;
import com.wifiplayer.net.tcp.ConnServer;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 可以控制的pc视图
 * @author Administrator
 *
 */
public class EnableCtrPcListView implements View.OnClickListener, OnItemClickListener{

	private Context context;
	private Dialog dialog;
	private List<DatagramPacket> pcs;
//	private Handler handler;
	
	public EnableCtrPcListView(Context context, Dialog dialog,
			List<DatagramPacket> pcs/*, Handler handler*/) {
		super();
		this.context = context;
		this.dialog = dialog;
		this.pcs = pcs;
//		this.handler = handler;
	}



	public View getView() {
		
		/*初始化控件*/
		View view = LayoutInflater.from(context).inflate(R.layout.view_pc_list, null);
		ListView lv = (ListView) view.findViewById(R.id.pcListListView);
		Button btn = (Button) view.findViewById(R.id.cancelButton);
		
		EnableCtrlPCAdapter adapter = new EnableCtrlPCAdapter(context, pcs, new int[]{R.id.pcNameTextview}, R.layout.view_pc_list_item);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(this);
		btn.setOnClickListener(this);
		return view;
		
	}



	@Override
	public void onClick(View v) {
		dialog.cancel();
		
	}



	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		dialog.cancel();
//		FunctionActivity activity = (FunctionActivity)context;
//		activity.mainDirBtnClick();
		DatagramPacket pc = (DatagramPacket) arg0.getAdapter().getItem(arg2);
		ConnServer.conn(pc.getAddress());
		ConnServer.send("我是android".getBytes());
	}
}
