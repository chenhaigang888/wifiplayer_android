package com.wifiplayer.activitys.views;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.wifiplayer.R;
import com.wifiplayer.activitys.FunctionActivity;
import com.wifiplayer.activitys.utils.PcOpManager;
import com.wifiplayer.net.udp.SearchPc;
import com.wifiplayer.utils.Tools;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 文件操作视图
 * @author chenkaigang
 *
 */
public class FileOpView {

	private Context context;
	private Dialog dialog;
	private JSONObject pf;
	private AdapterView<?> arg0;
	private int arg2;
	
	public static Handler downLoadHandler = null;
	
	public FileOpView(Context context, Dialog dialog, JSONObject pf,
			AdapterView<?> arg0, int arg2) {
		super();
		this.context = context;
		this.dialog = dialog;
		this.pf = pf;
		this.arg0 = arg0;
		this.arg2 = arg2;
		MediaPlayer mp = new MediaPlayer();
		
	}


	public View getView() {
		View view = LayoutInflater.from(context).inflate(R.layout.view_file_item_logn_click, null);
		LinearLayout openFileBtn = (LinearLayout) view.findViewById(R.id.openFileButton);//打开文件
		LinearLayout delFileBtn = (LinearLayout) view.findViewById(R.id.delFileButton);//删除文件
		LinearLayout cope2PhoneFileBtn = (LinearLayout) view.findViewById(R.id.cope2PhoneFileButton);//将文件拷贝到手机
		LinearLayout cancelBtn = (LinearLayout) view.findViewById(R.id.cancelButton);//取消按钮
		
		openFileBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				((FunctionActivity)context).openFile(arg0, arg2);
			}
		});

		delFileBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				try {
					if (pf.getString("sys").equals("true")) {
						Toast.makeText(context, R.string.del_err, Toast.LENGTH_LONG).show();
						return;
					}
				} catch (JSONException e1) {
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
						if (pf.getString("sys").equals("true")) {
							Toast.makeText(context, R.string.copy_err, Toast.LENGTH_LONG).show();
							return;
						}
					}
					
					if (pf.getString("dir").equals("true")) {
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
							HashMap<String,Object> map = (HashMap<String, Object>) msg.obj;
							long total =  (Long) map.get("total");//文件总长度
							long curr =  (Long) map.get("curr");//当前下载的长度
							
							int p = (int)(new Double(curr)/new Double(total) * 100);
							downProgressBar.setProgress(p);
							
							fileNameTextView.setText(context.getResources().getString(R.string.copying_file) + PcOpManager.copyFileName);
							
							totalProgressTextView.setText(new Tools().js(total));
							currentProgressTextView.setText(new Tools().js(curr));
							if (total==curr) {
								okBtn.setVisibility(View.VISIBLE);
							}
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
		return view;
		
	}
}
