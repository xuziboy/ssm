package com.imooc.o2o.util.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.UserAccessToken;
import com.imooc.o2o.dto.WechatUser;
import com.imooc.o2o.entity.Personinfo;

/**
 * 公众平台通用接口工具类
 * 
 * @author liuyq
 * @date 2013-08-09
 */
public class WechatUtil {
	private static Logger log = LoggerFactory.getLogger(WechatUtil.class);
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * 发起https请求并获取结果
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	public static String httpsRequest(String requestUrl,String requestMethod,String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			//创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			//从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection)url.openConnection();
			 httpUrlConn.setSSLSocketFactory(ssf);
			 httpUrlConn.setDoOutput(true);
			 httpUrlConn.setDoInput(true);
			 httpUrlConn.setUseCaches(false);
			 //设置请求方式（GET/POST）
			 httpUrlConn.setRequestMethod(requestMethod);
			 
			 if("GET".equalsIgnoreCase(requestMethod)) {
				 httpUrlConn.connect();
			 }
			 if(null!=outputStr) {
				 OutputStream outputStream = httpUrlConn.getOutputStream();
				 //主义编码格式，防止中文乱码
				 outputStream.write(outputStr.getBytes("UTF-8"));
				 outputStream.close();
			 }
			 //将返回的输入流转换成字符串
			 	InputStream inputStream = httpUrlConn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				inputStreamReader.close();
				// 释放资源
				inputStream.close();
				inputStream = null;
				httpUrlConn.disconnect();
				log.debug("http buffer:"+buffer.toString());
			} catch (ConnectException ce) {
				log.error("Weixin server connection timed out.");
			} catch(Exception e) {
				log.error("http request error:{}",e);
			}
			return buffer.toString();
		}
	
	
	/**
	 * 获取access_token
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static UserAccessToken getUserAccessToken(String code)throws IOException {
		//测试号信息里的appId
		String appId ="wx43b54528ab643a46";
		log.debug("appId:"+ appId);
		//测试号信息里的appsecret
		String appsecret="388175abf66555a98efd1bb5ed20ae38";
		log.debug("appsecret:"+ appsecret);
		//根据传入的Code，拼接出访问微信定义好的接口的url
		String url= "https://api.weixin.qq.com/sns/oauth2/access_token?appId="+appId +"&secret="+appsecret
				+"&code="+code+"&grant_type=authorization_code";
//		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appsecret;
		//向相应url发送请求获取token json字符串
		String tokenStr = httpsRequest(url,"GET",null);
		log.debug("userAccessToken:"+tokenStr);
		UserAccessToken token = new UserAccessToken();
		ObjectMapper mapper = new ObjectMapper();
			//将json字符串转换成相应对象
		try {
			token = mapper.readValue(tokenStr, UserAccessToken.class);
		}catch(Exception e) {
			log.error("获取用户的accessToken失败："+e.getMessage());
		}
		
		if(token == null) {
			log.error("获取用户accessToken失败");
			return null;
		}
		return token;
	}
	
	public static Personinfo getPersonInfoFromRequest(WechatUser user) {
		Personinfo personinfo = new Personinfo();
		personinfo.setName(user.getNickName());
		personinfo.setGender(user.getSex() +"");
		personinfo.setProfileImg(user.getHeadimgurl());
		personinfo.setEnableStatus(1);
		return personinfo;
	}
	
	public static WechatUser getUserInfo(String accessToken, String openId) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openId + "&lang=zh_CN";
//		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
		String jsonObject = httpsRequest(url, "GET", null);
		WechatUser user = new WechatUser();
		ObjectMapper mapper = new ObjectMapper();
		try {
			user = mapper.readValue(jsonObject, WechatUser.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user.getOpenId() == null) {
			log.debug("获取用户信息失败。");
			return null;
		}
		// user.setUnionid(jsonObject.getString("unionid"));
		return user;
	}
	
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */

	
}