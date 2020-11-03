/*
 * Created on 20 Jun 2017 ( Time 10:26:31 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.gvpt.admintool.web.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gvpt.admintool.bean.AccessRight;
import com.gvpt.admintool.bean.AccessRole;
import com.gvpt.admintool.bean.AuditTrail;
import com.gvpt.admintool.bean.UserAdmin;
import com.gvpt.admintool.business.service.AccessRightService;
import com.gvpt.admintool.business.service.AccessRoleService;
import com.gvpt.admintool.business.service.AuditTrailService;
import com.gvpt.admintool.business.service.UserAdminService;
import com.gvpt.admintool.common.util.CommonUtils;
import com.gvpt.admintool.data.repository.specifications.AccessRoleSpecifications;
//--- Common classes
import com.gvpt.admintool.web.common.AbstractController;
import com.gvpt.admintool.web.common.FormMode;
import com.gvpt.admintool.web.common.Message;
import com.gvpt.admintool.web.common.MessageType;
import com.gvpt.admintool.web.common.enumerations.UserActivity;
import com.gvpt.admintool.web.datatable.commons.DataTableRequest;
import com.gvpt.admintool.web.datatable.commons.DataTableResponse;
import com.gvpt.admintool.web.datatable.enumerations.AccessRoleIndexEnum;
import com.gvpt.admintool.web.listitem.AccessRightListItem;
import com.gvpt.admintool.web.listitem.AccessRightListItemTemporary;
import com.gvpt.admintool.web.listitem.UserListItem;
import com.gvpt.admintool.web.util.ParamUtil;

//-- Datatable

//--- Entities

//--- Services 

//--- List Items 

/**
 * Spring MVC controller for 'AccessRole' management.
 */
@Controller
@RequestMapping("/accessRole")
public class AccessRoleController extends AbstractController {

	// --- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "accessRole";
	private static final String MAIN_LIST_NAME = "list";

	// --- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM = "accessRole/form";
	private static final String JSP_LIST = "accessRole/list";

	// --- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE = "/accessRole/create";
	private static final String SAVE_ACTION_UPDATE = "/accessRole/update";

	// --- Main entity service
	@Resource
	private AccessRoleService accessRoleService; // Injected by Spring
	// --- Other service(s)
	@Resource
	private AccessRightService accessRightService; // Injected by Spring
	@Resource
	private UserAdminService userService; // Injected by Spring
	@Autowired
	AuditTrailService auditService;
	@Autowired
	NavigationController navigation;
	@Autowired
	CommonUtils commonUtils;

	List<AccessRightListItemTemporary> listAccessRightListItemTemporary = new ArrayList<AccessRightListItemTemporary>();

	@Value("${ipAddress.isforwardedOn}")
	private boolean isForwardedOn;

	public AccessRoleController() {
		super(AccessRoleController.class, MAIN_ENTITY_NAME);
		log("AccessRoleController created.");
	}
	
	private Boolean checkDuplicate(AccessRole accessRole){
		log("AccesRoleController method checkDuplicate: start...");
		log("AccesRoleController method checkDuplicate accessRoleName : " + accessRole.getRoleTitle());
		boolean check=false;
		List<AccessRole> list=accessRoleService.findAll();
		for(AccessRole obj:list){
			if(obj.getRoleTitle().equals(accessRole.getRoleTitle())){
				check=true;
				break;
			}
		}
		log("AccesRoleController method checkDuplicate  existing : " + check);
		log("AccesRoleController method checkDuplicate: end...");
		return check;	
	}

	@RequestMapping(value = "/checkAll")
	// GET or POST
	public @ResponseBody Boolean checkAll(HttpServletResponse response,
			HttpServletRequest httpServletRequest) {
		log("AccesRoleController method checkAll: start...");
		Boolean checkAll = true;
		for (int i = 0; i < listAccessRightListItemTemporary.size(); i++) {
			if (!listAccessRightListItemTemporary.get(i).getCheck()) {
				checkAll = false;
			}
		}
		log("AccesRoleController method checkAll: end...");
		return checkAll;
	}

