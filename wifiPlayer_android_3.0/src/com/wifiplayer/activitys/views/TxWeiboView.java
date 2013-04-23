package com.wifiplayer.activitys.views;

import com.google.gson.Gson;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv2.OAuthV2;
import com.wifiplayer.R;
import com.wifiplayer.activitys.FunctionActivity;
import com.wifiplayer.activitys.utils.SendBroadCastUtil;
import com.wifiplayer.bean.ReqReplyOp;
import com.wifiplayer.bean.TxWeiBoShareReply;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 腾讯微博分享
 * @author Administrator
 *
 */
public class TxWeiboView implements View.OnClickListener {

	private Context context;
	private Dialog dialog;
	private OAuthV2 oAuth;
	
	private EditText shareEditText = null;

	public TxWeiboView(Context context, Dialog dialog, OAuthV2 oAuth) {
		super();
		this.context = context;
		this.dialog = dialog;
		this.oAuth = oAuth;
	}

	public View getView() {
		
		/*初始化控件*/
		View view = LayoutInflater.from(context).inflate(R.layout.view_share_use_weibo, null);
		shareEditText = (EditText) view.findViewById(R.id.shareEditText);
		Button okBtn = (Button) view.findViewById(R.id.okButton);
		Button cancelBtn = (Button) view.findViewById(R.id.cancelButton);
		
		cancelBtn.setOnClickListener(this);
		okBtn.setOnClickListener(this);
		return view;
		
	}



	@Override
	public void onClick(View v) {
		dialog.cancel();
		int id = v.getId();
		switch (id) {
		case R.id.okButton:
			sendWeibo();
			
			break;
		case R.id.cancelButton:
			break;
		default:
			break;
		}
	}

	private void sendWeibo() {
		
        try {
        	new Thread(new Runnable() {
				
				@Override
				public void run() {
					TAPI tAPI= new TAPI(OAuthConstants.OAUTH_VERSION_2_A);
		            String response;
					try {
						response = tAPI.add(oAuth, "json", shareEditText.getText().toString(), "127.0.0.1");
						tAPI.shutdownConnection();
			            Gson gson = new Gson();
			            TxWeiBoShareReply twbsr = gson.fromJson(response, TxWeiBoShareReply.class);
			            short result = FunctionActivity.TX_WEIBO_SHARE_FAIL;
			            if (twbsr.getMsg().equals("ok")) {
			            	result = FunctionActivity.TX_WEIBO_SHARE_SUCC;
			            }
			            ReqReplyOp rro = new ReqReplyOp();
						rro.setCmd(result);
						SendBroadCastUtil.sendBroadCast(context, rro);
					} catch (Exception e) {
						e.printStackTrace();
					}
		            
				}
			}).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
		
	}
}
