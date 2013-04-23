package com.tencent.weibo.oauthv2;

import java.util.List;

import org.apache.http.NameValuePair;

import com.tencent.weibo.api.RequestAPI;
import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.exceptions.OAuthClientException;
import com.tencent.weibo.utils.QHttpClient;
import com.tencent.weibo.utils.QStrOperate;

/**
 *  ����OAuth version 2 ��׼ʵ�� Request API �ӿڵ���
 */
public class OAuthV2Request implements RequestAPI {

    private QHttpClient qHttpClient;
//    private static Log log = LogFactory.getLog(OAuthV2Request.class);
    
    /**
     * ʹ����Ϻ������ shutdownConnection() �ر��Զ����ɵ����ӹ�����
     */
    public OAuthV2Request(){
        qHttpClient = new QHttpClient();
    }


    public OAuthV2Request(QHttpClient qHttpClient) {
        this.qHttpClient=qHttpClient;
    }

    public String getResource(String url, List<NameValuePair> paramsList, OAuth oAuth)
            throws Exception {
        
        if(null==qHttpClient){
            throw new OAuthClientException("1001");
        }
        OAuthV2 oAuthV2 = (OAuthV2) oAuth;
        removeExtraClientip(paramsList, oAuthV2);
        
        paramsList.addAll(oAuthV2.getTokenParamsList());
        
        String queryString = QStrOperate.getQueryString(paramsList);
        return qHttpClient.httpGet(url, queryString);
        
    }

    private void removeExtraClientip(List<NameValuePair> paramsList, OAuthV2 oAuthV2) {
        int i=0;
        boolean found=false;
        for(NameValuePair nvp:paramsList){
            if(nvp.getName()!="clientip"){
                i++;
            }else{
                if(nvp.getValue()!="127.0.0.1"){
                    oAuthV2.setClientIP(nvp.getValue());
                }
                found=true;
                break;
            }
        }
        if(found)paramsList.remove(i);
    }

    public String postContent(String url, List<NameValuePair> paramsList, OAuth oAuth)
            throws Exception {
        
        if(null==qHttpClient){
            throw new OAuthClientException("1001");
        }
        OAuthV2 oAuthV2 = (OAuthV2) oAuth;
        removeExtraClientip(paramsList, oAuthV2);
        
        paramsList.addAll(oAuthV2.getTokenParamsList());
        
        
        String queryString = QStrOperate.getQueryString(paramsList);
        
//        log.info("RequestAPI postContent queryString = " + queryString);
        return qHttpClient.httpPost(url, queryString);
        
    }


    public String postFile(String url, List<NameValuePair> paramsList,
            List<NameValuePair> files, OAuth oAuth) throws Exception {
        
        if(null==qHttpClient){
            throw new OAuthClientException("1001");
        }
        OAuthV2 oAuthV2 = (OAuthV2) oAuth;
        removeExtraClientip(paramsList, oAuthV2);
        
        paramsList.addAll(oAuthV2.getTokenParamsList());
        
        String queryString = QStrOperate.getQueryString(paramsList);
//        log.info("RequestAPI postContent queryString = " + queryString);
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