	private void populateListOfAccessRightItems(Model model) {
		log("AccesRoleController method populateListOfAccessRightItems: start...");
		List<AccessRight> list = accessRightService.findAll();
		List<AccessRightListItem> items = new LinkedList<AccessRightListItem>();
		for (AccessRight accessRight : list) {
			items.add(new AccessRightListItem(accessRight));
		}
		model.addAttribute("listOfAccessRightItems", items);
		log("AccesRoleController method populateListOfAccessRightItems: end...");
	}

	/**
	 * Populates the combo-box "items" for the referenced entity "User"
	 * 
	 * @param model
	 */
	private void populateListOfUserItems(Model model) {
		log("AccesRoleController method populateListOfUserItems: start...");
		List<UserAdmin> list = userService.findAll();
		List<UserListItem> items = new LinkedList<UserListItem>();
		for (UserAdmin user : list) {
			items.add(new UserListItem(user));
		}
		model.addAttribute("listOfUserItems", items);
		log("AccesRoleController method populateListOfUserItems: end...");
	}

	/**
	 * Populates the Spring MVC model with the given entity and eventually other
	 * useful data
	 * 
	 * @param model
	 * @param accessRole
	 */
	private void populateModel(Model model, AccessRole accessRole,
			FormMode formMode, HttpServletRequest request) {
		log("AccesRoleController method populateModel: start...");
		listAccessRightListItemTemporary = new ArrayList<AccessRightListItemTemporary>();
		List<AccessRight> list = accessRightService.findAll();
		for (int i = 0; i < list.size(); i++) {
			AccessRightListItemTemporary accessRightListItemTemporary = new AccessRightListItemTemporary();
			accessRightListItemTemporary.setId(list.get(i).getAccessRightId().toString());
			accessRightListItemTemporary.setName(list.get(i).getName());
			accessRightListItemTemporary.setIdforUI("row" + i);
			accessRightListItemTemporary.setCheck(false);
			if (formMode == FormMode.UPDATE) {
				accessRole.getListOfAccessRight();
				for (int j = 0; j < accessRole.getListOfAccessRight().size(); j++) {
					if (accessRole.getListOfAccessRight().get(j)
							.getAccessRightId()
							.equals(list.get(i).getAccessRightId())) {
						accessRightListItemTemporary.setCheck(true);
					}
				}
			}

			listAccessRightListItemTemporary.add(accessRightListItemTemporary);
		}
		model.addAttribute("listAccessRightListItemTemporary",
				listAccessRightListItemTemporary);

		// --- Main entity
		model.addAttribute(MAIN_ENTITY_NAME, accessRole);
		if (formMode == FormMode.CREATE) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create"
													// mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
			// --- Other data useful in this screen in "create" mode (all
			// fields)
			populateListOfAccessRightItems(model);
			populateListOfUserItems(model);
		} else if (formMode == FormMode.UPDATE) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update"
													// mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
			// --- Other data useful in this screen in "update" mode (only
			// non-pk fields)
		}
		navigation.populateDefaultModel(model, request);
		log("AccesRoleController method populateModel: end...");
	}

	// --------------------------------------------------------------------------------------
	// Request mapping
	// --------------------------------------------------------------------------------------
	/**
	 * Shows a list with all the occurrences of AccessRole found in the database
	 * 
	 * @param model
	 *            Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model, HttpServletRequest request) {
		log("AccesRoleController method list: start...");
		log("Action 'list'");
		List<AccessRole> list = accessRoleService.findAll();
		log("AccesRoleController method list, size = " + list.size());
		model.addAttribute(MAIN_LIST_NAME, list);
		navigation.populateDefaultModel(model, request);
		log("AccesRoleController method list: end...");
		return JSP_LIST;
	}

	/**
	 * Shows a form page in order to create a new AccessRole
	 * 
	 * @param model
	 *            Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(Model model, HttpServletRequest request) {
		log("AccesRoleController method formForCreate: start...");
		// --- Populates the model with a new instance
		AccessRole accessRole = new AccessRole();
		populateModel(model, accessRole, FormMode.CREATE, request);
		log("AccesRoleController method formForCreate: end...");
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to update an existing AccessRole
	 * 
	 * @param model
	 *            Spring MVC model
	 * @param accessRoleId
	 *            primary key element
	 * @return
	 */
	@RequestMapping(value = "/form/{accessRoleId}")
	public String formForUpdate(Model model,
			@PathVariable("accessRoleId") Long accessRoleId,
			HttpServletRequest request) {
		log("AccesRoleController method formForUpdate: start...");
		log("Action 'formForUpdate'");
		log("AccesRoleController method formForUpdate get accessRoleId : "
				+ accessRoleId);
		// --- Search the entity by its primary key and stores it in the model
		AccessRole accessRole = accessRoleService.findById(accessRoleId);
		log("AccesRoleController method formForUpdate =" + accessRole);
		populateModel(model, accessRole, FormMode.UPDATE, request);
		log("AccesRoleController method formForUpdate: end...");
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends
	 * by 'http redirect'<br>
	 * 
	 * @param accessRole
	 *            entity to be created
	 * @param bindingResult
	 *            Spring MVC binding result
	 * @param model
	 *            Spring MVC model
	 * @param redirectAttributes
	 *            Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/create")
	// GET or POST
	public String create(@Valid AccessRole accessRole,BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes,HttpServletRequest httpServletRequest, Authentication authentication) {
		log("AccesRoleController method create: start...");
		log("AccesRoleController method create : " + accessRole);
		boolean checkDuplicate = checkDuplicate(accessRole);
		if(checkDuplicate){
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.DANGER, "accesRole.duplicate"));
			populateModel(model, accessRole, FormMode.CREATE, httpServletRequest);
			return "redirect:/"+JSP_FORM;
		}else{
			try {
				if (!bindingResult.hasErrors()) {
					accessRole.setCreatedBy(commonUtils.getLoginUser().getUserId());
					log("create get user id : "
							+ commonUtils.getLoginUser().getUserId());
					accessRole.setCreatedDate(new Date());
					for (AccessRightListItemTemporary item : listAccessRightListItemTemporary) {
						String accessRightId = httpServletRequest.getParameter(item
								.getIdforUI());
						if (accessRightId != null) {
							AccessRight accessRight = accessRightService
									.findById(Long.valueOf(accessRightId));
							if (accessRight != null) {
								accessRole.getListOfAccessRight().add(accessRight);
							}
						}
					}
					AccessRole accessRoleCreated = accessRoleService.create(accessRole);
					// UserActivity.SAVE_ACCES_ROLE
					saveAuditrail(httpServletRequest, accessRoleCreated,UserActivity.SAVE_ACCES_ROLE);
					
					model.addAttribute(MAIN_ENTITY_NAME, accessRoleCreated);
					log("AccesRoleController method create: end...");
					messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, "save.ok"));
					return redirectToForm(httpServletRequest,accessRoleCreated.getAccessRoleId());
				} else {
					populateModel(model, accessRole, FormMode.CREATE, httpServletRequest);
					return JSP_FORM;
				}
			} catch (Exception e) {
				log("Action 'create' : Exception - " + e.getMessage());
				messageHelper.addException(model, "accessRole.error.create", e);
				populateModel(model, accessRole, FormMode.CREATE,
						httpServletRequest);
				return JSP_FORM;
			}	
		}
	}

	private void saveAuditrail(HttpServletRequest request,
			AccessRole accessRole, UserActivity value) throws IOException,
			ServletException {
		log("AccesRoleController method saveAuditrail: start...");
		UserAdmin user = new UserAdmin();
		user = commonUtils.getLoginUser();
		String ipAddress = CommonUtils.getRemoteIpAddress(request,isForwardedOn);
		log("AccesRoleController method saveAuditrail get ipAddress : "+ ipAddress);
		log("AccesRoleController method saveAuditrail get accessRole : "+ accessRole);
		log("AccesRoleController method saveAuditrail get request.getRequestURI() : "+ request.getRequestURI());
		AuditTrail auditTrail = auditService.saveAudit(user.getUserId(), value,new Date(),String.format("%s (%s)", request.getRequestURI(), accessRole),ipAddress);
		log("AccesRoleController method saveAuditrail get auditTrail = "+ auditTrail);
		log("AccesRoleController method saveAuditrail: end...");
	}

	@RequestMapping(value = "/update")
	// GET or POST
	public String update(@Valid AccessRole accessRole,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes,
			HttpServletRequest httpServletRequest, Authentication authentication) {
		log("AccesRoleController method update: start...");
		log("Action 'update'");
		log("AccesRoleController method update get accessRole: " + accessRole);
		log("AccesRoleController method update getUser id : "
				+ commonUtils.getLoginUser().getUserId());
		try {
			if (!bindingResult.hasErrors()) {
				for (AccessRightListItemTemporary item : listAccessRightListItemTemporary) {
					String accessRightId = httpServletRequest.getParameter(item
							.getIdforUI());
					if (accessRightId != null) {
						AccessRight accessRight = accessRightService
								.findById(Long.valueOf(accessRightId));
						if (accessRight != null) {
							accessRole.getListOfAccessRight().add(accessRight);
						}
					}
				}

				accessRole
						.setModifiedBy(commonUtils.getLoginUser().getUserId());
				accessRole.setModifiedDate(new Date());
				AccessRole accessRoleSaved = accessRoleService
						.update(accessRole);
				saveAuditrail(httpServletRequest, accessRoleSaved,
						UserActivity.UPDATE_ACCES_ROLE);
				model.addAttribute(MAIN_ENTITY_NAME, accessRoleSaved);
				// --- Set the result message
				messageHelper.addMessage(redirectAttributes, new Message(
						MessageType.SUCCESS, "save.ok"));
				log("Action 'update' : update done - redirect");
				log("AccesRoleController method update: end...");
				return redirectToForm(httpServletRequest,
						accessRole.getAccessRoleId());
			} else {
				log("Action 'update' : binding errors");
				populateModel(model, accessRole, FormMode.UPDATE,
						httpServletRequest);
				return JSP_FORM;
			}
		} catch (Exception e) {
			messageHelper.addException(model, "accessRole.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage());
			populateModel(model, accessRole, FormMode.UPDATE,
					httpServletRequest);
			return JSP_FORM;
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends
	 * by 'http redirect'<br>
	 * 
	 * @param redirectAttributes
	 * @param accessRoleId
	 *            primary key element
	 * @return
	 */
	@RequestMapping(value = "/delete/{accessRoleId}")
	// GET or POST
	public String delete(RedirectAttributes redirectAttributes,
			@PathVariable("accessRoleId") Long accessRoleId,
			HttpServletRequest httpServletRequest, Authentication authentication) {
		log("AccesRoleController method delete: start...");
		log("Action 'delete'");
		log("AccesRoleController method delete get user id : " + accessRoleId);
		try {
			accessRoleService.delete(accessRoleId);
			saveAuditrail(httpServletRequest, null,
					UserActivity.DELETE_ACCES_ROLE);
			// --- Set the result message
			messageHelper.addMessage(redirectAttributes, new Message(
					MessageType.SUCCESS, "delete.ok"));
		} catch (Exception e) {
			messageHelper.addException(redirectAttributes,
					"accessRole.error.delete", e);
		}
		log("AccesRoleController method delete: end...");
		return redirectToList();
	}

	@RequestMapping(value = "/datatableJson", method = RequestMethod.GET)
	public @ResponseBody DataTableResponse<AccessRole> getDataTableJson(
			@Valid DataTableRequest dataTableRequest,
			BindingResult bindingResult, HttpServletRequest request, Model model) {
		log("getDataTableJson start here ....");
		Long accessRoleIdFilter = ParamUtil.getLong(request,
				"accessRoleIdFilter");
		String roleTitleFilter = ParamUtil
				.getString(request, "roleTitleFilter");
		Long createdByFilter = ParamUtil.getLong(request, "createdByFilter");
		Date createdDateFilter = ParamUtil
				.getDate(request, "createdDateFilter");
		Long modifiedByFilter = ParamUtil.getLong(request, "modifiedByFilter");
		Date modifiedDateFilter = ParamUtil.getDate(request,
				"modifiedDateFilter");
		int pageSize = 10;
		if (dataTableRequest.getiDisplayLength() > 0) {
			pageSize = dataTableRequest.getiDisplayLength();
		}

		DataTableResponse<AccessRole> response = new DataTableResponse<AccessRole>();
		String sortedColumn = AccessRoleIndexEnum.getMaps().get(
				dataTableRequest.getiSortedColumnIndex());
		Direction sort = Sort.Direction.ASC;
		if (dataTableRequest.getsSortDirection().equals("desc")) {
			sort = Sort.Direction.DESC;
		}
		Pageable pageable = new PageRequest(dataTableRequest.getPageNumber(),
				pageSize, sort, sortedColumn);
		Long totalRecord = 0l;

		JSONObject filters = new JSONObject();

		if (accessRoleIdFilter != null) {
			filters.put("accessRoleId", accessRoleIdFilter);
		}
		if (roleTitleFilter != null && !roleTitleFilter.isEmpty()) {
			filters.put("roleTitle", roleTitleFilter);
		}
		if (createdByFilter != null) {
			filters.put("createdBy", createdByFilter);
		}
		if (createdDateFilter != null) {
			filters.put("createdDate", createdDateFilter);
		}
		if (modifiedByFilter != null) {
			filters.put("modifiedBy", modifiedByFilter);
		}
		if (modifiedDateFilter != null) {
			filters.put("modifiedDate", modifiedDateFilter);
		}
		AccessRoleSpecifications spec = new AccessRoleSpecifications(filters);

		if (accessRoleIdFilter != null
				|| (roleTitleFilter != null && !roleTitleFilter.isEmpty())
				|| createdByFilter != null || createdDateFilter != null
				|| modifiedByFilter != null || modifiedDateFilter != null) {
			totalRecord = accessRoleService.countWithSpec(spec);
		} else {
			totalRecord = accessRoleService.countAll();
		}

		if (totalRecord.intValue() > 0) {
			if (dataTableRequest.getiDisplayLength() < totalRecord.intValue()) {
				pageSize = dataTableRequest.getiDisplayLength();
			} else {
				pageSize = totalRecord.intValue();
			}
		}

		List<AccessRole> pageResult = new ArrayList<AccessRole>();
		if (accessRoleIdFilter != null
				|| (roleTitleFilter != null && !roleTitleFilter.isEmpty())
				|| createdByFilter != null || createdDateFilter != null
				|| modifiedByFilter != null || modifiedDateFilter != null) {
			pageResult = accessRoleService.findWithSpec(spec, pageable);

		} else {
			pageResult = accessRoleService.findAll(pageable);			
		}
		int page=pageSize*dataTableRequest.getPageNumber();
		int no=page;
		for (int i = pageResult.size()-pageResult.size(); i < pageResult.size(); i++) {
			no=no+1;
			pageResult.get(i).setNo(no);
			if (pageResult.get(i).getListOfUserAdmin().size() > 0) {
				pageResult.get(i).setUse(true);
			} else {
				pageResult.get(i).setUse(false);
			}
		}
		response.setDraw(dataTableRequest.getsEcho());
		response.setRecordsFiltered(totalRecord.intValue());
		response.setRecordsTotal(totalRecord.intValue());
		response.setData(pageResult);
		log("getDataTableJson getting totalRecord value from AccesRole ..."
				+ totalRecord.toString());
		log("getDataTableJson getting pageSize value from AccesRole ..."
				+ pageSize);
		return response;
	}
}