package com.wifiplayer.activitys;

import java.io.IOException;

import net.miidi.ad.banner.AdBannerManager;
import net.miidi.wall.AdWall;
import net.miidi.wall.AdWallManager;
import net.miidi.wall.IAdWallShowAppsNotifier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.webview.OAuthV2AuthorizeWebView;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.net.RequestListener;

import com.wifiplayer.R;
import com.wifiplayer.activitys.utils.PcOpManager;
import com.wifiplayer.activitys.utils.PromptDialog;
import com.wifiplayer.activitys.utils.SendBroadCastUtil;
import com.wifiplayer.activitys.views.ConnPc;
import com.wifiplayer.activitys.views.FileOpView;
import com.wifiplayer.activitys.views.ShareView;
import com.wifiplayer.activitys.views.TxWeiboView;
import com.wifiplayer.adapters.FileListAdapter;
import com.wifiplayer.bean.ReqReplyOp;
import com.wifiplayer.bean.myCtrlView.MyImageViewButton;
import com.wifiplayer.bean.packages.Head;
import com.wifiplayer.net.tcp.ConnServer;
import com.wifiplayer.net.tcp.server.ServerManager;
import com.wifiplayer.net.udp.SearchPc;
import com.wifiplayer.net.udp.UdpServer;

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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 主功能界面
 * 
 * @author chenkaigang
 * 
 */
public class FunctionActivity extends Activity implements View.OnClickListener,
		OnItemClickListener, OnItemLongClickListener, IAdWallShowAppsNotifier  {

	private MyImageViewButton shareBtn;// 分享按钮
	private MyImageViewButton recommendBtn;// 推荐软件按钮
	private MyImageViewButton connPCBtn;// 连接电脑按钮
	private MyImageViewButton mainDirBtn;// 主目录按钮
	private ListView dirLv;// 文件目录列表

	private Context context = FunctionActivity.this;
	
	MyBroadCastReceiver br = null;
	public static PromptDialog pd = null;
	public static String currDir = null;
	
	
//	public static Oauth2AccessToken accessToken;
	public static final int SINA_WEIBO_SHARE_SUCC = 0x2000;//新浪微博分享成功
	public static final int SINA_WEIBO_SHARE_FAIL = 0x2001;//新浪微博分享失败
	
	public static final int TX_WEIBO_SHARE_SUCC = 0x2101;//腾讯微博分享成功
	public static final int TX_WEIBO_SHARE_FAIL = 0x2102;//腾讯微博分享成功
	
  private OAuthV2 oAuth;
	
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
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_function);
		
//		accessToken = AccessTokenKeeper.readAccessToken(this);
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

		/*米迪广告*/
		AdBannerManager.init(context, "5007", "qqmtacmjf678r4cn", true);
		AdWallManager.init(this, "5007", "qqmtacmjf678r4cn", true);
		
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
		View view = new ConnPc(context, dialog).getView();
		dialog.setContentView(view);
		dialog.show();
	}
	

	/**
	 * 推荐软件按钮点击事件
	 */
	private void recommendBtnClick() {
		AdWall.showAppOffers(this);
	}

	/**
	 * 分享按钮点击事件
	 */
	private void shareBtnClick() {
		final Dialog dialog = new Dialog(context, R.style.no_title_dialog);
		View view = new ShareView(context, dialog).getView();
		dialog.setContentView(view);
		dialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		openFile(arg0, arg2);//打开文件 
	}

	/**
	 * 单击事件
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public void openFile(AdapterView<?> arg0, int arg2) {
		/*判断用户点击的是否为上一页选项*/
		try {
			JSONObject pf = (JSONObject) arg0.getAdapter().getItem(arg2);
			if (arg2 == 0 && !pf.getString("sys").equals("true")) {
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
			if (pf.getString("dir").equals("true")) {// 是文件夹
				currDir = path;
				PcOpManager.openDir(path, context);
			} else {
				if (pf.getString("sys").equals("true")) {//是系统分区
					PcOpManager.openDir(pf.getString("name"), context);
					currDir = pf.getString("name");
				} else {
					PcOpManager.openFile(context, path);//打开文件
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onItemLongClick(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {
		final JSONObject pf = (JSONObject) arg0.getAdapter().getItem(arg2);
		final Dialog dialog = new Dialog(context, R.style.no_title_dialog);
		View view = new FileOpView(context, dialog, pf, arg0, arg2).getView();
		dialog.setContentView(view);
		dialog.show();
		return true;
	}
	
	/**
	 * 广播接收器
	 * @author Administrator
	 *
	 */
	class MyBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			pd.closePromptDialog();
			ReqReplyOp rro = (ReqReplyOp) intent.getSerializableExtra("rro");
			int cmd = rro.getCmd();
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
				Toast.makeText(context, R.string.share_succ, Toast.LENGTH_LONG).show();
				break;
			case SINA_WEIBO_SHARE_FAIL:
				Toast.makeText(context, R.string.share_fail, Toast.LENGTH_LONG).show();
				break;
			case TX_WEIBO_SHARE_SUCC:
				Toast.makeText(context, R.string.share_succ, Toast.LENGTH_LONG).show();
				break;
			case TX_WEIBO_SHARE_FAIL:
				Toast.makeText(context, R.string.share_fail, Toast.LENGTH_LONG).show();
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

	/**
	 * 分享到新浪微博
	 * @param content
	 * @param picPath
	 * @throws WeiboException
	 */
	public void share2weibo(String content,Oauth2AccessToken accessToken) throws WeiboException {
//		accessToken = AccessTokenKeeper.readAccessToken(this);
    	StatusesAPI sapi = new StatusesAPI(accessToken);
    	
    	sapi.update(content, null, null, new RequestListener() {
			
			@Override
			public void onIOException(IOException arg0) {
				Log.i("receive", "--------onIOException--------");
				ReqReplyOp rro = new ReqReplyOp();
				rro.setCmd(SINA_WEIBO_SHARE_FAIL);
				SendBroadCastUtil.sendBroadCast(context, rro);
			}
			
			@Override
			public void onError(WeiboException arg0) {
				Log.i("receive", "--------onError--------");
				ReqReplyOp rro = new ReqReplyOp();
				rro.setCmd(SINA_WEIBO_SHARE_FAIL);
				SendBroadCastUtil.sendBroadCast(context, rro);
			}
			
			@Override
			public void onComplete(String arg0) {
				Log.i("receive", "--------onComplete--------");
				ReqReplyOp rro = new ReqReplyOp();
				rro.setCmd(SINA_WEIBO_SHARE_SUCC);
				SendBroadCastUtil.sendBroadCast(context, rro);
			}
		});
		
    }

	@Override
	public void onDismissApps() {
		
	}

	@Override
	public void onShowApps() {
		
	}
	
	 /*
     * 通过读取OAuthV2AuthorizeWebView返回的Intent，获取用户授权信息
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)   {
        if (requestCode == 2) {
            if (resultCode==OAuthV2AuthorizeWebView.RESULT_CODE) {
                oAuth=(OAuthV2) data.getExtras().getSerializable("oauth");
                if(oAuth.getStatus() == 0)
                    Toast.makeText(getApplicationContext(), R.string.a_succ, Toast.LENGTH_SHORT).show();
                
                final Dialog dialog = new Dialog(context,R.style.no_title_dialog);
                View view = new TxWeiboView(context, dialog, oAuth).getView();
                dialog.setContentView(view);
                dialog.show();
            }
        }
    }

}
