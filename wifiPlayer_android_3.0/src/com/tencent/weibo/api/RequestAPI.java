package com.tencent.weibo.api;

import java.util.List;

import org.apache.http.NameValuePair;

import com.tencent.weibo.beans.OAuth;

/**
 * ����Http��HttpsЭ��ͨѶ�Ľӿ�
 */
public interface RequestAPI {
    /**
     * ʹ��Get��������API����
     * 
     * @param url  Զ��API�����ַ
     * @param paramsList �����б�
     * @param oAuth OAuth��Ȩ��Ϣ
     * @return  Json �� XML ��ʽ����Դ
     * @throws Exception
     * @see <a href="">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
     */
    public String getResource(String url, List<NameValuePair> paramsList, OAuth oAuth) throws Exception;
    
    /**
     * ʹ��Post��������API����
     * 
     * @param url  Զ��API�����ַ
     * @param paramsList �����б�
     * @param oAuth OAuth��Ȩ��Ϣ
     * @return  Json �� XML ��ʽ����Դ
     * @throws Exception
     * @see <a href="">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
     */
    public String postContent(String url, List<NameValuePair> paramsList, OAuth oAuth) throws Exception;
    
    /**
     * ʹ��Post��������API���󣬲��ϴ��ļ�
     * 
     * @param url  Զ��API�����ַ
     * @param paramsList �����б�
     * @param files ��Ҫ�ϴ����ļ��б�
     * @param oAuth OAuth��Ȩ��Ϣ
     * @return  Json �� XML ��ʽ����Դ
     * @throws Exception
     * @see <a href="">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
     */
    public String postFile(String url, List<NameValuePair> paramsList,
            List<NameValuePair> files, OAuth oAuth) throws Exception;
    
    /**
     * �ر����ӹ�����
     */
    public void shutdownConnection();

}
