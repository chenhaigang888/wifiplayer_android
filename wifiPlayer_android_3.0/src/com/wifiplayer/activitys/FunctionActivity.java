package com.wifiplayer.activitys;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.net.RequestListener;

import com.wifiplayer.R;
import com.wifiplayer.activitys.utils.PcOpManager;
import com.wifiplayer.activitys.utils.PromptDialog;
import com.wifiplayer.activitys.utils.SendBroadCastUtil;
import com.wifiplayer.activitys.views.EnableCtrPcListView;
import com.wifiplayer.activitys.views.Share2WeiBoView;
import com.wifiplayer.adapters.FileListAdapter;
import com.wifiplayer.bean.FindedPC;
import com.wifiplayer.bean.ReqReplyOp;
import com.wifiplayer.bean.myCtrlView.MyImageViewButton;
import com.wifiplayer.bean.packages.Head;
import com.wifiplayer.net.tcp.ConnServer;
import com.wifiplayer.net.tcp.server.ServerManager;
import com.wifiplayer.net.udp.SearchPc;
import com.wifiplayer.net.udp.UdpServer;
import com.wifiplayer.utils.AccessTokenKeeper;
import com.wifiplayer.utils.IPAddressTool;
import com.wifiplayer.utils.ReadDirectoryFile;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.EditText;
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
	
	private Weibo mWeiBo;//sina微博
	public static Oauth2AccessToken accessToken;
	public static final short SINA_WEIBO_SHARE_SUCC = 0x2000;//新浪微博分享成功
	public static final short SINA_WEIBO_SHARE_FAIL = 0x2001;//新浪微博分享失败

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
		
		accessToken = AccessTokenKeeper.readAccessToken(this);
		
		pd = new PromptDialog(context);
		init();// 初始化控件
		// loadData();// 初始化数据
		/* 设置按钮监听事件 */
		shareBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				shareBtnClick();
			}
		});
		recommendBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				recommendBtnClick();
			}
		});
		connPCBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				connPCBtnClick();
			}
		});
		mainDirBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainDirBtnClick();
			}
		});

		dirLv.setOnItemClickListener(this);
		dirLv.setOnItemLongClickListener(this);

		br = new MyBroadCastReceiver();
		registerBroadCastReceiver(br);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ServerManager sm = new ServerManager();
				sm.startServer();
				new UdpServer().start();
				
			}
		}).start();
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
//		int id = v.getId();
//		switch (id) {
//		case R.id.shareButton:
//			shareBtnClick();
//			break;
//		case R.id.recommendButton:
//			recommendBtnClick();
//			break;
//		case R.id.connPCButton:
//			connPCBtnClick();
//			break;
//		case R.id.mainDirButton:
//			mainDirBtnClick();
//			break;
//
//		default:
//			break;
//		}
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
		final EditText ipAddrEditText = (EditText) view.findViewById(R.id.ipAddrEditText);
		
		okBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				try {
					String ipAddr = ipAddrEditText.getText().toString();
					boolean isIP = IPAddressTool.isIPAdress(ipAddr);
					
					if (!isIP) {
						Log.i("receive", "非法ip地址");
						ipAddrEditText.setText("你输入的似乎不像是IP地址！");
						return;
					}
					PcOpManager.connServer(ipAddrEditText.getText().toString(), context);
					PcOpManager.openMainDir(context, false);
					dialog.cancel();
				} catch (UnknownHostException e) {
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
		
	}

	/**
	 * 推荐软件按钮点击事件
	 */
	private void recommendBtnClick() {
		ReadDirectoryFile.listFile("/mnt/sdcard/");

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
				
				if (accessToken != null) {
					Dialog share_dialog = new Dialog(context, R.style.no_title_dialog);
					View view = new Share2WeiBoView(context, share_dialog).getView();
					share_dialog.setContentView(view);
					share_dialog.show();
				} else {
					mWeiBo = Weibo.getInstance("308491239", "http://www.xn--yeto9bx06i.com");
					mWeiBo.authorize(context, new AuthDialogListener());
				}
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
				Intent i = new Intent(Intent.ACTION_SEND); 
//            	i.setType("text/plain"); //模拟器请使用这行
            	i.setType("message/rfc822") ; // 真机上使用这行
//            	i.putExtra(Intent.EXTRA_EMAIL, new String[]{"chg4951674@163.com"}); 
            	i.putExtra(Intent.EXTRA_SUBJECT,"wifiPlayer电脑端！"); 
            	i.putExtra(Intent.EXTRA_TEXT,"我要获得wifiPlayer电脑端！"); 
            	context.startActivity(Intent.createChooser(i, "Select email application."));
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
		openFile(arg0, arg1, arg2, arg3);//打开文件 
	}

	/**
	 * 单击事件
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	private void openFile(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		/*判断用户点击的是否为上一页选项*/
		try {
			JSONObject pf = (JSONObject) arg0.getAdapter().getItem(arg2);
			Log.i("chg", "pf:" + pf);

			if (arg2 == 0 && !pf.getBoolean("sys")) {
				try {
					if (currDir == null) {
						Toast.makeText(context, R.string.already_2_main_dir, Toast.LENGTH_LONG).show();
						return;
					}
					if (SearchPc.os.equals("Mac OS X")) {
						currDir = currDir.substring(0, currDir.lastIndexOf("/"));
					} else if(SearchPc.os.equals("android")) {
						currDir = currDir.substring(0, currDir.lastIndexOf("/"));
					} else {
						currDir = currDir.substring(0, currDir.lastIndexOf("\\"));
					}
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
	public boolean onItemLongClick(final AdapterView<?> arg0, final View arg1, final int arg2,
			final long arg3) {
		final JSONObject pf = (JSONObject) arg0.getAdapter().getItem(arg2);
		
		View view = LayoutInflater.from(context).inflate(R.layout.view_file_item_logn_click, null);
		final Dialog dialog = new Dialog(context, R.style.no_title_dialog);
		dialog.setContentView(view);
		dialog.show();

		LinearLayout openFileBtn = (LinearLayout) view.findViewById(R.id.openFileButton);//打开文件
		LinearLayout delFileBtn = (LinearLayout) view.findViewById(R.id.delFileButton);//删除文件
		LinearLayout cope2PhoneFileBtn = (LinearLayout) view.findViewById(R.id.cope2PhoneFileButton);//将文件拷贝到手机
		LinearLayout cancelBtn = (LinearLayout) view.findViewById(R.id.cancelButton);//取消按钮
		
		openFileBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				openFile(arg0, arg1, arg2, arg3);
			}
		});

		delFileBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				try {
					if (pf.getBoolean("sys")) {
						Toast.makeText(context, R.string.del_err, Toast.LENGTH_LONG).show();
						return;
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
					if (!SearchPc.os.equals("android")) {
						if (pf.getBoolean("sys")) {
							Toast.makeText(context, R.string.copy_err, Toast.LENGTH_LONG).show();
							return;
						}
					}
					
					if (pf.getBoolean("dir")) {
						Toast.makeText(context, R.string.copy_err, Toast.LENGTH_LONG).show();
						return;
					}
				
					final Dialog downLoadDialog = new Dialog(context, R.style.no_title_dialog);
					View downLoadView = LayoutInflater.from(context).inflate(R.layout.copy_file_2_phone_process, null);
					downLoadDialog.setContentView(downLoadView);
					downLoadDialog.show();
					
					final TextView fileNameTextView = (TextView) downLoadView.findViewById(R.id.fileNameTextView);//显示当前拷贝d文件名称
					final ProgressBar downProgressBar = (ProgressBar) downLoadView.findViewById(R.id.downProgressBar);
					final TextView currentProgressTextView = (TextView) downLoadView.findViewById(R.id.currentProgressTextView);//当前下载d位置
					final TextView totalProgressTextView = (TextView) downLoadView.findViewById(R.id.totalProgressTextView);//总共需要下载长度
					final Button okBtn = (Button) downLoadView.findViewById(R.id.okBtn);
					
					downProgressBar.setMax(100);//设置progressBar的长度
					
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
							
							double totalD = new Double(total);
							double currD = new Double(curr);
							int p = (int)(currD/totalD * 100);
							downProgressBar.setProgress(p);
							
							String totalUnit = String.format("%.2f", js(new Double(total))) + strKB[second];//文件总长度单位
							second = 0;
							String currUnit = String.format("%.2f", js(new Double(curr))) + strKB[second];//当前下载的长度单位
							Log.i("receive", "currUnit:" + currUnit);
							
							fileNameTextView.setText(getResources().getString(R.string.copying_file) + PcOpManager.copyFileName);
							totalProgressTextView.setText(totalUnit);
							currentProgressTextView.setText(currUnit);
							
							if (total.equals(curr)) {
								okBtn.setVisibility(View.VISIBLE);
							}
							second = 0;
							super.handleMessage(msg);
						}

						/**
						 * 字符串转成int
						 * @param total
						 * @return
						 */
						private int double2Int(String total) {
							total = total.substring(0, total.indexOf("."));
							return new Integer(total);
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
					SearchPc.connServer();
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
				delFileReply(rro);
				break;
			case Head.OPEN_FILE_REPLY:
				showSuccess(rro);
				break;
			case Head.COPY_FILE_2_PHONE_REPLY:
				copyFileReply(rro);
				break;
			case SINA_WEIBO_SHARE_SUCC:
				Toast.makeText(context, "分享成功！", Toast.LENGTH_LONG).show();
				break;
			case SINA_WEIBO_SHARE_FAIL:
				Toast.makeText(context, "分享失败！", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}

		/**
		 * 删除文件返回，不过此处是删除失败的信息，因为删除成功后直接返回了OPEN_DIR指令
		 * @param rro
		 */
		private void delFileReply(ReqReplyOp rro) {
			Toast.makeText(context, R.string.del_file_err, Toast.LENGTH_LONG).show();
			
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
			if (rro.getContent().equals("true")) {
				Toast.makeText(context, R.string.play_succ, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(context, R.string.play_fail, Toast.LENGTH_LONG).show();
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
				
			} else {
				Toast.makeText(context, rro.getContent(), Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	double fileD;
	String[] strKB = {"B","KB","MB","GB","TB","PB"};
	 int second = 0;//计算单位换算了多少次
	public double js(double fileLenth) {
		if (fileLenth >= 1024) {
			fileD = fileLenth/ 1024;
			second ++;
			js(fileD);
		}
		return fileD;
		
	}
	/**
	 * 新浪微博认证
	 * @author Administrator
	 *
	 */
	class AuthDialogListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            accessToken = new Oauth2AccessToken(token, expires_in);
            if (accessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(accessToken.getExpiresTime()));
//                mText.setText("认证成功: \r\n access_token: " + token + "\r\n" + "expires_in: " + expires_in + "\r\n有效期：" + date);
                try {
                    Class sso = Class
                            .forName("com.weibo.sdk.android.api.WeiboAPI");// 如果支持weiboapi的话，显示api功能演示入口按钮
                } catch (ClassNotFoundException e) {
                    // e.printStackTrace();
//                    Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");
                }
//                cancelBtn.setVisibility(View.VISIBLE);
                AccessTokenKeeper.keepAccessToken(context, accessToken);
                Toast.makeText(context, "认证成功", Toast.LENGTH_SHORT).show();

                try {
					share2weibo("认证地方发送");
				} catch (WeiboException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        
        

        @Override
        public void onError(WeiboDialogError e) {
            Toast.makeText(getApplicationContext(),  "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "Auth cancel", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(getApplicationContext(),  "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
	/**
	 * 分享到新浪微博
	 * @param content
	 * @param picPath
	 * @throws WeiboException
	 */
	public void share2weibo(String content) throws WeiboException {
    	StatusesAPI sapi = new StatusesAPI(accessToken);
    	
    	sapi.update(content, null, null, new RequestListener() {
			
			@Override
			public void onIOException(IOException arg0) {
				System.out.println("----------IOException-----------" + arg0.getStackTrace());
				ReqReplyOp rro = new ReqReplyOp();
				rro.setCmd(SINA_WEIBO_SHARE_FAIL);
				SendBroadCastUtil.sendBroadCast(context, rro);
			}
			
			@Override
			public void onError(WeiboException arg0) {
				System.out.println("----------WeiboException-----------" + arg0.getStatusCode());
				ReqReplyOp rro = new ReqReplyOp();
				rro.setCmd(SINA_WEIBO_SHARE_FAIL);
				SendBroadCastUtil.sendBroadCast(context, rro);
			}
			
			@Override
			public void onComplete(String arg0) {
				Log.i("receive", "新浪微博分享成功");
				ReqReplyOp rro = new ReqReplyOp();
				rro.setCmd(SINA_WEIBO_SHARE_SUCC);
				SendBroadCastUtil.sendBroadCast(context, rro);
			}
		});
		
    }

}
