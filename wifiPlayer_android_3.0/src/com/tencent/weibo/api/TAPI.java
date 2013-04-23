package com.tencent.weibo.api;

import java.io.File;

import org.apache.http.message.BasicNameValuePair;

import com.tencent.weibo.beans.OAuth;
import com.tencent.weibo.utils.QArrayList;
import com.tencent.weibo.utils.QHttpClient;
 
/**
 * ΢�����API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3">��Ѷ΢������ƽ̨��΢����ص�API�ĵ�<a>
 */

public class TAPI extends BasicAPI{
    
    private String tShowUrl=apiBaseUrl+"/t/show";
    private String tAddUrl=apiBaseUrl+"/t/add";
    private String tAddPicUrl=apiBaseUrl+"/t/add_pic";
    private String tAddPicUrlUrl=apiBaseUrl+"/t/add_pic_url";
    private String tAddVideoUrl=apiBaseUrl+"/t/add_video";
    private String tCommentUrl=apiBaseUrl+"/t/comment";
    private String tDelUrl=apiBaseUrl+"/t/del";
    private String tReAddUrl=apiBaseUrl+"/t/re_add";
    private String tReCountUrl=apiBaseUrl+"/t/re_count";
    private String tReListUrl=apiBaseUrl+"/t/re_list";
    private String tReplyUrl=apiBaseUrl+"/t/reply";
    /**
     * ʹ����Ϻ������ shutdownConnection() �ر��Զ����ɵ����ӹ�����
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     */
    public TAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion ����OAuthVersion������ͨ���������
     * @param qHttpClient ʹ�����е����ӹ�����
     */
    public TAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * ��ȡһ��΢������
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param id ΢��id
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%8E%B7%E5%8F%96%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A%E6%95%B0%E6%8D%AE">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String show(OAuth oAuth, String format, String id) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("id", id));
		
