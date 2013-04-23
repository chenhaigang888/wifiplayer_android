package com.tencent.weibo.api;

import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;

/**
 * �������API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%90%9C%E7%B4%A2%E7%9B%B8%E5%85%B3">��Ѷ΢������ƽ̨��������ص�API�ĵ�<a>
 */

public class SearchAPI extends BasicAPI {

    private String searchTUrl=apiBaseUrl+"/search/t";
    /**
     * ʹ����Ϻ������ shutdownConnection() �ر��Զ����ɵ����ӹ�����
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     */
    public SearchAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     * @param qHttpClient ʹ�����е����ӹ�����
     */
    public SearchAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * ����΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ��json��xml��
	 * @param keyword �����ؼ���
	 * @param pagesize ��������ļ�¼������1-30����
	 * @param page �����ҳ�룬��0��ʼ ҳ��
	 * @param contenttype ���ݹ��ˡ�0-��ʾ�������ͣ�1-���ı���2-�����ӣ�4-��ͼƬ��8-����Ƶ��0x10-����Ƶ 
	 * @param sorttype ����ʽ 0-��ʾ��Ĭ�Ϸ�ʽ����(��ʱ������(����)) 
	 * @param msgtype ��Ϣ�����ͣ���λʹ�ã� 0-���У�1-ԭ������2 ת�أ�8-�ظ�(���һ����Ϣ�����жԻ�)��0x10-�ջ�(�������ҳ�����жԻ�) 
	 * @param searchtype �������� 
     *               <li>0-Ĭ���������ͣ�����Ϊģ�������� 
     *               <li>1-ģ��������ʱ�����starttime��endtime���С��һСʱ��ʱ����������Ϊstarttimeǰendtime������㣬���������Ϊ1Сʱ 
     *               <li>8-ʵʱ������ѡ��ʵʱ������ֻ������������ӵ�΢����ʱ�������Ҫ����Ϊ����ļ����ӷ�Χ�ڲ���Ч�����Ҳ������������� 
     * @param starttime  ��ʼʱ�䣬��UNIXʱ���ʾ����1970��1��1��0ʱ0��0���������ڵ��������� endtime  ����ʱ�䣬��starttimeһ��ʹ�ã��������starttime��  
     * @param endtime  ����ʱ�䣬��starttimeһ��ʹ�ã��������starttime�� 
     * @param province  ʡ���루�����ʾ���Եص�������  
     * @param city  �б��루�����ʾ��ʡ������  
     * @param longitue  ���ȣ���ʵ����*1000000  
     * @param latitude  γ�ȣ���ʵ����*1000000  
     * @param radius  �뾶����������λ�ף�������20000��  

	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%90%9C%E7%B4%A2%E7%9B%B8%E5%85%B3/%E6%90%9C%E7%B4%A2%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String t(OAuth oAuth, String format, String keyword,
			String pagesize, String page,String contenttype,String sorttype,
			String msgtype ,String searchtype,String starttime, 
			String endtime, String province,String city, String longitue,
			String latitude,String radius) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("keyword", keyword));
		paramsList.add(new BasicNameValuePair("pagesize", pagesize));
		paramsList.add(new BasicNameValuePair("page", page));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
        paramsList.add(new BasicNameValuePair("sorttype", sorttype));
        paramsList.add(new BasicNameValuePair("msgtype", msgtype));
        paramsList.add(new BasicNameValuePair("searchtype", searchtype));
        paramsList.add(new BasicNameValuePair("starttime", starttime));
        paramsList.add(new BasicNameValuePair("endtime", endtime));
        paramsList.add(new BasicNameValuePair("province", province));
        paramsList.add(new BasicNameValuePair("city", city));
        paramsList.add(new BasicNameValuePair("longitue", longitue));
        paramsList.add(new BasicNameValuePair("latitude", latitude));
        paramsList.add(new BasicNameValuePair("radius", radius));
		
		return requestAPI.getResource(searchTUrl, paramsList,
				oAuth);
	}

	public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        searchTUrl=apiBaseUrl+"/search/t";
    }
}
