package com.tencent.weibo.utils;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * �Զ��������Httpclient��<br>
 * �ṩhttpGet��httpPost���ִ�����Ϣ�ķ�ʽ<br>
 * �ṩhttpPost�ϴ��ļ��ķ�ʽ
 */
public class QHttpClient {

    //TODO SDKĬ�ϲ������� 
    public static final int CONNECTION_TIMEOUT = 5000;
    public static final int CON_TIME_OUT_MS=5000;
    public static final int SO_TIME_OUT_MS=5000;
    public static final int MAX_CONNECTIONS_PER_HOST = 2;
    public static final int MAX_TOTAL_CONNECTIONS = 2;
    
    //��־���
    private static String TAG = "QHttpClient.class";
    
    private HttpClient httpClient;
    
    public QHttpClient(){
        this(MAX_CONNECTIONS_PER_HOST,MAX_TOTAL_CONNECTIONS,CON_TIME_OUT_MS,SO_TIME_OUT_MS);
    }
    
    public QHttpClient(int maxConnectionPerHost, int maxTotalConnections, int conTimeOutMs, int soTimeOutMs){
        
        // Register the "http" & "https" protocol scheme, They are required
        // by the default operator to look up socket factories.        
        SchemeRegistry supportedSchemes = new SchemeRegistry();         
        supportedSchemes.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        
        //-----------------------------------SSL Scheme------------------------------------------
        try {
            SSLSocketFactory sslSocketFactory=SSLSocketFactory.getSocketFactory();
            sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            supportedSchemes.register(new Scheme("https",new QSSLSocketFactory(), 443));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //----------------------------------SSL Scheme end---------------------------------------
        
        // Prepare parameters.
        HttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(httpParams, maxTotalConnections);
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(maxConnectionPerHost); 
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute); 
        
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUseExpectContinue(httpParams, false);
        
//        ThreadSafeClientConnManager  connectionManager = new ThreadSafeClientConnManager(httpParams,supportedSchemes);
        SingleClientConnManager singleClientConnManager=new SingleClientConnManager(httpParams,supportedSchemes);
        HttpConnectionParams.setConnectionTimeout(httpParams, conTimeOutMs);
        HttpConnectionParams.setSoTimeout(httpParams, soTimeOutMs);
        
        HttpClientParams.setCookiePolicy(httpParams, CookiePolicy.BROWSER_COMPATIBILITY);
//        httpClient=new DefaultHttpClient(connectionManager, httpParams);
        httpClient=new DefaultHttpClient(singleClientConnManager, httpParams);
    }

    /**
     * Get����������Ϣ
     * 
     * @param url  ���ӵ�URL
     * @param queryString  ���������
     * @return ���������ص���Ϣ
     * @throws Exception
     */
    public String httpGet(String url, String queryString) throws Exception {

        String responseData = null;
        if (queryString != null && !queryString.equals("")) {
            url += "?" + queryString;
        }
        Log.i(TAG, "QHttpClient httpGet [1] url = "+url);

        HttpGet httpGet = new HttpGet(url);
        httpGet.getParams().setParameter("http.socket.timeout",
                new Integer(CONNECTION_TIMEOUT));
        try {
            HttpResponse response=httpClient.execute(httpGet);
            Log.i(TAG, "QHttpClient httpGet [2] StatusLine : "+ response.getStatusLine());
            responseData =EntityUtils.toString(response.getEntity());
            Log.i(TAG, "QHttpClient httpGet [3] Response = "+ responseData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpGet.abort();
        }

        return responseData;
    }

    /**
     * Post����������Ϣ
     * 
     * @param url  ���ӵ�URL
     * @param queryString ���������
     * @return ���������ص���Ϣ
     * @throws Exception
     */
    public String httpPost(String url, String queryString) throws Exception {
        String responseData = null;
        URI tmpUri=new URI(url);
        URI uri = URIUtils.createURI(tmpUri.getScheme(), tmpUri.getHost(), tmpUri.getPort(), tmpUri.getPath(), 
                queryString, null);
        Log.i(TAG, "QHttpClient httpPost [1] url = "+uri.toURL());
        
        HttpPost httpPost = new HttpPost(uri);
        httpPost.getParams().setParameter("http.socket.timeout",
                new Integer(CONNECTION_TIMEOUT));
        if (queryString != null && !queryString.equals("")) {
            StringEntity reqEntity = new StringEntity(queryString);   
            // ��������   
             reqEntity.setContentType("application/x-www-form-urlencoded");   
            // �������������   
            httpPost.setEntity(reqEntity);
        }

        try {
            HttpResponse response=httpClient.execute(httpPost);
            Log.i(TAG, "QHttpClient httpPost [2] StatusLine = "+response.getStatusLine());
            responseData =EntityUtils.toString(response.getEntity());
            Log.i(TAG, "QHttpClient httpPost [3] responseData = "+responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            httpPost.abort();
        }

        return responseData;
    }
    
    /**
     * Post���������ļ�����Ϣ
     * 
     * @param url  ���ӵ�URL
     * @param queryString ���������
     * @param files �ϴ����ļ��б�
     * @return ���������ص���Ϣ
     * @throws Exception
     */
    public String httpPostWithFile(String url, String queryString, List<NameValuePair> files) throws Exception {

        String responseData = null;

        URI tmpUri=new URI(url);
        URI uri = URIUtils.createURI(tmpUri.getScheme(), tmpUri.getHost(), tmpUri.getPort(), tmpUri.getPath(), 
                queryString, null);
        Log.i(TAG, "QHttpClient httpPostWithFile [1]  uri = "+uri.toURL());
        
        
        MultipartEntity mpEntity = new MultipartEntity();
        HttpPost httpPost = new HttpPost(uri);
        StringBody stringBody;
        FileBody fileBody;
        File targetFile;
        String filePath;
        FormBodyPart fbp;
        
        List<NameValuePair> queryParamList=QStrOperate.getQueryParamsList(queryString);
        for(NameValuePair queryParam:queryParamList){
            stringBody=new StringBody(queryParam.getValue(),Charset.forName("UTF-8"));
            fbp= new FormBodyPart(queryParam.getName(), stringBody);
            mpEntity.addPart(fbp);
//            Log.i(TAG, "------- "+queryParam.getName()+" = "+queryParam.getValue());
        }
        
        for (NameValuePair param : files) {
            filePath = param.getValue();
            targetFile= new File(filePath);
            fileBody = new FileBody(targetFile,"application/octet-stream");
            fbp= new FormBodyPart(param.getName(), fileBody);
            mpEntity.addPart(fbp);
            
        }

//        Log.i(TAG, "---------- Entity Content Type = "+mpEntity.getContentType());

        httpPost.setEntity(mpEntity);
        
        try {
            HttpResponse response=httpClient.execute(httpPost);
            Log.i(TAG, "QHttpClient httpPostWithFile [2] StatusLine = "+response.getStatusLine());
            responseData =EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
          httpPost.abort();
        }
        Log.i(TAG, "QHttpClient httpPostWithFile [3] responseData = "+responseData);
        return responseData;
    }

    /**
     * �Ͽ�QHttpClient������
     */
    public void shutdownConnection(){
        try { 
            httpClient.getConnectionManager().shutdown(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
