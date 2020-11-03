package com.gvpt.admintool.web.controller.login;

import com.gvpt.admintool.bean.UserAdmin;
import com.gvpt.admintool.bean.auth.SessionModel;
import com.gvpt.admintool.business.service.AuditTrailService;
import com.gvpt.admintool.business.service.UserAdminService;
import com.gvpt.admintool.common.util.CommonUtils;
import com.gvpt.admintool.web.common.enumerations.UserActivity;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class LogoutSuccessHandlerImpl extends SimpleUrlLogoutSuccessHandler {
	Logger log = LoggerFactory.getLogger(LogoutSuccessHandlerImpl.class);

	@Autowired
	UserAdminService userService;
	
	@Autowired
	AuditTrailService auditService;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Value("${ipAddress.isforwardedOn}")
	private boolean isForwardedOn;

	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (authentication != null) {
			UserAdmin user = commonUtils.getLoginUser(authentication);
			String ipAddress = CommonUtils.getRemoteIpAddress(request,
					isForwardedOn);
			auditService.saveAudit(
					user.getUserId(),
					UserActivity.LOGOUT,
					new Date(),
					String.format("%s (%s)", request.getRequestURI(),
							user.getEmail()), ipAddress);
		}
		CommonUtils.clearSession(request);

		super.handle(request, response, authentication);
	}

}
