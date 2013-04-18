package com.wifiplayer.activitys;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wifiplayer.R;
import com.wifiplayer.activitys.utils.PcOpManager;
import com.wifiplayer.activitys.utils.PromptDialog;
import com.wifiplayer.activitys.views.EnableCtrPcListView;
import com.wifiplayer.adapters.FileListAdapter;
import com.wifiplayer.bean.ReqReplyOp;
import com.wifiplayer.bean.myCtrlView.MyImageViewButton;
import com.wifiplayer.bean.packages.Head;
import com.wifiplayer.net.tcp.ConnServer;
import com.wifiplayer.net.udp.SearchPc;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主功能界面
 * 
 * @author chenkaigang
 * 
 */
public class FunctionActivity extends Activity implements View.OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	private MyImageViewButton shareBtn;// 分享按钮
	private MyImageViewButton recommendBtn;// 推荐软件按钮
	private MyImageViewButton connPCBtn;// 连接电脑按钮
	private MyImageViewButton mainDirBtn;// 主目录按钮
	private ListView dirLv;// 文件目录列表

	private Context context = FunctionActivity.this;
	
	MyBroadCastReceiver br = null;
	public static PromptDialog pd = null;
	
	public static String currDir = null;
	
	public static Handler downLoadHandler = null;

	Handler fileHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) { 
			super.handleMessage(msg);
			JSONArray files =  (JSONArray) msg.obj;
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
		requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_function);
		
		pd = new PromptDialog(context);
		init();// 初始化控件
		// loadData();// 初始化数据
		/* 设置按钮监听事件 */
		shareBtn.setOnClickListener(this);
		recommendBtn.setOnClickListener(this);
		connPCBtn.setOnClickListener(this);
		mainDirBtn.setOnClickListener(this);

		dirLv.setOnItemClickListener(this);
		dirLv.setOnItemLongClickListener(this);

		br = new MyBroadCastReceiver();
		registerBroadCastReceiver(br);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		finish();
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(br);
		ConnServer.close();
		System.exit(0);
		super.onDestroy();
	}
	
	
	/**
	 * 注册一个广播监听器
	 */
	private void registerBroadCastReceiver(MyBroadCastReceiver br) {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.wifiplayer.activitys.FunctionActivity");
		registerReceiver(br, intentFilter);
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		shareBtn = (MyImageViewButton) findViewById(R.id.shareButton);
		recommendBtn = (MyImageViewButton) findViewById(R.id.recommendButton);
		connPCBtn = (MyImageViewButton) findViewById(R.id.connPCButton);
		mainDirBtn = (MyImageViewButton) findViewById(R.id.mainDirButton);
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
		PcOpManager.openMainDir(context, false);
	}

	/**
	 * 连接电脑按钮点击事件
	 */
	private void connPCBtnClick() {
		final Dialog dialog = new Dialog(context, R.style.no_title_dialog);
		View view = LayoutInflater.from(context).inflate(R.layout.view_conn_pc_btn, null);
		dialog.setContentView(view);
		dialog.show();
		
		LinearLayout searchPcBtn = (LinearLayout) view.findViewById(R.id.searchPcButton);//搜索局域网内可以控制的电脑
		LinearLayout connPcUseIPBtn = (LinearLayout) view.findViewById(R.id.connPcUseIPButton);//连接指定ip的电脑
		LinearLayout cancelBtn = (LinearLayout) view.findViewById(R.id.cancelButton);//取消按钮
		
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
		
		LinearLayout sinaWeiBoShareBtn = (LinearLayout) view.findViewById(R.id.sinaWeiBoShareLinearLayout);
		LinearLayout txWeiBoShareBtn = (LinearLayout) view.findViewById(R.id.txShareLinearLayout);
		LinearLayout emailShareBtn = (LinearLayout) view.findViewById(R.id.emailShareLinearLayout);
		LinearLayout cancelBtn = (LinearLayout) view.findViewById(R.id.cancelLinearLayout);
		
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
		JSONObject pf = (JSONObject) arg0.getAdapter().getItem(arg2);
		
		/*判断用户点击的是否为上一页选项*/
		try {
			if (arg2 == 0 && !pf.getBoolean("sys")) {
				try {
					if (currDir == null) {
						Toast.makeText(context, "已到主目录", Toast.LENGTH_LONG).show();
						return;
					}
					currDir = currDir.substring(0, currDir.lastIndexOf("\\"));
					PcOpManager.openDir(currDir, context);
				} catch (Exception e) {
					PcOpManager.openMainDir(context,false);
				}
				return;
			}
			String path = pf.getString("path");
			/*点击正常目录的时候*/
			// 判断是否是文件夹
			if (pf.getBoolean("dir")) {// 是文件夹
				currDir = path;
				PcOpManager.openDir(path, context);
			} else {
				if (pf.getBoolean("sys")) {//是系统分区
					PcOpManager.openDir(pf.getString("name"), context);
					currDir = pf.getString("name");
				} else {
					PcOpManager.openFile(context, path);//打开文件
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		final JSONObject pf = (JSONObject) arg0.getAdapter().getItem(arg2);
		
		View view = LayoutInflater.from(context).inflate(R.layout.view_file_item_logn_click, null);
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
				try {
					PcOpManager.openFile(context, pf.getString("path"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		delFileBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				final Dialog askDialog = new Dialog(context, R.style.no_title_dialog);
				View askView = LayoutInflater.from(context).inflate(R.layout.view_ask_del_file_op_dialog, null);
				askDialog.setContentView(askView);
				askDialog.show();
				Button okBtn = (Button) askView.findViewById(R.id.okBtn); 
				Button cancelBtn = (Button) askView.findViewById(R.id.cancelBtn); 
				
				okBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						askDialog.cancel();
						try {
							PcOpManager.delFile(context, pf.getString("path"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
				
				cancelBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						askDialog.cancel();
					}
				});
				
				
			}
		});
		/*将电脑上的文件拷贝到手机上*/
		cope2PhoneFileBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				try {
					
					final Dialog downLoadDialog = new Dialog(context, R.style.no_title_dialog);
					View downLoadView = LayoutInflater.from(context).inflate(R.layout.copy_file_2_phone_process, null);
					downLoadDialog.setContentView(downLoadView);
					downLoadDialog.show();
					
					final TextView fileNameTextView = (TextView) downLoadView.findViewById(R.id.fileNameTextView);//显示当前拷贝d文件名称
					final ProgressBar downProgressBar = (ProgressBar) downLoadView.findViewById(R.id.downProgressBar);
					final TextView currentProgressTextView = (TextView) downLoadView.findViewById(R.id.currentProgressTextView);//当前下载d位置
					final TextView totalProgressTextView = (TextView) downLoadView.findViewById(R.id.totalProgressTextView);//总共需要下载长度
					final Button okBtn = (Button) downLoadView.findViewById(R.id.okBtn);
					
					okBtn.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							downLoadDialog.cancel();
						}
					});
					
					downLoadHandler = new Handler() {
						
						@Override
						public void handleMessage(Message msg) {
							HashMap<String,String> map = (HashMap<String, String>) msg.obj;
							String total = map.get("total");//文件总长度
							String curr = map.get("curr");//当前下载的长度
							
							fileNameTextView.setText(getResources().getString(R.string.copying_file) + PcOpManager.copyFileName);
							totalProgressTextView.setText(total + "MB");
							currentProgressTextView.setText(curr + "MB");
							
							
							
							downProgressBar.setMax(new Integer(total));//设置progressBar的长度
							downProgressBar.setProgress(new Integer(curr));
							if (total.equals(curr)) {
								okBtn.setVisibility(View.VISIBLE);
							}
							super.handleMessage(msg);
						}
					};
					PcOpManager.copyFile2Phone(context, pf.getString("path"));
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
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
				pd.closePromptDialog();
				List<DatagramPacket> pcs = (List<DatagramPacket>) msg.obj;
				if (pcs.isEmpty()) {
					Toast.makeText(context, "没有可以控制的电脑", Toast.LENGTH_LONG).show();
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
		
		pd.showPromptDialog(R.string.please_wait);
	}
	
	class MyBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			pd.closePromptDialog();
			ReqReplyOp rro = (ReqReplyOp) intent.getSerializableExtra("rro");
			short cmd = rro.getCmd();
			switch (cmd) {
			case Head.CONN_SERVER_REPLY:
				connServerReply(rro);
				break;
			case Head.OPEN_DIR_REPLY:
				connServerReply(rro);
				break;
			case Head.DEL_FILE_REPLY:
				connServerReply(rro);
				break;
			case Head.OPEN_FILE_REPLY:
				showSuccess(rro);
				break;
			case Head.COPY_FILE_2_PHONE_REPLY:
				copyFileReply(rro);
				break;

			default:
				break;
			}
		}

		/**
		 * 拷贝文件返回信息
		 * @param rro
		 */
		private void copyFileReply(ReqReplyOp rro) {
			
			Toast.makeText(context, rro.getContent(), Toast.LENGTH_LONG).show();
		}

		/**
		 * 显示成功打开文件的信息
		 * @param rro 
		 */
		private void showSuccess(ReqReplyOp rro) {
			Log.i("receive", "播放文件返回" + rro.getContent());
			if (rro.getContent().equals("true")) {
				Toast.makeText(context, "播放成功!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(context, "播放失败，没有找到相应的音乐播放器!", Toast.LENGTH_LONG).show();
			}
		}

		/**
		 * 控制返回内容处理
		 * @param rro
		 */
		private void connServerReply(ReqReplyOp rro) {
			int status = rro.getStatus();
			
			if (status == 0) {//操作成功
				try {
					JSONArray jsonArr = new JSONArray(rro.getContent());
					Message msg = new Message();
					msg.obj = jsonArr;
					msg.setTarget(fileHandler);
					msg.sendToTarget();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}
	

}
