package com.gvpt.admintool.web.controller.login;

import com.gvpt.admintool.bean.UserAdmin;
import com.gvpt.admintool.business.service.AuditTrailService;
import com.gvpt.admintool.business.service.UserAdminService;
import com.gvpt.admintool.common.util.CommonUtils;
import com.gvpt.admintool.web.common.enumerations.UserActivity;
import com.gvpt.admintool.web.controller.NavigationController;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class SuccessLoginHandlerImpl extends SimpleUrlAuthenticationSuccessHandler
	 {

	Logger log = LogManager.getLogger(SuccessLoginHandlerImpl.class);

	@Autowired
	UserAdminService userService;
	
	@Autowired
	AuditTrailService auditService;
	
	@Autowired
	NavigationController navigation;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Value("${ipAddress.isforwardedOn}")
	private boolean isForwardedOn;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		log.debug("audit on login");
		UserAdmin user = commonUtils.getLoginUser(authentication);
		log.debug("user id : " + authentication.getName());
		String ipAddress = CommonUtils.getRemoteIpAddress(request, isForwardedOn);
		
		auditService.saveAudit(user.getUserId(), UserActivity.LOGIN, new Date(), 
				String.format("%s (%s)", request.getRequestURI(), user.getEmail()), ipAddress);
		navigation.populateDefaultModel(request);
		handle(request, response, authentication);
	    clearAuthenticationAttributes(request);
	}
}
