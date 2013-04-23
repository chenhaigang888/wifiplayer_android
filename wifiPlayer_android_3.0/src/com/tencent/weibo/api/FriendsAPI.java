package com.tencent.weibo.api;

import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;

/**
 * ��ϵ�����API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%85%B3%E7%B3%BB%E9%93%BE%E7%9B%B8%E5%85%B3">��Ѷ΢������ƽ̨�Ϲ�ϵ����ص�API�ĵ�<a>
 */

public class FriendsAPI extends BasicAPI {
 
    private String friendsFansListUrl=apiBaseUrl+"/friends/fanslist";
    private String friendsIdolListUrl=apiBaseUrl+"/friends/idollist";
    private String friendsAddUrl=apiBaseUrl+"/friends/add";
    private String friendsDelUrl=apiBaseUrl+"/friends/del";
    private String friendsCheckUrl=apiBaseUrl+"/friends/check";
    private String friendsUserFansListUrl=apiBaseUrl+"/friends/user_fanslist";
    private String friendsUserIdolListUrl=apiBaseUrl+"/friends/user_idollist";
    private String friendsUserSpecialListUrl=apiBaseUrl+"/friends/user_speciallist";
    private String friendsFansListSUrl=apiBaseUrl+"/friends/fanslist_s";

    /**
     * ʹ����Ϻ������ shutdownConnection() �ر��Զ����ɵ����ӹ�����
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     */
    public FriendsAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     * @param qHttpClient ʹ�����е����ӹ�����
     */
    public FriendsAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * �ҵ������б�
	 * 
	 * @param oAuth ��׼����
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param reqnum  �������(1-30)
	 * @param startindex ��ʼλ�ã���һҳ��0���������·�ҳ�����reqnum*��page-1������
	 * @param mode  ��ȡģʽ��Ĭ��Ϊ0 
     *                           <li>mode=0����ģʽ���·�˿��ǰ��ֻ����ȡ1000�� 
     *                           <li>mode=1����ģʽ����ȡȫ����˿���Ϸ�˿��ǰ 
     * @param install  ���˰�װӦ�ú��ѣ���ѡ�� <br>
     *                           0-�����Ǹò�����1-��ȡ�Ѱ�װӦ�ú��ѣ�2-��ȡδ��װӦ�ú��� 
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%88%91%E7%9A%84%E5%90%AC%E4%BC%97%E5%88%97%E8%A1%A8">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String fanslist(OAuth oAuth, String format, String reqnum,
			String startindex, String mode, String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
        paramsList.add(new BasicNameValuePair("mode", mode));
        paramsList.add(new BasicNameValuePair("install", install));
		