		return requestAPI.getResource(tShowUrl, paramsList, oAuth);
	}

	/**
	 * ����һ��΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�)
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String add(OAuth oAuth, String format, String content,
			String clientip) throws Exception {
		return this.add(oAuth, format, content, clientip, "", "", "");
	}

	/**
	 * ����һ��΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�)
	 * @param jing ���ȣ�������գ�
	 * @param wei γ�ȣ�������գ�
	 * @param syncflag  ΢��ͬ�����ռ�����ǣ���ѡ��0-ͬ����1-��ͬ����Ĭ��Ϊ0��  
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String add(OAuth oAuth, String format, String content,
			String clientip, String jing, String wei, String syncflag) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
        paramsList.add(new BasicNameValuePair("syncflag", syncflag));
		
		return requestAPI.postContent(tAddUrl, paramsList, oAuth);
	}

	/**
	 * ɾ��һ��΢������
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param id ΢��id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%88%A0%E9%99%A4%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String del(OAuth oAuth, String format, String id) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("id", id));
		
		return requestAPI.postContent(tDelUrl, paramsList, oAuth);
	}

	/**
	 * ת��һ��΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�)
	 * @param reid ת�������΢��id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%BD%AC%E6%92%AD%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String reAdd(OAuth oAuth, String format, String content,
			String clientip, String reid) throws Exception {
		return this.reAdd(oAuth, format, content, clientip, "", "", reid);
	}

	/**
	 * ת��һ��΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�)
	 * @param jing ���ȣ�������գ�
	 * @param wei γ�ȣ�������գ�
	 * @param reid ת�������΢��id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%BD%AC%E6%92%AD%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String reAdd(OAuth oAuth, String format, String content,
			String clientip, String jing, String wei, String reid)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
		paramsList.add(new BasicNameValuePair("reid", reid));
		
		return requestAPI.postContent(tReAddUrl, paramsList, oAuth);
	}

	/**
	 * ����һ��΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�)
	 * @param reid ���������΢��id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%82%B9%E8%AF%84%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String comment(OAuth oAuth, String format, String content,
			String clientip, String reid) throws Exception {
		return this.comment(oAuth, format, content, clientip, "", "", reid);
	}

	/**
	 * ����һ��΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�)
	 * @param jing ���ȣ�������գ�
	 * @param wei γ�ȣ�������գ�
	 * @param reid ���������΢��id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%82%B9%E8%AF%84%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String comment(OAuth oAuth, String format, String content,
			String clientip, String jing, String wei, String reid)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
		paramsList.add(new BasicNameValuePair("reid", reid));
		
		return requestAPI.postContent(tCommentUrl, paramsList,
				oAuth);
	}
	
	/**
	 * �ظ�һ��΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�)
	 * @param reid �ظ��ĸ����΢��id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%9B%9E%E5%A4%8D%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A%EF%BC%88%E5%8D%B3%E5%AF%B9%E8%AF%9D%EF%BC%89">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String reply(OAuth oAuth, String format, String content,
			String clientip, String reid) throws Exception {
		return this.reply(oAuth, format, content, clientip, "", "", reid);
	}

	/**
	 * �ظ�һ��΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�)
	 * @param jing ���ȣ�������գ�
	 * @param wei γ�ȣ�������գ�
	 * @param reid �ظ��ĸ����΢��id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%9B%9E%E5%A4%8D%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A%EF%BC%88%E5%8D%B3%E5%AF%B9%E8%AF%9D%EF%BC%89">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String reply(OAuth oAuth, String format, String content,
			String clientip, String jing, String wei, String reid)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
		paramsList.add(new BasicNameValuePair("reid", reid));
		
		return requestAPI.postContent(tReplyUrl, paramsList,
				oAuth);
	}

	/**
	 * ����һ����ͼƬ��΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�)
	 * @param picpath �����Ǳ���ͼƬ·�� �� �����ַ
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�1-����ͼƬ</a>
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%94%A8%E5%9B%BE%E7%89%87URL%E5%8F%91%E8%A1%A8%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�2-����ͼƬ</a>
	 */
	public String addPic(OAuth oAuth, String format, String content,
			String clientip, String picpath) throws Exception {
		return this.addPic(oAuth, format, content, clientip, "", "", picpath, "");
	}

	/**
	 * ����һ����ͼƬ��΢��
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�)
	 * @param jing ���ȣ�������գ�
	 * @param wei γ�ȣ�������գ�
	 * @param picpath �����Ǳ���ͼƬ·�� �� �����ַ
	 * @param syncflag  ΢��ͬ�����ռ�����ǣ���ѡ��0-ͬ����1-��ͬ����Ĭ��Ϊ0��  
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�1-����ͼƬ</a>
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%94%A8%E5%9B%BE%E7%89%87URL%E5%8F%91%E8%A1%A8%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�2-����ͼƬ</a>
	 */
	public String addPic(OAuth oAuth, String format, String content,
			String clientip, String jing, String wei, String picpath,String syncflag)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
        paramsList.add(new BasicNameValuePair("syncflag", syncflag));
		
		if(new File(picpath).exists()){
			//
			QArrayList pic = new QArrayList();
			pic.add(new BasicNameValuePair("pic", picpath));
			return requestAPI.postFile(tAddPicUrl, paramsList, pic,
					oAuth);
		}else{
			paramsList.add(new BasicNameValuePair("pic_url", picpath));
			return requestAPI.postContent(tAddPicUrlUrl, paramsList, oAuth);
		}
		
	}

	/**
	 * ��ȡ΢����ǰ�ѱ�ת������
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param ids ΢��ID�б��á�,������
	 * @param flag  0����ȡת��������1����ȡ�������� 2�����߶���ȡ
 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%BD%AC%E6%92%AD%E6%95%B0%E6%88%96%E7%82%B9%E8%AF%84%E6%95%B0">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�<a>
	 */
	public String reCount(OAuth oAuth, String format, String ids, String flag)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("ids", ids));
        paramsList.add(new BasicNameValuePair("flag", flag));
		
		return requestAPI.getResource(tReCountUrl, paramsList,
				oAuth);
	}

	/**
	 * ��ȡ����΢����ת������/�����б�
	 * 
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param flag  ��ʶ��0��ת���б� 1�������б� 2��������ת���б� 
	 * @param rootid ת����ظ���΢�������id��Դ΢��id��
	 * @param pageflag ��ҳ��ʶ��0����һҳ��1�����·�ҳ��2�����Ϸ�ҳ��
	 * @param pagetime ��ҳ��ʼʱ�䣨��һҳ����0�����Ϸ�ҳ������һ�����󷵻صĵ�һ����¼ʱ�䣬���·�ҳ������һ�����󷵻ص����һ����¼ʱ�䣩
	 * @param reqnum ÿ�������¼��������1-100����
	 * @param twitterid ��ҳ�ã���1-100����0���������·�ҳ������һ�����󷵻ص����һ����¼id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%8E%B7%E5%8F%96%E5%8D%95%E6%9D%A1%E5%BE%AE%E5%8D%9A%E7%9A%84%E8%BD%AC%E5%8F%91%E6%88%96%E7%82%B9%E8%AF%84%E5%88%97%E8%A1%A8">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String reList(OAuth oAuth, String format, String flag,String rootid,
			String pageflag, String pagetime, String reqnum,
			String twitterid) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("flag", flag));
		paramsList.add(new BasicNameValuePair("rootid", rootid));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("twitterid", twitterid));
		
		return requestAPI.getResource(tReListUrl, paramsList,
				oAuth);
	}
 

	
	/**
	 * ������Ƶ΢��
	 * @param oAuth
	 * @param format �������ݵĸ�ʽ �ǣ�json��xml��
	 * @param content  ΢������
	 * @param clientip �û�IP(�Է����û����ڵ�) �û�IP������û������IP,
	 * @param jing ���ȣ�������գ�  ���ȣ�������գ�
	 * @param wei γ�ȣ�������գ�
	 * @param url ��Ƶ��ַ����̨�Զ�������Ƶ��Ϣ��֧��youku,tudou,ku6
     * @param syncflag  ΢��ͬ�����ռ�����ǣ���ѡ��0-ͬ����1-��ͬ����Ĭ��Ϊ0��  
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E8%A7%86%E9%A2%91%E5%BE%AE%E5%8D%9A">��Ѷ΢������ƽ̨�Ϲ��ڴ���API���ĵ�</a>
	 */
	public String addVideo(
			OAuth oAuth, String format, String content,
			String clientip, String jing, String wei,String url,
			String syncflag) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
		paramsList.add(new BasicNameValuePair("url", url));
        paramsList.add(new BasicNameValuePair("syncflag", syncflag));
		
		return requestAPI.postContent(tAddVideoUrl, paramsList,
				oAuth);
	}
	
    public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        tShowUrl=apiBaseUrl+"/t/show";
        tAddUrl=apiBaseUrl+"/t/add";
        tAddPicUrl=apiBaseUrl+"/t/add_pic";
        tAddPicUrlUrl=apiBaseUrl+"/t/add_pic_url";
        tAddVideoUrl=apiBaseUrl+"/t/add_video";
        tCommentUrl=apiBaseUrl+"/t/comment";
        tDelUrl=apiBaseUrl+"/t/del";
        tReAddUrl=apiBaseUrl+"/t/re_add";
        tReCountUrl=apiBaseUrl+"/t/re_count";
        tReListUrl=apiBaseUrl+"/t/re_list";
        tReplyUrl=apiBaseUrl+"/t/reply";
    }
}
