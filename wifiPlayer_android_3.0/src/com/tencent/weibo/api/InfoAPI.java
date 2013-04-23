package com.tencent.weibo.api;

import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;


/**
 * ���ݸ������API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%95%B0%E6%8D%AE%E6%9B%B4%E6%96%B0%E7%9B%B8%E5%85%B3">��Ѷ΢������ƽ̨�����ݸ�����ص�API�ĵ�<a>
 */

public class InfoAPI extends BasicAPI {

    private String infoUpdateUrl=apiBaseUrl+"/info/update";

    /**
     * ʹ����Ϻ������ shutdownConnection() �ر��Զ����ɵ����ӹ�����
     * @param OAuthVersion  ����OAuthVersion������ͨ���������
     */
    public InfoAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     * @param qHttpClient ʹ�����е����ӹ�����
     */
    public InfoAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * �鿴���ݸ�������
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ��json��xml��
	 * @param op �������ͣ�  0-����ѯ��1-��ѯ��Ϻ���Ӧ������0
	 * @param type  5-��ҳδ����Ϣ������6-@ҳδ����Ϣ������7-˽��ҳ��Ϣ������8-������������9-��ҳ�㲥����ԭ���ģ�<br>
	 *                         op=0ʱ��typeĬ��Ϊ0����ʱ�����������ͼ�����op=1ʱ�������ĳ�����͵�type������type���͵ļ������������������ͼ���
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%95%B0%E6%8D%AE%E6%9B%B4%E6%96%B0%E7%9B%B8%E5%85%B3/%E6%9F%A5%E7%9C%8B%E6%95%B0%E6%8D%AE%E6%9B%B4%E6%96%B0%E6%9D%A1%E6%95%B0">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String update(OAuth oAuth, String format, String op, String type)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("op", op));
		paramsList.add(new BasicNameValuePair("type", type));
		
		return requestAPI.getResource(infoUpdateUrl, paramsList,
				oAuth);
	}

    public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        infoUpdateUrl=apiBaseUrl+"/info/update";
    }


}
