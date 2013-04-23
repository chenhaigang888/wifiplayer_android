package com.tencent.weibo.api;

import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;

/**
 * ˽�����API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E7%A7%81%E4%BF%A1%E7%9B%B8%E5%85%B3">��Ѷ΢������ƽ̨��˽����ص�API�ĵ�<a>
 */

public class PrivateAPI extends BasicAPI {

    private String privateRecvUrl=apiBaseUrl+"/private/recv";
    private String privateSendUrl=apiBaseUrl+"/private/send";
    
    /**
     * ʹ����Ϻ������ shutdownConnection() �ر��Զ����ɵ����ӹ�����
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     */
    public PrivateAPI(String OAuthVersion) {
        super(OAuthVersion);
    }
    
    /**
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     * @param qHttpClient ʹ�����е����ӹ�����
     */
    public PrivateAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * ��ȡ˽���ռ����б�
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ��json��xml��
	 * @param pageflag  ��ҳ��ʶ��0����һҳ��1�����·�ҳ��2���Ϸ�ҳ��
	 * @param pagetime  ��ҳ��ʼʱ�䣨��һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼ʱ�䣬���·�ҳ������һ�����󷵻ص����һ����¼ʱ�䣩
	 * @param reqnum ÿ�������¼��������1-20����
	 * @param lastid ���ڷ�ҳ����pagetime���ʹ�ã���һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼id�����·�ҳ������һ�����󷵻ص����һ����¼id��
	 * @param contenttype  ���ݹ��ˡ�0-�������ͣ�1-���ı���2-�����ӣ�4-��ͼƬ��8-����Ƶ��16-����Ƶ��Ĭ��Ϊ0 
     * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E7%A7%81%E4%BF%A1%E7%9B%B8%E5%85%B3/%E6%94%B6%E4%BB%B6%E7%AE%B1">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String recv(OAuth oAuth, String format, String pageflag,
			String pagetime, String reqnum, String lastid,String contenttype) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
        paramsList.add(new BasicNameValuePair("lastid", lastid));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(privateRecvUrl,
				paramsList, oAuth);
	}
	
	/**
	 * ��ȡ˽�ŷ������б�
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ��json��xml��
	 * @param pageflag  ��ҳ��ʶ��0����һҳ��1�����·�ҳ��2���Ϸ�ҳ��
	 * @param pagetime  ��ҳ��ʼʱ�䣨��һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼ʱ�䣬���·�ҳ������һ�����󷵻ص����һ����¼ʱ�䣩
	 * @param reqnum ÿ�������¼��������1-20����
     * @param lastid ���ڷ�ҳ����pagetime���ʹ�ã���һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼id�����·�ҳ������һ�����󷵻ص����һ����¼id��
	 * @param contenttype  ���ݹ��ˡ�0-�������ͣ�1-���ı���2-�����ӣ�4-��ͼƬ��8-����Ƶ��16-����Ƶ��Ĭ��Ϊ0 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E7%A7%81%E4%BF%A1%E7%9B%B8%E5%85%B3/%E5%8F%91%E4%BB%B6%E7%AE%B1">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String send(OAuth oAuth, String format, String pageflag,
			String pagetime, String reqnum, String lastid,String contenttype) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
        paramsList.add(new BasicNameValuePair("lastid", lastid));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(privateSendUrl,
				paramsList, oAuth);
	}

    public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        privateRecvUrl=apiBaseUrl+"/private/recv";
        privateSendUrl=apiBaseUrl+"/private/send";
        
    }
}
