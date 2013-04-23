package com.wifiplayer.activitys.views;

import com.weibo.sdk.android.WeiboException;
import com.wifiplayer.R;
import com.wifiplayer.activitys.FunctionActivity;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 可以控制的pc视图
 * @author Administrator
 *
 */
public class Share2WeiBoView implements View.OnClickListener{

	private Context context;
	private Dialog dialog;
	
	public Share2WeiBoView(Context context, Dialog dialog) {
		super();
		this.context = context;
		this.dialog = dialog;
	}


	EditText shareEditText = null;
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
			FunctionActivity fa = (FunctionActivity)context;
			try {
				fa.share2weibo(shareEditText.getText().toString());
			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.cancelButton:
			break;
		default:
			break;
		}
	}
}
