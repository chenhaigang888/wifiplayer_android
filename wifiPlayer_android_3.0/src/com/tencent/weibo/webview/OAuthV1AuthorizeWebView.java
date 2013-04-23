package com.tencent.weibo.webview;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv1.OAuthV1;

/**
 * ʹ��Webview��ʾOAuth Version 1.0 ��Ȩ��ҳ��<br>
 * <p>����ʹ�÷�����</p>
 * <li>��Ҫ���ñ���ĵط���������´���
 * <pre>
 * //�뽫OAuthV1Activity��Ϊ�����������
 * Intent intent = new Intent(OAuthV1Activity.this, OAuthV1AuthorizeWebView.class);   
 * intent.putExtra("oauth", oAuth);  //oAuthΪOAuthV1���ʵ���������Ȩ�����Ϣ
 * startActivityForResult(intent, myRrequestCode);  //�����ú��ʵ�requsetCode
 * </pre>
 * <li>��д���ջص���Ϣ�ķ���
 * <pre>
 * if (requestCode==myRrequestCode) {  //��Ӧ֮ǰ���õĵ�myRequsetCode
 *     if (resultCode==OAuthV1AuthorizeWebView.RESULT_CODE) {
 *         //ȡ�÷��ص�OAuthV1��ʵ��oAuth
 *         oAuth=(OAuthV1) data.getExtras().getSerializable("oauth");
 *     }
 * }
 * <pre>
 * @see android.app.Activity#onActivityResult(int requestCode, int resultCode,  Intent data)
 */
public class OAuthV1AuthorizeWebView extends Activity
{
    public final static int RESULT_CODE=1;
	private static final String TAG = "OAuthV1AuthorizeWebView";
	private OAuthV1 oAuth;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout=new LinearLayout(this);
		WebView webView = new WebView(this);
		linearLayout.addView(webView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		setContentView(linearLayout);
		Intent intent = this.getIntent();
		oAuth = (OAuthV1) intent.getExtras().getSerializable("oauth");
		String urlStr = OAuthConstants.OAUTH_V1_AUTHORIZE_URL+"?oauth_token="+oAuth.getOauthToken();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webView.requestFocus();
        webView.loadUrl(urlStr);
        System.out.println(urlStr.toString());
        Log.i(TAG, "WebView Starting....");
		WebViewClient client = new WebViewClient()
		{
            /**
             * �ص���������ҳ�濪ʼ����ʱִ��
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i(TAG, "WebView onPageStarted...");
                Log.i(TAG, "URL = " + url);
                if (url.indexOf("checkType=verifycode") != -1) {
                    int start=url.indexOf("checkType=verifycode&v=")+23;
                    String verifyCode=url.substring(start, start+6);
                    oAuth.setOauthVerifier(verifyCode);
                    Intent intent = new Intent();
                    intent.putExtra("oauth", oAuth);
                    setResult(RESULT_CODE, intent);
                    view.destroyDrawingCache();
                    view.destroy();
                    finish();
                }
                super.onPageStarted(view, url, favicon);
            }
		    
			/*
			 * TODO
			 * Android2.2�����ϰ汾����ʹ�ø÷���
			 * Ŀǰhttps://open.t.qq.com�д���http��Դ������sslerror������վ�������ȥ���÷���
			 */
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
			    if((null != view.getUrl())&&(view.getUrl().startsWith("https://open.t.qq.com"))){
			        handler.proceed();//����֤��
			    }else{
			        handler.cancel(); //Ĭ�ϵĴ���ʽ��WebView��ɿհ�ҳ
			    }
		        //handleMessage(Message msg); ��������
		  }

		};
		webView.setWebViewClient(client);
	}
	
}
