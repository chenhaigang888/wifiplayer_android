package com.wifiplayer.activitys;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

import com.wifiplayer.R;
import com.wifiplayer.activitys.views.EnableCtrPcListView;
import com.wifiplayer.adapters.EnableCtrlPCAdapter;
import com.wifiplayer.adapters.FileListAdapter;
import com.wifiplayer.bean.PcFile;
import com.wifiplayer.net.udp.SearchPc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 主功能界面
 * 
 * @author chenkaigang
 * 
 */
public class FunctionActivity extends Activity implements View.OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	private Button shareBtn;// 分享按钮
	private Button recommendBtn;// 推荐软件按钮
	private Button connPCBtn;// 连接电脑按钮
	private Button mainDirBtn;// 主目录按钮
	private ListView dirLv;// 文件目录列表

	private Context context = FunctionActivity.this;

	Handler fileHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			List<PcFile> files = (List<PcFile>) msg.obj;
			FileListAdapter adapter = new FileListAdapter(
					context,
					files,
					R.layout.view_listview_item,
					new int[] { R.id.fileImageView, R.id.fileNameTextView,
							R.id.fileSizeTextView, R.id.fileCreateDateTextView });
			dirLv.setAdapter(adapter);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function);

		init();// 初始化控件
		// loadData();// 初始化数据
		/* 设置按钮监听事件 */
		shareBtn.setOnClickListener(this);
		recommendBtn.setOnClickListener(this);
		connPCBtn.setOnClickListener(this);
		mainDirBtn.setOnClickListener(this);

		dirLv.setOnItemClickListener(this);
		dirLv.setOnItemLongClickListener(this);

	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		List<PcFile> files = new ArrayList<PcFile>();
		PcFile pf = null;
		pf = new PcFile();
		pf.setName("上一级目录");
		pf.setDir(true);
		files.add(pf);
		for (int i = 0; i < 50; i++) {
			pf = new PcFile("文件" + i, "", i % 2 == 0 ? true : false, "10MB", null);
			files.add(pf);
		}

		Message msg = new Message();
		msg.obj = files;
		msg.setTarget(fileHandler);
		msg.sendToTarget();
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		shareBtn = (Button) findViewById(R.id.shareButton);
		recommendBtn = (Button) findViewById(R.id.recommendButton);
		connPCBtn = (Button) findViewById(R.id.connPCButton);
		mainDirBtn = (Button) findViewById(R.id.mainDirButton);
		dirLv = (ListView) findViewById(R.id.dirListView);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.shareButton:
			shareBtnClick();
			break;
		case R.id.recommendButton:
			recommendBtnClick();
			break;
		case R.id.connPCButton:
			connPCBtnClick();
			break;
		case R.id.mainDirButton:
			mainDirBtnClick();
			break;

		default:
			break;
		}
	}

	/**
	 * 主目录按钮点击事件
	 */
	public void mainDirBtnClick() {
		showMainDir();
	}

	/**
	 * 展示主目录内容
	 */
	private void showMainDir() {
		List<PcFile> files = new ArrayList<PcFile>();
		PcFile pf = null;

		pf = new PcFile("C", "", true, "50GB", null);
		files.add(pf);
		pf = new PcFile("D", "", true, "60GB", null);
		files.add(pf);
		pf = new PcFile("E", "", true, "80GB", null);
		files.add(pf);
		pf = new PcFile("F", "", true, "120GB", null);
		files.add(pf);
		Message msg = new Message();
		msg.obj = files;
		msg.setTarget(fileHandler);
		msg.sendToTarget();

	}

	/**
	 * 连接电脑按钮点击事件
	 */
	private void connPCBtnClick() {
		final Dialog dialog = new Dialog(context, R.style.no_title_dialog);
		View view = LayoutInflater.from(context).inflate(R.layout.view_conn_pc_btn, null);
		dialog.setContentView(view);
		dialog.show();
		
		Button searchPcBtn = (Button) view.findViewById(R.id.searchPcButton);//搜索局域网内可以控制的电脑
		Button connPcUseIPBtn = (Button) view.findViewById(R.id.connPcUseIPButton);//连接指定ip的电脑
		Button cancelBtn = (Button) view.findViewById(R.id.cancelButton);//取消按钮
		
		searchPcBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				search();
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
		
		okBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				
			}
		});
		
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				
			}
		});
		
	}

	/**
	 * 推荐软件按钮点击事件
	 */
	private void recommendBtnClick() {
		// TODO Auto-generated method stub

	}

	/**
	 * 分享按钮点击事件
	 */
	private void shareBtnClick() {
		final Dialog dialog = new Dialog(context, R.style.no_title_dialog);
		View view = LayoutInflater.from(context).inflate(R.layout.view_share_view, null);
		dialog.setContentView(view);
		dialog.show();
		
		Button sinaWeiBoShareBtn = (Button) view.findViewById(R.id.sinaWeiBoShareButton);
		Button txWeiBoShareBtn = (Button) view.findViewById(R.id.txWeiBoShareButton);
		Button emailShareBtn = (Button) view.findViewById(R.id.emailShareButton);
		Button cancelBtn = (Button) view.findViewById(R.id.cancelButton);
		
		sinaWeiBoShareBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				
			}
		});
		
		txWeiBoShareBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				
			}
		});
		
		emailShareBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				
			}
		});
		
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		PcFile pf = (PcFile) arg0.getAdapter().getItem(arg2);
		// 判断是否是文件夹
		if (pf.isDir()) {// 是文件夹
			if (pf.getName().equals("上一级目录")) {
				showMainDir();
			} else {// 不是文件夹
				loadData();
			}
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_file_item_logn_click, null);
		final Dialog dialog = new Dialog(context, R.style.no_title_dialog);
		dialog.setContentView(view);
		dialog.show();

		Button openFileBtn = (Button) view.findViewById(R.id.openFileButton);//打开文件
		Button delFileBtn = (Button) view.findViewById(R.id.delFileButton);//删除文件
		Button cope2PhoneFileBtn = (Button) view.findViewById(R.id.cope2PhoneFileButton);//将文件拷贝到手机
		Button cancelBtn = (Button) view.findViewById(R.id.cancelButton);//取消按钮
		
		openFileBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				Toast.makeText(context, "此功能暂时未完成,敬请期待！", Toast.LENGTH_LONG).show();
			}
		});

		delFileBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				Toast.makeText(context, "此功能暂时未完成,敬请期待！", Toast.LENGTH_LONG).show();
			}
		});
		
		cope2PhoneFileBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				Toast.makeText(context, "此功能暂时未完成,敬请期待！", Toast.LENGTH_LONG).show();
			}
		});
		
		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		return true;
	}
	
	/**
	 * 搜索局域网内可以控制的电脑
	 */
	private void search() {
		final Handler searchHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				List<DatagramPacket> pcs = (List<DatagramPacket>) msg.obj;
				if (pcs.isEmpty()) {
					Toast.makeText(context, "没有可以控制的电脑", Toast.LENGTH_LONG).show();
					return;
				}
				Dialog dialog = new Dialog(context);
				View view = new EnableCtrPcListView(context, dialog, pcs).getView();
				dialog.setContentView(view);
				dialog.show();
				
				super.handleMessage(msg);
			}
			
		};
		
		new Thread(new Runnable() {
			public void run() {
				try {
					SearchPc.connServer();
					List<DatagramPacket> pcs = SearchPc.receive();
					Message msg = new Message();
					msg.obj = pcs;
					msg.setTarget(searchHandler);
					msg.sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	

}
