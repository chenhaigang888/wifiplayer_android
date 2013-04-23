package com.tencent.weibo.api;

import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;

/**
 * ʱ�������API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF">��Ѷ΢������ƽ̨��ʱ������ص�API�ĵ�<a>
 */

public class StatusesAPI extends BasicAPI {

    private String statusesHomeTimelineUrl=apiBaseUrl+"/statuses/home_timeline";
    private String statusesUSERTimelineUrl=apiBaseUrl+"/statuses/user_timeline";
    private String statusesMentionsTimelineUrl=apiBaseUrl+"/statuses/mentions_timeline";
    private String statusesBroadcastTimelineUrl=apiBaseUrl+"/statuses/broadcast_timeline";
    private String statusesUserTimelineIdsUrl=apiBaseUrl+"/statuses/user_timeline_ids";
    private String statusesUsersTimelineUrl=apiBaseUrl+"/statuses/users_timeline";
    /**
     * ʹ����Ϻ������ shutdownConnection() �ر��Զ����ɵ����ӹ�����
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     */
    public StatusesAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     * @param qHttpClient ʹ�����е����ӹ�����
     */
    public StatusesAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * ��ҳʱ����
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param pageflag ��ҳ��ʶ��0����һҳ��1�����·�ҳ��2���Ϸ�ҳ��
	 * @param pagetime ��ҳ��ʼʱ�䣨��һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼ʱ�䣬���·�ҳ������һ�����󷵻ص����һ����¼ʱ�䣩 
	 * @param reqnum ÿ�������¼��������1-70����
	 * @param type ��ȡ���� 0x1 ԭ������ 0x2 ת�� 0x8 �ظ� 0x10 �ջ� 0x20 �ἰ 0x40 ���� <br>
     *                          ������ȡ���������ʹ��|����(0x1|0x2)�õ�3����ʱtype=3���ɣ������ʾ��ȡ�������� 
	 * @param contenttype ���ݹ��ˡ�0-��ʾ�������ͣ�1-���ı���2-�����ӣ�4-��ͼƬ��8-����Ƶ��0x10-����Ƶ 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E4%B8%BB%E9%A1%B5%E6%97%B6%E9%97%B4%E7%BA%BF">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String homeTimeline(OAuth oAuth, String format, String pageflag,
			String pagetime, String reqnum,String type, String contenttype) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
        paramsList.add(new BasicNameValuePair("type", type));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(statusesHomeTimelineUrl,
				paramsList, oAuth);
	}

	/**
	 * �����û�����ʱ����
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param pageflag ��ҳ��ʶ��0����һҳ��1�����·�ҳ��2���Ϸ�ҳ��
	 * @param pagetime ��ҳ��ʼʱ�䣨��һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼ʱ�䣬���·�ҳ������һ�����󷵻ص����һ����¼ʱ�䣩 
	 * @param reqnum ÿ�������¼��������1-70����
	 * @param lastid ���ڷ�ҳ����pagetime���ʹ�ã���һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼id�����·�ҳ������һ�����󷵻ص����һ����¼id�� 
	 * @param name  ����Ҫ��ȡ���û����û���
	 * @param fopenid ����Ҫ��ȡ���û���openid����ѡ�� <br>
     *                             name��fopenid����ѡһ������ͬʱ��������nameֵΪ�� 
     * @param type ��ȡ���� 0x1 ԭ������ 0x2 ת�� 0x8 �ظ� 0x10 �ջ� 0x20 �ἰ 0x40 ���� <br>
     *                          ������ȡ���������ʹ��|����(0x1|0x2)�õ�3����ʱtype=3���ɣ������ʾ��ȡ�������� 
     * @param contenttype ���ݹ��ˡ�0-��ʾ�������ͣ�1-���ı���2-�����ӣ�4-��ͼƬ��8-����Ƶ��0x10-����Ƶ  
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E5%85%B6%E4%BB%96%E7%94%A8%E6%88%B7%E5%8F%91%E8%A1%A8%E6%97%B6%E9%97%B4%E7%BA%BF">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String userTimeline(OAuth oAuth, String format, String pageflag,
			String pagetime, String reqnum, String lastid, String name, String fopenid,
			String type, String contenttype) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
        paramsList.add(new BasicNameValuePair("lastid", lastid));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
        paramsList.add(new BasicNameValuePair("type", type));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(statusesUSERTimelineUrl,
				paramsList, oAuth);
	}

    /**
	 * �û��ἰʱ����
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param pageflag ��ҳ��ʶ��0����һҳ��1�����·�ҳ��2���Ϸ�ҳ��
	 * @param pagetime ��ҳ��ʼʱ�䣨��һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼ʱ�䣬���·�ҳ������һ�����󷵻ص����һ����¼ʱ�䣩 
	 * @param reqnum ÿ�������¼��������1-100����
	 * @param lastid ��pagetime���ʹ�ã���һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼id�����·�ҳ������һ�����󷵻ص����һ����¼id�� 
	 * @param type ��ȡ���� 0x1 ԭ������ 0x2 ת�� 0x8 �ظ� 0x10 �ջ� 0x20 �ἰ 0x40 ���� <br>
     *                        ������ȡ���������ʹ��|����(0x1|0x2)�õ�3����ʱtype=3���ɣ������ʾ��ȡ�������� 
     * @param contenttype ���ݹ��ˡ�0-��ʾ�������ͣ�1-���ı���2-�����ӣ�4-��ͼƬ��8-����Ƶ��0x10-����Ƶ 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E7%94%A8%E6%88%B7%E6%8F%90%E5%8F%8A%E6%97%B6%E9%97%B4%E7%BA%BF">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String mentionsTimeline(OAuth oAuth, String format,
			String pageflag, String pagetime, String reqnum, String lastid,
			String type, String contenttype)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("lastid", lastid));
        paramsList.add(new BasicNameValuePair("type", type));
        paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(
		        statusesMentionsTimelineUrl,
				paramsList, oAuth);
	}

    /**
	 * �ҷ���ʱ����
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��  �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param pageflag ��ҳ��ʶ��0����һҳ��1�����·�ҳ��2���Ϸ�ҳ��
	 * @param pagetime ��ҳ��ʼʱ�䣨��һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼ʱ�䣬���·�ҳ������һ�����󷵻ص����һ����¼ʱ�䣩 
	 * @param reqnum ÿ�������¼��������1-200����
	 * @param lastid ��pagetime���ʹ�ã���һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼id�����·�ҳ������һ�����󷵻ص����һ����¼id�� 
	 * @param type ��ȡ����, 0x1 ԭ������ 0x2 ת�� 0x8 �ظ� 0x10 �ջ� 0x20 �ἰ 0x40 ���� ������ȡ���������|��(0x1|0x2) �õ�3��type=3����,�����ʾ��ȡ��������
	 * @param contenttype ���ݹ��� �����ʾ�������� 1-���ı� 2-������ 4ͼƬ 8-����Ƶ 0x10-����Ƶ
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E6%88%91%E5%8F%91%E8%A1%A8%E6%97%B6%E9%97%B4%E7%BA%BF">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String broadcastTimeline(OAuth oAuth, String format,
			String pageflag, String pagetime, String reqnum, String lastid,
			String type,String contenttype)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("lastid", lastid));
		paramsList.add(new BasicNameValuePair("type", type));
		paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(
		        statusesBroadcastTimelineUrl,
				paramsList, oAuth);
	}
	
    /**
	 * �����û�����ʱ��������
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param pageflag ��ҳ��ʶ��0����һҳ��1�����·�ҳ��2���Ϸ�ҳ��
	 * @param pagetime ��ҳ��ʼʱ�䣨��һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼ʱ�䣬���·�ҳ������һ�����󷵻ص����һ����¼ʱ�䣩 
	 * @param reqnum ÿ�������¼��������1-300����
	 * @param lastid ��pagetime���ʹ�ã���һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼id�����·�ҳ������һ�����󷵻ص����һ����¼id�� 
	 * @param name ����Ҫ��ȡ���û����û�������ѡ�� 
	 * @param fopenid ����Ҫ��ȡ���û���openid����ѡ�� 
     *                             name��fopenid����ѡһ������ͬʱ��������nameֵΪ�� 
	 * @param type ��ȡ����, 0x1 ԭ������ 0x2 ת�� 0x8 �ظ� 0x10 �ջ� 0x20 �ἰ 0x40 ����  ��ȡ����, 0x1 ԭ������ 0x2 ת�� 0x8 �ظ� 0x10 �ջ� 0x20 �ἰ 0x40 ���� 	������ȡ���������|��(0x1|0x2) �õ�3��type=3����,�����ʾ��ȡ��������
	 * @param contenttype ���ݹ��� �����ʾ�������� 1-���ı� 2-������ 4ͼƬ 8-����Ƶ 0x10-����Ƶ
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E5%85%B6%E4%BB%96%E7%94%A8%E6%88%B7%E5%8F%91%E8%A1%A8%E6%97%B6%E9%97%B4%E7%BA%BF%E7%B4%A2%E5%BC%95">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String userTimelineIds(OAuth oAuth, String format,
			String pageflag ,String pagetime,String reqnum,String lastid,
			String name,String fopenid,String type,String contenttype)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("lastid", lastid));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid ));
		paramsList.add(new BasicNameValuePair("type", type));
		paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(
		        statusesUserTimelineIdsUrl,
				paramsList, oAuth);
	}	

	/**
	 * ���û�����ʱ����
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param pageflag ��ҳ��ʶ��0����һҳ��1�����·�ҳ��2���Ϸ�ҳ��
	 * @param pagetime ��ҳ��ʼʱ�䣨��һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼ʱ�䣬���·�ҳ������һ�����󷵻ص����һ����¼ʱ�䣩 
	 * @param reqnum ÿ�������¼��������1-100����
	 * @param lastid ��һҳ ʱ��0,�������·�ҳ������һ�����󷵻ص����һ����¼ID����ҳ��
	 * @param names ����Ҫ��ȡ�û��б��á�,�����������磺abc,bcde,effg����ѡ�����30����
	 * @param fopenids  ����Ҫ��ȡ���û�openid�б����»��ߡ�_�����������磺B624064BA065E01CB73F835017FE96FA_B624064BA065E01CB73F835017FE96FB����ѡ�����30���� <br>
     *                                names��fopenids����ѡһ������ͬʱ��������namesֵΪ�� 
	 * @param type ��ȡ����, 0x1 ԭ������ 0x2 ת�� 0x8 �ظ� 0x10 �ջ� 0x20 �ἰ 0x40 ����
	 * @param contenttype ���ݹ��� �����ʾ�������� 1-���ı� 2-������ 4ͼƬ 8-����Ƶ 0x10-����Ƶ
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E5%A4%9A%E7%94%A8%E6%88%B7%E5%8F%91%E8%A1%A8%E6%97%B6%E9%97%B4%E7%BA%BF">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String usersTimeline(OAuth oAuth, String format,String pageflag,
			String pagetime,String reqnum,String lastid,String names,
			String fopenids, String type,String contenttype)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("lastid", lastid));
		paramsList.add(new BasicNameValuePair("names", names));
		paramsList.add(new BasicNameValuePair("fopenids", fopenids));
		paramsList.add(new BasicNameValuePair("type", type));
		paramsList.add(new BasicNameValuePair("contenttype", contenttype));
		
		return requestAPI.getResource(
		        statusesUsersTimelineUrl,
				paramsList, oAuth);
	}

	public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        statusesHomeTimelineUrl=apiBaseUrl+"/statuses/home_timeline";
        statusesUSERTimelineUrl=apiBaseUrl+"/statuses/user_timeline";
        statusesMentionsTimelineUrl=apiBaseUrl+"/statuses/mentions_timeline";
        statusesBroadcastTimelineUrl=apiBaseUrl+"/statuses/broadcast_timeline";
        statusesUserTimelineIdsUrl=apiBaseUrl+"/statuses/user_timeline_ids";
        statusesUsersTimelineUrl=apiBaseUrl+"/statuses/users_timeline";
    }
}
