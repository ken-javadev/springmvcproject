package com.gvpt.admintool.web.controller.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

public class AccessDeniedHandler extends AccessDeniedHandlerImpl {

	Logger log = LoggerFactory.getLogger(AccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest _request,
			HttpServletResponse _response, AccessDeniedException _exception)
			throws IOException, ServletException {
		setErrorPage("/accessDenied"); // this is a standard Spring MVC
										// Controller

		// any time a user tries to access a part of the application that they
		// do not have rights to lock their account
		super.handle(_request, _response, _exception);
	}

}
