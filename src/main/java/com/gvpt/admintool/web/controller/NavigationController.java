package com.gvpt.admintool.web.controller;

import com.gvpt.admintool.bean.AccessRight;
import com.gvpt.admintool.bean.AccessRole;
import com.gvpt.admintool.bean.AuditTrail;
import com.gvpt.admintool.bean.UserAdmin;
import com.gvpt.admintool.bean.auth.SessionModel;
import com.gvpt.admintool.bean.auth.UserPrincipal;
import com.gvpt.admintool.business.service.AccessRightService;
import com.gvpt.admintool.business.service.AccessRoleService;
import com.gvpt.admintool.business.service.AuditTrailService;
import com.gvpt.admintool.business.service.UserAdminService;
import com.gvpt.admintool.common.util.CommonUtils;
import com.gvpt.admintool.web.common.enumerations.AppConstants;
import com.gvpt.admintool.web.listitem.AccessRightListItem;
import com.gvpt.admintool.web.listitem.ChildMenuListItem;
import com.gvpt.admintool.web.listitem.MenuListItem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class NavigationController {
	@Resource
    private AccessRightService accessRightService;
	@Resource
	private AccessRoleService accessRoleService;
	@Resource
    private UserAdminService userAdminService;
	@Resource
    private AuditTrailService auditTrailService;
	@Autowired
	private CommonUtils commonUtils;
	@Autowired
	private ApplicationContext appContext;
	@Value("${nav.menu.max.size}")
	private int maxMenuSize;
	
	private static Logger log= Logger.getLogger(NavigationController.class);
	
	public List<MenuListItem> getUserNavigation(){
		log.info("NavigationController method getUserNavigation : start...");
		List<MenuListItem> listOfMenu = new ArrayList<MenuListItem>();
		List<ChildMenuListItem> childMenus = new ArrayList<ChildMenuListItem>();

		UserAdmin userLogin = commonUtils.getLoginUser();
		AccessRole accessRole = userLogin.getAccessRole();
		if (accessRole != null) {
			List<AccessRight> listOfAccessRight = new ArrayList<>();
			if (accessRole.getRoleTitle()
					.equalsIgnoreCase(AppConstants.superAdmin)) {
				listOfAccessRight = accessRightService.findAllOrderById();
			} else {
				listOfAccessRight = accessRoleService.findById(accessRole.getAccessRoleId()).getListOfAccessRight();
			}
			for (AccessRight accessRight : listOfAccessRight) {
				String path = appContext.getMessage(AppConstants.NAV+accessRight.getAccessRightId().toString(), new Object[]{}, Locale.US);
				MenuListItem menu = new MenuListItem(path, accessRight.getName());
				String childMenuLabel = null;
				String childMenuUrl = null;

				try {
					for (int i = 1; i <= maxMenuSize; i++) {
						String url = AppConstants.NAV+accessRight.getAccessRightId().toString()+
								"."+AppConstants.CHILD_MENU+i+"."+AppConstants.URL;
						String label = AppConstants.NAV+accessRight.getAccessRightId().toString()+
								"."+AppConstants.CHILD_MENU+i+"."+AppConstants.LABEL;
						childMenuLabel = appContext.getMessage(label, 
								new Object[]{}, Locale.US);
						childMenuUrl = appContext.getMessage(url, 
								new Object[]{}, Locale.US);
						if(!url.equals(childMenuUrl) && !label.equals(childMenuLabel)){
							childMenus.add(new ChildMenuListItem(childMenuUrl, childMenuLabel));
						}
					}
				} catch (NoSuchMessageException e) {}
				if(childMenus.size() > 0){
					menu.setChildMenus(childMenus);
				}
				listOfMenu.add(menu);
			}
		}
		log.info("NavigationController method getUserNavigation : User Login = "+userLogin.getName());
		log.info("NavigationController method getUserNavigation : end...");
		return listOfMenu;
	}
	
	public List<MenuListItem> getUserControlPanel(){
		log.info("NavigationController method getUserControlPanel : start...");
		List<MenuListItem> menu = new ArrayList<MenuListItem>();
		UserAdmin userLogin = commonUtils.getLoginUser();
		AccessRole accessRole = null;
		accessRole = userLogin.getAccessRole();
		if (accessRole != null) {
			List<AccessRight> listOfAccessRight = new ArrayList<>();
			if (accessRole.getRoleTitle()
					.equalsIgnoreCase(AppConstants.superAdmin)) {
				listOfAccessRight = accessRightService.findAllOrderById();
			} else {
				listOfAccessRight = accessRoleService.findById(accessRole.getAccessRoleId()).getListOfAccessRight();
			}
			for (AccessRight accessRight : listOfAccessRight) {
				String path = appContext.getMessage(AppConstants.NAV+accessRight.getAccessRightId().toString(), new Object[]{}, Locale.US);
				String label = appContext.getMessage(AppConstants.CP+accessRight.getAccessRightId().toString(), new Object[]{}, Locale.US);
				if(!label.isEmpty()){
					menu.add(new MenuListItem(path, label));
				}
			}
		}
		log.info("NavigationController method getUserControlPanel : User Login = "+userLogin.getName());
		log.info("NavigationController method getUserControlPanel : end...");
		return menu;
	}
	
	public void populateDefaultModel(Model model, HttpServletRequest request){
		log.info("NavigationController method populateDefaultModel : start...");
		List<MenuListItem> nav = (List<MenuListItem>) request.getSession().getAttribute("listOfNav");
		List<MenuListItem> menu = (List<MenuListItem>) request.getSession().getAttribute("listOfMenu");
		String name = (String)request.getSession().getAttribute("userName");
		String lastLogin = (String)request.getSession().getAttribute("lastLogin");
		UserAdmin user = commonUtils.getLoginUser();
		if(name==null || name.isEmpty()){
			name =user.getName();
			request.getSession().setAttribute("userName",name);
			AuditTrail auditTrail = auditTrailService.findLastLoginById(user.getUserId());
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("d MMM yyyy, h:mm a");
			request.getSession().setAttribute("lastLogin", simpleDateFormat.format(auditTrail.getActionDatetime()));
		}
		
		if(nav == null || nav.size() == 0){
			nav = getUserNavigation();
			request.getSession().setAttribute("listOfNav", nav);
		}
		
		if(menu == null || menu.size() == 0){
			menu = getUserControlPanel();
			request.getSession().setAttribute("listOfMenu", menu);
		}
		
		model.addAttribute("userName", name);
		model.addAttribute("lastLogin", lastLogin);
		model.addAttribute("listOfNav", nav);
		model.addAttribute("listOfMenu", menu);
		log.info("NavigationController method populateDefaultModel get username:  = "+name);
		log.info("NavigationController method populateDefaultModel get lastLogin:  = "+lastLogin);
		String redirect = (request.getRequestURI()).replace(request.getContextPath(),"");
		request.getSession().setAttribute(AppConstants.MAIN_PATH, redirect);
		log.info("NavigationController method populateDefaultModel : end...");
	}
	
	public void populateDefaultModel(HttpServletRequest request){
		log.info("NavigationController method populateDefaultModel : start...");
		List<MenuListItem> nav = (List<MenuListItem>) request.getSession().getAttribute("listOfNav");
		List<MenuListItem> menu = (List<MenuListItem>) request.getSession().getAttribute("listOfMenu");
		String name = (String)request.getSession().getAttribute("userName");
		UserAdmin user = commonUtils.getLoginUser();
		if(name==null || name.isEmpty()){
			name = user.getName();
			request.getSession().setAttribute("userName",name);
			AuditTrail auditTrail = auditTrailService.findLastLoginById(user.getUserId());
			log.info("NavigationController method populateDefaultModel save auditTrail:  = "+auditTrail);
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("d MMM yyyy, h:mm a");
			request.getSession().setAttribute("lastLogin", simpleDateFormat.format(auditTrail.getActionDatetime()));
		}
		
		if(nav == null || nav.size() == 0){
			nav = getUserNavigation();
			request.getSession().setAttribute("listOfNav", nav);
		}
		
		if(menu == null || menu.size() == 0){
			menu = getUserControlPanel();
			request.getSession().setAttribute("listOfMenu", menu);
		}
		String redirect = (request.getRequestURI()).replace(request.getContextPath(),"");
		request.getSession().setAttribute(AppConstants.MAIN_PATH, redirect);
		log.info("NavigationController method populateDefaultModel get username:  = "+name);
		log.info("NavigationController method populateDefaultModel get lastLogin:  = "+request.getSession().getAttribute("lastLogin"));
		log.info("NavigationController method populateDefaultModel : end...");
	}

}