		return requestAPI.getResource(friendsFansListUrl,
				paramsList, oAuth);
	}

	/**
	 * �����������б�
	 * 
	 * @param oAuth ��׼����
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param reqnum  �������(1-30)
	 * @param startindex ��ʼλ�ã���һҳ��0���������·�ҳ�����reqnum*��page-1������
     * @param install  ���˰�װӦ�ú��ѣ���ѡ�� <br>
     *                           0-�����Ǹò�����1-��ȡ�Ѱ�װӦ�ú��ѣ�2-��ȡδ��װӦ�ú��� 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%88%91%E6%94%B6%E5%90%AC%E7%9A%84%E4%BA%BA%E5%88%97%E8%A1%A8">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String idollist(OAuth oAuth, String format, String reqnum,
			String startindex, String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
        paramsList.add(new BasicNameValuePair("install", install));
		
		return requestAPI.getResource(friendsIdolListUrl,
				paramsList, oAuth);
	}

	/**
	 * ����ĳ���û�<br>
	 *
	 * @param oAuth ��׼����
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param name ���˵��ʻ����б���","����
	 * @param fopenids  ����Ҫ��ȡ���û�openid�б����»��ߡ�_�����������磺B624064BA065E01CB73F835017FE96FA_B624064BA065E01CB73F835017FE96FB����ѡ�����30���� <br>
	 *                                name��fopenids����ѡһ������ͬʱ��������nameֵΪ��
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%94%B6%E5%90%AC%E6%9F%90%E4%B8%AA%E7%94%A8%E6%88%B7">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String add(OAuth oAuth, String format, String name,String fopenids ) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("name", name));
		paramsList.add(new BasicNameValuePair("fopenids", fopenids ));
		
		return requestAPI.postContent(friendsAddUrl, paramsList,
		        oAuth);
	}

	/**
	 * ȡ������ĳ���û�
	 * 
	 * @param oAuth ��׼����
	 * @param format �����ݵĸ�ʽ �ǣ�json��xml��
	 * @param name ���˵��ʻ���
	 * @param fopenid  ���˵�openid����ѡ�� <br>
	 *                              name��fopenid����ѡһ������ͬʱ��������nameֵΪ�� 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E5%8F%96%E6%B6%88%E6%94%B6%E5%90%AC%E6%9F%90%E4%B8%AA%E7%94%A8%E6%88%B7">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String del(OAuth oAuth, String format, String name,String fopenid) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
		
		return requestAPI.postContent(friendsDelUrl, paramsList,
		        oAuth);
	}
	
	/**
	 * ����Ƿ��ҵ����ڻ�����������
	 * 
	 * @param oAuth ��׼����
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param names names  �����˵��ʻ����б��ö��š�,���ָ�����aaa,bbb�����30������ѡ��  
     * @param fopenids  �����˵ĵ��û�openid�б��á�_�����������磺B624064BA065E01CB73F835017FE96FA_B624064BA065E01CB73F835017FE96FB����ѡ�����30���� <br>
     *                               names��fopenids����ѡһ������ͬʱ��������namesֵΪ�� 
	 * @param flag 0 ������ڣ�1����������� 2 ���ֹ�ϵ�����
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%A3%80%E6%B5%8B%E6%98%AF%E5%90%A6%E6%88%91%E7%9A%84%E5%90%AC%E4%BC%97%E6%88%96%E6%94%B6%E5%90%AC%E7%9A%84%E4%BA%BA">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String check(OAuth oAuth, String format, String names,String fopenids,String flag) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("names", names));
		paramsList.add(new BasicNameValuePair("fopenids", fopenids));
        paramsList.add(new BasicNameValuePair("flag", flag));
		
		return requestAPI.getResource(friendsCheckUrl, paramsList,
		        oAuth);
	}
	
	/**
     * ��ȡ�����û������б�
     * 
     * @param oAuth ��׼����
     * @param format �������ݵĸ�ʽ �ǣ�json��xml��
     * @param reqnum �������(1-30)
     * @param startindex ��ʼλ�ã���һҳ��0���������·�ҳ���reqnum*��page-1������ 
     * @param name �û��ʻ���
     * @param fopenid  ���˵�openid����ѡ�� <br>
     *                              name��fopenid����ѡһ������ͬʱ��������nameֵΪ�� 
     * @param mode  ��ȡģʽ��Ĭ��Ϊ0 
     *                           <li>mode=0����ģʽ���·�˿��ǰ��ֻ����ȡ1000�� 
     *                           <li>mode=1����ģʽ����ȡȫ����˿���Ϸ�˿��ǰ 
     * @param install  ���˰�װӦ�ú��ѣ���ѡ�� <br>
     *                           0-�����Ǹò�����1-��ȡ�Ѱ�װӦ�ú��ѣ�2-��ȡδ��װӦ�ú��� 
     * @return
     * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E5%85%B6%E4%BB%96%E5%B8%90%E6%88%B7%E5%90%AC%E4%BC%97%E5%88%97%E8%A1%A8">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
     */
    public String userFanslist(OAuth oAuth, String format, String reqnum,
            String startindex,String name,String fopenid,String mode,String install) throws Exception {
        QArrayList paramsList = new QArrayList();
        paramsList.add(new BasicNameValuePair("format", format));
        paramsList.add(new BasicNameValuePair("reqnum", reqnum));
        paramsList.add(new BasicNameValuePair("startindex", startindex));
        paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
        paramsList.add(new BasicNameValuePair("mode", mode));
        paramsList.add(new BasicNameValuePair("install", install));
        
        return requestAPI.getResource(friendsUserFansListUrl,
                paramsList, oAuth);
    }

	/**
	 * �����ʻ����������б� 
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param reqnum �������(1-30)
	 * @param startindex ��ʼλ�ã���һҳ��0���������·�ҳ���reqnum*��page-1������ 
	 * @param name �û��ʻ���
	 * @param fopenid  ���˵�openid����ѡ�� <br>
     *                              name��fopenid����ѡһ������ͬʱ��������nameֵΪ�� 
     * @param install  ���˰�װӦ�ú��ѣ���ѡ�� <br>
     *                           0-�����Ǹò�����1-��ȡ�Ѱ�װӦ�ú��ѣ�2-��ȡδ��װӦ�ú��� 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E5%85%B6%E4%BB%96%E5%B8%90%E6%88%B7%E6%94%B6%E5%90%AC%E7%9A%84%E4%BA%BA%E5%88%97%E8%A1%A8">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String userIdollist(OAuth oAuth, String format, String reqnum,
			String startindex,String name, String fopenid,String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
        paramsList.add(new BasicNameValuePair("install", install));
		
		return requestAPI.getResource(friendsUserIdolListUrl,
				paramsList, oAuth);
	}
	
	/**
	 * �����ʻ��ر����������б�
	 * 
	 * @param oAuth 
	 * @param format �������ݵĸ�ʽ��json��xml��
	 * @param reqnum �������(1-30)
	 * @param startindex ��ʼλ�ã���һҳ��0���������·�ҳ���reqnum*��page-1������
	 * @param name �û��ʻ�������ѡ��
     * @param fopenid  ���˵�openid����ѡ�� <br>
     *                              name��fopenid����ѡһ������ͬʱ��������nameֵΪ�� 
     * @param install  ���˰�װӦ�ú��ѣ���ѡ�� <br>
     *                           0-�����Ǹò�����1-��ȡ�Ѱ�װӦ�ú��ѣ�2-��ȡδ��װӦ�ú��� 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E5%85%B6%E4%BB%96%E5%B8%90%E6%88%B7%E7%89%B9%E5%88%AB%E6%94%B6%E5%90%AC%E7%9A%84%E4%BA%BA%E5%88%97%E8%A1%A8">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String userSpeciallist(OAuth oAuth, String format, String reqnum,
			String startindex,String name, String fopenid,String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
		paramsList.add(new BasicNameValuePair("name", name));
        paramsList.add(new BasicNameValuePair("fopenid", fopenid));
        paramsList.add(new BasicNameValuePair("install", install));
		
		return requestAPI.getResource(friendsUserSpecialListUrl,
				paramsList, oAuth);
	}
	
	/**
	 * �ҵķ�˿�б�����Ϣ��200��)
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param reqnum  �������(1-200)
	 * @param startindex ��ʼλ�ã���һҳ��0���������·�ҳ�����reqnum*��page-1������
     * @param install  ���˰�װӦ�ú��ѣ���ѡ�� <br>
     *                           0-�����Ǹò�����1-��ȡ�Ѱ�װӦ�ú��ѣ�2-��ȡδ��װӦ�ú��� 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%B8%90%E6%88%B7%E7%9B%B8%E5%85%B3/%E6%88%91%E7%9A%84%E5%90%AC%E4%BC%97%E5%88%97%E8%A1%A8%EF%BC%8C%E7%AE%80%E5%8D%95%E4%BF%A1%E6%81%AF%EF%BC%88200%E4%B8%AA%EF%BC%89">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String fanslistS(OAuth oAuth, String format, String reqnum,
			String startindex,String install) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("startindex", startindex));
        paramsList.add(new BasicNameValuePair("install", install));
		
		return requestAPI.getResource(friendsFansListSUrl,
				paramsList, oAuth);
	}	

	public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        friendsFansListUrl=apiBaseUrl+"/friends/fanslist";
        friendsIdolListUrl=apiBaseUrl+"/friends/idollist";
        friendsAddUrl=apiBaseUrl+"/friends/add";
        friendsDelUrl=apiBaseUrl+"/friends/del";
        friendsCheckUrl=apiBaseUrl+"/friends/check";
        friendsUserFansListUrl=apiBaseUrl+"/friends/user_fanslist";
        friendsUserIdolListUrl=apiBaseUrl+"/friends/user_idollist";
        friendsUserSpecialListUrl=apiBaseUrl+"/friends/user_speciallist";
        friendsFansListSUrl=apiBaseUrl+"/friends/fanslist_s";
    }	
}
