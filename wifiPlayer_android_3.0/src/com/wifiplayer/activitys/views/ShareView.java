package com.wifiplayer.activitys.views;

import java.text.SimpleDateFormat;

import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.oauthv2.OAuthV2Client;
import com.tencent.weibo.webview.OAuthV2AuthorizeWebView;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.wifiplayer.R;
import com.wifiplayer.utils.AccessTokenKeeper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 分享视图
 * @author chenkaigang
 *
 */
public class ShareView {

	private Context context;
	private Dialog dialog;
//	private Weibo mWeiBo;
	
	
	
	public ShareView(Context context, Dialog dialog) {
		super();
		this.context = context;
		this.dialog = dialog;
	}

	
	
	/*腾讯微博*/
//  private static String TAG="OAuthV2ImplicitGrantActivity.class";
    
  /*
   * 申请APP KEY的具体介绍，可参见 
   * http://wiki.open.t.qq.com/index.php/应用接入指引
   * http://wiki.open.t.qq.com/index.php/腾讯微博移动应用接入规范#.E6.8E.A5.E5.85.A5.E6.B5.81.E7.A8.8B
   */
  //!!!请根据您的实际情况修改!!!      认证成功后浏览器会被重定向到这个url中  必须与注册时填写的一致
  private String redirectUri="http://www.xn--yeto9bx06i.com";                   
  //!!!请根据您的实际情况修改!!!      换为您为自己的应用申请到的APP KEY
  private String clientId = "801198444"; 
  //!!!请根据您的实际情况修改!!!      换为您为自己的应用申请到的APP SECRET
  private String clientSecret="57b7dfafa317842fa576223425b2b7a5";
  private OAuthV2 oAuth;
	
	public View getView() {
		View view = LayoutInflater.from(context).inflate(R.layout.view_share_view, null);
		LinearLayout sinaWeiBoShareBtn = (LinearLayout) view.findViewById(R.id.sinaWeiBoShareLinearLayout);
		LinearLayout txWeiBoShareBtn = (LinearLayout) view.findViewById(R.id.txShareLinearLayout);
		LinearLayout emailShareBtn = (LinearLayout) view.findViewById(R.id.emailShareLinearLayout);
		LinearLayout cancelBtn = (LinearLayout) view.findViewById(R.id.cancelLinearLayout);
		
		sinaWeiBoShareBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
				Oauth2AccessToken accToken = AccessTokenKeeper.readAccessToken(context);
					Log.i("receive", "accToken.getExpiresTime():" + accToken.getExpiresTime());
					if (!accToken.getToken().equals("")) {
						Dialog share_dialog = new Dialog(context, R.style.no_title_dialog);
						View view = new Share2WeiBoView(context, share_dialog, accToken).getView();
						share_dialog.setContentView(view);
						share_dialog.show();
				} else {
					Log.i("receive", "认证中:");
					Weibo mWeiBo = Weibo.getInstance("308491239", "http://www.xn--yeto9bx06i.com");
					mWeiBo.authorize(context, new AuthDialogListener());
				}
			}
		});
		
		txWeiBoShareBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				oAuth=new OAuthV2(redirectUri);
		        oAuth.setClientId(clientId);
		        oAuth.setClientSecret(clientSecret);
		        OAuthV2Client.getQHttpClient().shutdownConnection();
				Intent intent = new Intent(context, OAuthV2AuthorizeWebView.class);//创建Intent，使用WebView让用户授权
                intent.putExtra("oauth", oAuth);
                ((Activity)context).startActivityForResult(intent,2);  
			}
		});
		
		emailShareBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				Intent i = new Intent(Intent.ACTION_SEND); 
//            	i.setType("text/plain"); //模拟器请使用这行
            	i.setType("message/rfc822") ; // 真机上使用这行
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
		
		return view;
		
	}
	
	/**
	 * 新浪微博认证
	 * @author Administrator
	 *
	 */
	class AuthDialogListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
        	Log.i("receive", "新浪微博进来了吗");
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            Oauth2AccessToken accessToken = new Oauth2AccessToken(token, expires_in);
            Log.i("receive", "认证成功前");
            if (accessToken.isSessionValid()) {
            	Log.i("receive", "认证成功");
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(accessToken.getExpiresTime()));
                Dialog share_dialog = new Dialog(context, R.style.no_title_dialog);
				View view = new Share2WeiBoView(context, share_dialog, accessToken).getView();
				share_dialog.setContentView(view);
				share_dialog.show();
                try {
                    Class sso = Class.forName("com.weibo.sdk.android.api.WeiboAPI");// 如果支持weiboapi的话，显示api功能演示入口按钮
                } catch (ClassNotFoundException e) {
                }
                AccessTokenKeeper.keepAccessToken(context, accessToken);
                Toast.makeText(context, "认证成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(WeiboDialogError e) {
        	Log.i("receive", "----------------------onError-----------------------");
            Toast.makeText(context,  "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
        	Log.i("receive", "----------------------onCancel-----------------------");
            Toast.makeText(context, "Auth cancel", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
        	Log.i("receive", "----------------------onWeiboException-----------------------");
            Toast.makeText(context,  "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
