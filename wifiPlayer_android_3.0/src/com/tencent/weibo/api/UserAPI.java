package com.tencent.weibo.api;

import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;

/**
 * �ʻ����API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3">��Ѷ΢������ƽ̨���ʻ���ص�API�ĵ�<a>
 */

public class UserAPI  extends BasicAPI{
 
    private String userInfoUrl=apiBaseUrl+"/user/info";
    private String userOtherInfoUrl=apiBaseUrl+"/user/other_info";
    private String userInfosUrl=apiBaseUrl+"/user/infos";

    /**
     * ʹ����Ϻ������ shutdownConnection() �ر��Զ����ɵ����ӹ�����
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     */
    public UserAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     * @param qHttpClient ʹ�����е����ӹ�����
     */
    public UserAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * ��ȡ�Լ�������
	 * 
	 * @param oAuth 
	 * @param format �������ݵĸ�ʽ��json��xml��
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E8%8E%B7%E5%8F%96%E8%87%AA%E5%B7%B1%E7%9A%84%E8%AF%A6%E7%BB%86%E8%B5%84%E6%96%99">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String info(OAuth oAuth, String format) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		
		return requestAPI.getResource(userInfoUrl, paramsList,
				oAuth);
	}

	/**
	 * ��ȡ�����û���������
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ��json��xml��
	 * @param name ���˵��ʻ�������ѡ��
	 * @param fopenid  ���˵�openid����ѡ�� name��fopenid����ѡһ������ͬʱ��������nameֵΪ�� 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E8%8E%B7%E5%8F%96%E5%85%B6%E4%BB%96%E4%BA%BA%E8%B5%84%E6%96%99">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String otherInfo(OAuth oAuth, String format, String name, String fopenid)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
		
		return requestAPI.getResource(userOtherInfoUrl,
				paramsList, oAuth);
	}



	/**
	 * ��ȡһ���˵ļ�����
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param names �û�ID�б� ���� abc,edf,xxxx
	 * @param fopenid  ���˵�openid����ѡ�� name��fopenid����ѡһ������ͬʱ��������nameֵΪ�� 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E8%8E%B7%E5%8F%96%E4%B8%80%E6%89%B9%E4%BA%BA%E7%9A%84%E7%AE%80%E5%8D%95%E8%B5%84%E6%96%99">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String infos(OAuth oAuth, String format, String names ,String fopenids
			)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("names", names));
        paramsList.add(new BasicNameValuePair("fopenids", fopenids));
		
		return requestAPI.getResource(userInfosUrl,paramsList, oAuth);
	}

    public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        userInfoUrl=apiBaseUrl+"/user/info";
        userOtherInfoUrl=apiBaseUrl+"/user/other_info";
        userInfosUrl=apiBaseUrl+"/user/infos";
    }

}
