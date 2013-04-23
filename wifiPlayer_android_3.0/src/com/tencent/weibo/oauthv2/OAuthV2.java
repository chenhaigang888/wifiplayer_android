package com.tencent.weibo.oauthv2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.constants.OAuthConstants;

/**
 * OAuth version 2 ��֤����ʵ����
 */
public class OAuthV2 extends OAuth implements Serializable{
    
    private static final long serialVersionUID = -4667312552797390709L;
    private String redirectUri = "null";// ��Ȩ�ص���ַ
    private String clientId = "";// ����Ӧ��ʱ�����app_key
    private String clientSecret="";//����Ӧ��ʱ���䵽��app_secret
    private String responseType = "code";// code��token��Ĭ��Ϊcode
    private String type="default";//��ʾ��Ȩҳ�����ͣ�Ĭ����ȨҳΪpc��Ȩҳ
    private String authorizeCode= null;//������ȡaccessToken����Ȩ��
    private String accessToken= null;
    private String expiresIn= null;//accessToken����ʱ��
    private String grantType= "authorization_code";//��authorization_code, ��refresh_token
    private String refreshToken= null;//ˢ��token
    
//    private static Log log = LogFactory.getLog(OAuthV2.class);// ��־���

    public OAuthV2() {
        super();
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_2_A;
    }
    
    /**
     * @param redirectUri ��֤�ɹ���������ᱻ�ض��������ַ
     */
    public OAuthV2(String redirectUri) {
        super();
        this.redirectUri = redirectUri;
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_2_A;
    }

    /**
     * @param clientId Ӧ�����뵽��APP KEY
     * @param clientSecret Ӧ�����뵽��APP SECRET
     * @param redirectUri ��֤�ɹ���������ᱻ�ض��������ַ
     */
    public OAuthV2(String clientId, String clientSecret, String redirectUri) {
        super();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.oauthVersion=OAuthConstants.OAUTH_VERSION_2_A;
    }
    
    /**Authorize code grant��ʽ�У�Authorization�׶���Ҫ�Ĳ���*/
    public List<NameValuePair> getAuthorizationParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            paramsList.add(new BasicNameValuePair("client_id",clientId));
            paramsList.add(new BasicNameValuePair("response_type",  responseType));
            paramsList.add(new BasicNameValuePair("redirect_uri", redirectUri));
        return paramsList;
    }
    
    /**Authorize code grant��ʽ�У�AccessToken�׶���Ҫ�Ĳ���*/
    public List<NameValuePair> getAccessTokenByCodeParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            paramsList.add(new BasicNameValuePair("client_id",clientId));
            paramsList.add(new BasicNameValuePair("client_secret",clientSecret));
            paramsList.add(new BasicNameValuePair("redirect_uri", redirectUri));
            paramsList.add(new BasicNameValuePair("grant_type",  "authorization_code"));
            paramsList.add(new BasicNameValuePair("code",  authorizeCode));
        return paramsList;
    }
    
    /**
     * ����APIʱ���踽����OAuth��Ȩ��Ϣ
     * @return
     */
    public List<NameValuePair> getTokenParamsList() {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            paramsList.add(new BasicNameValuePair("oauth_consumer_key",clientId));
            paramsList.add(new BasicNameValuePair("access_token",accessToken));
            paramsList.add(new BasicNameValuePair("openid",  openid));
            paramsList.add(new BasicNameValuePair("clientip", clientIP));
            paramsList.add(new BasicNameValuePair("oauth_version",  oauthVersion));
            paramsList.add(new BasicNameValuePair("scope", scope));
        return paramsList;
    }
    
    /**�ض����ַ*/
    public String getRedirectUri() {
        return redirectUri;
    }

    /**�ض����ַ*/
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    /**Ӧ�õ�APP KEY*/
    public String getClientId() {
        return clientId;
    }

    /**Ӧ�õ�APP KEY*/
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**��Ȩ����*/
    public String getResponeType() {
        return responseType;
    }

    /**Ӧ�����뵽��APP SECRET*/
    public String getClientSecret() {
        return clientSecret;
    }

    /**Ӧ�����뵽��APP SECRET*/
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**��Ȩ����*/
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    /** ��ʾ��Ȩҳ�����ͣ�Ĭ����ȨҳΪpc��Ȩҳ */
    public String getType() {
        return type;
    }

    /** ��ʾ��Ȩҳ�����ͣ�Ĭ����ȨҳΪpc��Ȩҳ */
    public void setType(String type) {
        this.type = type;
    }

    /**��Ȩ��*/
    public String getAuthorizeCode() {
        return authorizeCode;
    }

    /**��Ȩ��*/
    public void setAuthorizeCode(String authorizeCode) {
        this.authorizeCode = authorizeCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**accessToken����ʱ��*/
    public String getExpiresIn() {
        return expiresIn;
    }

    /**accessToken����ʱ��*/
    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**ȷ������Ķ���authorization_code��refresh_token*/
    public String getGrantType() {
        return grantType;
    }

    /**ȷ������Ķ���authorization_code��refresh_token*/
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    /**ˢ��token*/
    public String getRefreshToken() {
        return refreshToken;
    }

    /**ˢ��token*/
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
