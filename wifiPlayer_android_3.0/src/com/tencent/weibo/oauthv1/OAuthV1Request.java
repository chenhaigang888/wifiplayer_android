package com.tencent.weibo.oauthv1;

import java.util.List;

import org.apache.http.NameValuePair;

import com.tencent.weibo.api.RequestAPI;
import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.exceptions.OAuthClientException;
import com.tencent.weibo.utils.QHttpClient;

/**
 * ����OAuth version 1 ��׼ʵ�� Request API �ӿڵ���
 */

public class OAuthV1Request implements RequestAPI {
    private QHttpClient qHttpClient;

//    private static Log log = LogFactory.getLog(OAuthV1Request.class);

    /**
     * ʹ����Ϻ������ shutdownConnection() �ر��Զ����ɵ����ӹ�����
     */
    public OAuthV1Request(){
        qHttpClient=new QHttpClient();
    }
    
    public OAuthV1Request(QHttpClient qHttpClient) {
        this.qHttpClient=qHttpClient;
    }

    public String getResource(String url, List<NameValuePair> paramsList, OAuth oAuth) throws Exception {

        if(null==qHttpClient){
            throw new OAuthClientException("1001");
        }
        OAuthV1 oAuthV1 = (OAuthV1) oAuth;
        paramsList.addAll(oAuthV1.getTokenParamsList());

        String queryString = OAuthV1Client.getOauthParams(url, "GET", oAuthV1.getOauthConsumerSecret(),
                oAuthV1.getOauthTokenSecret(), paramsList);
        return qHttpClient.httpGet(url, queryString);
    }

    public String postContent(String url, List<NameValuePair> paramsList, OAuth oAuth) throws Exception {

        if(null==qHttpClient){
            throw new OAuthClientException("1001");
        }
        OAuthV1 oAuthV1 = (OAuthV1) oAuth;
        paramsList.addAll(oAuthV1.getTokenParamsList());

        String queryString = OAuthV1Client.getOauthParams(url, "POST", oAuthV1.getOauthConsumerSecret(),
                oAuthV1.getOauthTokenSecret(), paramsList);

//        log.info("RequestAPI postContent queryString = " + queryString);
        return qHttpClient.httpPost(url, queryString);
    }

    public String postFile(String url, List<NameValuePair> paramsList, List<NameValuePair> files, OAuth oAuth) throws Exception {

        OAuthV1 oAuthV1 = (OAuthV1) oAuth;
        paramsList.addAll(oAuthV1.getTokenParamsList());

        String queryString = OAuthV1Client.getOauthParams(url, "POST", oAuthV1.getOauthConsumerSecret(),
                oAuthV1.getOauthTokenSecret(), paramsList);

        return qHttpClient.httpPostWithFile(url, queryString, files);
    }

    /**
     * �����ʹ�õ����Զ����ɵ�QHttpClient�����ø÷����ر����ӹ�����
     */
    public void shutdownConnection() {
        qHttpClient.shutdownConnection();
        
    }

    public QHttpClient getqHttpClient() {
        return qHttpClient;
    }

    public void setqHttpClient(QHttpClient qHttpClient) {
        this.qHttpClient = qHttpClient;
    }
}
