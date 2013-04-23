package com.tencent.weibo.oauthv1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.constants.OAuthConstants;

/**
 * OAuth version 1 ��֤����ʵ����
 */

public class OAuthV1 extends OAuth implements Serializable{

    private static final long serialVersionUID = 4695293221245171919L;
    private String oauthCallback = "null";// ��֤�ɹ���������ᱻ�ض������url��
    private String oauthConsumerKey = "";// AppKey(client credentials)
    private String oauthConsumerSecret = "";// AppSecret
    private String oauthSignatureMethod = "HMAC-SHA1";// ǩ����������ֻ֧��HMAC-SHA1
    private String oauthToken = ""; // Request Token �� Access Token
    private String oauthTimestamp = "";// ʱ���
    private String oauthNonce = "";// ����ֵ������ַ�������ֹ�طŹ���
    private String oauthTokenSecret = ""; // Request Token �� Access Token ��Ӧ��ǩ����Կ
    private String oauthVerifier = ""; // ��֤��

    // private static Log log = LogFactory.getLog(OAuthV1.class);// ��־���

    public OAuthV1() {
        super();
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_1 ;
    }

    /**
     * @param oauthCallback ��֤�ɹ���������ᱻ�ض��������ַ
     */
    public OAuthV1(String oauthCallback) {
        super();
        this.oauthCallback = oauthCallback;
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_1 ;
    }

    /**
     * 
     * @param oauthConsumerKey Ӧ�����뵽��APP KEY
     * @param oauthConsumerSecret Ӧ�����뵽��APP SECRET
     * @param oauthCallback ��֤�ɹ���������ᱻ�ض��������ַ
     */
    public OAuthV1(String oauthConsumerKey, String oauthConsumerSecret,
            String oauthCallback) {
        super();
        this.oauthConsumerKey = oauthConsumerKey;
        this.oauthConsumerSecret = oauthConsumerSecret;
        this.oauthCallback = oauthCallback;
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_1 ;
    }

    /**
     * ���� timestamp.
     * 
     * @return timestamp
     */
    private String generateTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * �������Ĵ� 15103344 �� 9999999 ֮������ִ���������������������Ϊ Oauth_Nonce ֵ
     * 
     * @return Oauth_Nonce
     */
    private String generateNonce() {
        String nonce = "";
        for (int i = 0; i < 4; i++) {
            nonce=String.valueOf(random.nextInt(100000000))+nonce;
            for(;nonce.length()<(i+1)*8;nonce="0"+nonce);
        }
        return nonce;
    }

    public List<NameValuePair> getParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        oauthTimestamp = this.generateTimeStamp();
        oauthNonce = this.generateNonce();
        paramsList.add(new BasicNameValuePair("oauth_consumer_key",oauthConsumerKey));
        paramsList.add(new BasicNameValuePair("oauth_signature_method",oauthSignatureMethod));
        paramsList.add(new BasicNameValuePair("oauth_timestamp", oauthTimestamp));
        paramsList.add(new BasicNameValuePair("oauth_nonce", oauthNonce));
        paramsList.add(new BasicNameValuePair("oauth_callback", oauthCallback));
        paramsList.add(new BasicNameValuePair("oauth_version", oauthVersion));
        return paramsList;
    }

    public List<NameValuePair> getAccessParams() {
        List<NameValuePair> paramsList = this.getTokenParamsList();
        paramsList.add(new BasicNameValuePair("oauth_verifier", oauthVerifier));
        return paramsList;
    }

    public List<NameValuePair> getTokenParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        oauthTimestamp = this.generateTimeStamp();
        oauthNonce = this.generateNonce();
        paramsList.add(new BasicNameValuePair("oauth_consumer_key",oauthConsumerKey));
        paramsList.add(new BasicNameValuePair("oauth_signature_method",oauthSignatureMethod));
        paramsList.add(new BasicNameValuePair("oauth_timestamp", oauthTimestamp));
        paramsList.add(new BasicNameValuePair("oauth_nonce", oauthNonce));
        paramsList.add(new BasicNameValuePair("oauth_token", oauthToken));
        paramsList.add(new BasicNameValuePair("oauth_version", oauthVersion));
        return paramsList;
    }

    /**Ӧ�õ�APP KEY*/
    public String getOauthConsumerKey() {
        return oauthConsumerKey;
    }

    /**Ӧ�õ�APP KEY*/
    public void setOauthConsumerKey(String oauthConsumerKey) {
        this.oauthConsumerKey = oauthConsumerKey;
    }

    /**ʹ�õ�ǩ����������ֻ֧��HMAC-SHA1*/
    public String getOauthSignatureMethod() {
        return oauthSignatureMethod;
    }

    /**ʹ�õ�ǩ����������ֻ֧��HMAC-SHA1*/
    public void setOauthSignatureMethod(String oauthSignatureMethod) {
        this.oauthSignatureMethod = oauthSignatureMethod;
    }

    /**Ӧ�����뵽��APP SECRET*/
    public String getOauthConsumerSecret() {
        return oauthConsumerSecret;
    }

    /**Ӧ�����뵽��APP SECRET*/
    public void setOauthConsumerSecret(String oauthConsumerSecret) {
        this.oauthConsumerSecret = oauthConsumerSecret;
    }

    /**���requestToken��accessToken*/
    public String getOauthToken() {
        return oauthToken;
    }

    /**���requestToken��accessToken*/
    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    /**ʱ���*/
    public String getOauthTimestamp() {
        return oauthTimestamp;
    }

    /**ʱ�����ͨ��{@link #generateTimeStamp()}�ɵõ���ǰʱ���*/
    public void setOauthTimestamp(String oauthTimestamp) {
        this.oauthTimestamp = oauthTimestamp;
    }

    /**����ֵ������ַ��������ظ�����*/
    public String getOauthNonce() {
        return oauthNonce;
    }

    /**����ֵ������ַ��������ظ����飬ͨ��{@link #generateNonce()}�ɵõ����ֵ*/
    public void setOauthNonce(String oauthNonce) {
        this.oauthNonce = oauthNonce;
    }

    /**�ض����ַ*/
    public String getOauthCallback() {
        return oauthCallback;
    }

    /**�ض����ַ*/
    public void setOauthCallback(String oauthCallback) {
        this.oauthCallback = oauthCallback;
    }

    /**���requestSecret��accessSecret*/
    public String getOauthTokenSecret() {
        return oauthTokenSecret;
    }

    /**���requestSecret��accessSecret*/
    public void setOauthTokenSecret(String oauthTokenSecret) {
        this.oauthTokenSecret = oauthTokenSecret;
    }

    /**��Ȩ��*/
    public String getOauthVerifier() {
        return oauthVerifier;
    }

    /**��Ȩ��*/
    public void setOauthVerifier(String oauthVerifier) {
        this.oauthVerifier = oauthVerifier;
    }
}
