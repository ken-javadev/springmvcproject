package com.gvpt.admintool.common.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.gvpt.admintool.bean.UserAdmin;
import com.gvpt.admintool.bean.auth.UserPrincipal;
import com.gvpt.admintool.business.service.UserAdminService;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service(value="commonUtils")
public class CommonUtils {
	
	@Resource
	private UserAdminService userService;
	private static Logger logger = Logger.getLogger(CommonUtils.class);
	public static String getRemoteIpAddress(HttpServletRequest request, boolean isXForwarded) {
		// is client behind something?
		/*String ipAddress = request.getHeader("X-FORWARDED-FOR");
		log.info("ipAddress from x-forwarded-for = " + ipAddress);*/
//		logger.info("isXForwarded: "+isXForwarded);
		
		String ipAddress = request.getRemoteAddr();
//		logger.info("ipAddress from remote addr = " + ipAddress);
		
		if(isXForwarded) {
			String ipAddressXForwarded = request.getHeader("X-FORWARDED-FOR");
//			logger.info("ipAddress from x-forwarded-for : " + ipAddressXForwarded);
			
			if(null != ipAddressXForwarded) {
				String[] arrIpAddressXForwarded= ipAddressXForwarded.split(",");
				if(arrIpAddressXForwarded.length > 0) {
					ipAddress= arrIpAddressXForwarded[0];
				}
			}
		}
		
		return ipAddress;
	}
	
	public static void clearSession(HttpServletRequest request) {
		
		request.getSession().invalidate();
	}
	
	public UserAdmin getLoginUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal sessionUser = (UserPrincipal) auth.getPrincipal();
		UserAdmin user = sessionUser.getUser();
		return user;
	}
	
	public UserAdmin getLoginUser(Authentication authentication) {
		UserPrincipal sessionUser = (UserPrincipal) authentication.getPrincipal();
		UserAdmin user = sessionUser.getUser();
		return user;
	}
	
	public static Predicate<String> getPredicateBySystemNameEndWith(final String filter){
		return new Predicate<String>(){

			@Override
			public boolean apply(String finder) {
				if(finder.startsWith(filter)){
					return true;
				}
				return false;
			}
			
		};
	}
	
	/**
	 * filter list which start with filter parameter
	 * @param filter
	 * @param unfiltered
	 * @return
	 */
	public static List<String> getListStartWith(String filter, List<String> unfiltered){
		Iterable<String> filtered = Iterables.filter(unfiltered, getPredicateBySystemNameEndWith(filter));
		return Lists.newArrayList(filtered);
	}
	
	public static HttpClient createHttpClientAcceptsUntrustedCerts() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
	    HttpClientBuilder b = HttpClientBuilder.create();
	 
	    // setup a Trust Strategy that allows all certificates.
	    //
	    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
	        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	            return true;
	        }
	    }).build();
	    b.setSslcontext( sslContext);
	 
	    // don't check Hostnames, either.
	    //      -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
	    HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
	 
	    // here's the special part:
	    //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
	    //      -- and create a Registry, to register it.
	    //
	    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.getSocketFactory())
	            .register("https", sslSocketFactory)
	            .build();
	 
	    // now, we create connection-manager using our Registry.
	    //      -- allows multi-threaded use
	    PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
	    b.setConnectionManager( connMgr);
	 
	    // finally, build the HttpClient;
	    //      -- done!
	    HttpClient client = b.build();
	    return client;
	}
	
	public static Date getShowStartDatetime(Date showStartDate, String showTime){
		logger.info("show date : " + showStartDate);
		logger.info("show Time : " + showTime);
		String hour = "0";
		String minute = "0";
		if(showTime.length() == 4){
			hour = showTime.substring(0, 2);
			if(hour.startsWith("0")){
				hour = hour.substring(1);
				logger.info("hour : " + hour);
			}
			minute = showTime.substring(2, 4);
			if(minute.startsWith("0")){
				minute = minute.substring(1);
				logger.info("minute : " + minute);
			}
		} else if(showTime.length() == 3){
			hour = showTime.substring(0, 1);
			logger.info("hour : " + hour);
			minute = showTime.substring(1, 3);
			logger.info("minute : " + minute);
		} else {
			minute = showTime;
			logger.info("minute : " + minute);
		}
		Date showStartDatetime = DateUtil.setHourAndMinute(showStartDate, Integer.valueOf(hour), Integer.valueOf(minute));
		logger.info("showStartDatetime = " + showStartDatetime);
		return showStartDatetime;
	}
}
