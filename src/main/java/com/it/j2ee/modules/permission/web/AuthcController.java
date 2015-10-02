package com.it.j2ee.modules.permission.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.it.j2ee.modules.common.util.HttpUtils;
import com.it.j2ee.modules.permission.entity.Menu;
import com.it.j2ee.modules.permission.entity.Role;
import com.it.j2ee.modules.permission.entity.User;
import com.it.j2ee.modules.permission.service.AccountService;
import com.it.j2ee.modules.permission.service.MenuService;
import com.it.j2ee.modules.permission.service.RoleService;

/**
 * TODO 方法调用权限--没控制
 * TODO 用户管理时，系统管理员用户不可维护--切记
 * @author Administrator
 *
 */
@Controller
@RequestMapping
public class AuthcController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String toMenuList(Model model) {
		List<Menu> menus = menuService.getAllMenu();
		model.addAttribute("menus", menus);
		getAllMenuTreeData(model);
		
		return "account/menuList";
	}
	
	@RequestMapping(value = "/adapter/menu", method = RequestMethod.GET)
	public String toMenuListPage(Model model) {
		getAllMenuTreeData(model);
		return "account/assignMenu";
	}

	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public String toRoleList(Model model) {
		List<Role> roles = roleService.getAllRole();
		model.addAttribute("roles", roles);
		
		return "account/roleList";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String toUserList(Model model) {
		List<User> users = accountService.getAllUser();
		model.addAttribute("users", users);
		
		return "account/userList";
	}
	
	@RequestMapping(value = "/adapter/action", method = RequestMethod.GET)
	public String toActionListPage(HttpServletRequest request, Model model) {
		Map<String,String> reqParams = HttpUtils.getParameters(request);
		List<Menu> menus = menuService.getAllMenu();
		
		Map<String,Object> resultMap =new HashMap<String, Object>();
		resultMap.put("total", menus.size());
		resultMap.put("rows", menus);
		model.addAttribute("menus", JSONObject.toJSONString(resultMap));
		model.addAllAttributes(reqParams);
		
		return "account/assignAction";
	}
	
	@RequestMapping(value = "/adapter/action", method = RequestMethod.POST)
	public String saveRoleMenu(Long roleId,String roleActions,Model model) {
		roleService.saveRoleMenuAction(roleId, roleActions);	
		return "account/assignAction";
	}
	
	private void buildMenuTree(Menu parentMenu, List<Menu> menus){
		List<Menu> tempMenus = new ArrayList<Menu>();
		for (Menu menu : menus) {
			if(menu.getpId() == parentMenu.getId()){
				tempMenus.add(menu);
				buildMenuTree(menu, menus);
			}
		}
		
		if(tempMenus.size()>1){
	    	Collections.sort(tempMenus,new Comparator<Menu>() {
	            public int compare(Menu arg0, Menu arg1) {
	                return arg0.getDisplayOrder().compareTo(arg1.getDisplayOrder());
	            }
	        });
    	}
		
		parentMenu.setChildrens(tempMenus);
	}
	
	@RequestMapping(value = "/loadMenuTree", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getAllMenuTreeData(Model model) {
		List<Menu> menus = menuService.getAllMenu();
		
		// 构造easyui tree data
		Menu rootMenu = new Menu();
		for (Menu menu : menus) {
			if("ROOT".equals(menu.getMenuCode())){
				rootMenu.setId(menu.getId());
				rootMenu.setMenuName(menu.getMenuName());
				buildMenuTree(rootMenu, menus);
			}
		}
		
		Map<String,Object> rootMenuTree = new HashMap<String, Object>();
		List<Map<String,Object>> menuTree = new ArrayList<Map<String,Object>>();
		rootMenuTree.put("id", rootMenu.getId());
		rootMenuTree.put("text", rootMenu.getMenuName());
		rootMenuTree.put("children", menuTree);
		for(Menu menu : rootMenu.getChildrens()){
			Map<String,Object> firstMenu = new HashMap<String, Object>();
			firstMenu.put("id", menu.getId());
			firstMenu.put("text", menu.getMenuName());
			
			List<Map<String,Object>> secondMenuTree = new ArrayList<Map<String,Object>>();
			for(Menu childMenu : menu.getChildrens()){
				Map<String,Object> secondMenu = new HashMap<String,Object>();
				secondMenu.put("id", childMenu.getId());
				secondMenu.put("text", childMenu.getMenuName());
				secondMenuTree.add(secondMenu);
			}
			firstMenu.put("children", secondMenuTree);
			menuTree.add(firstMenu);
		}
		
		List<Map<String,Object>> responseMenuTree = new ArrayList<Map<String,Object>>();
		responseMenuTree.add(rootMenuTree);
		model.addAttribute("menuTreeData", JSONObject.toJSONString(responseMenuTree));
		
		return responseMenuTree;
	}
	
	
	
}
