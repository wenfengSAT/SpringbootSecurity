package com.security.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.security.entity.ResourceEntity;
import com.security.entity.RoleEntity;
import com.security.entity.UserEntity;
import com.security.repository.UserRepository;

/**
 * 
 * @Description： 功能描述
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2018年9月5日上午10:22:03]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Controller
public class IndeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndeController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/main")
	public String index(Model model,HttpServletRequest request,HttpServletResponse response) {
		UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<RoleEntity> roleList = user.getRoles();
		Set<ResourceEntity> resourceList = new HashSet<ResourceEntity>();
		String roles = "";
		for (RoleEntity role : roleList) {
			roles += role.getName() + ",";
			resourceList.addAll(role.getResources());
		}
		roles = roles.substring(0, roles.length() - 1);
		request.getSession().setAttribute("roles", roles);
		LOGGER.debug("====================="+roles);
		for(ResourceEntity r: resourceList) {
			LOGGER.debug(r.getName());
		}
		model.addAttribute("resourceList", resourceList);
		// request.getSession().setMaxInactiveInterval(5);// 设置session超时时间5秒
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		int pageNumber = 1;
		int pageSize = 10;
		Pageable pageable = new PageRequest(pageNumber-1,pageSize);
    	Page<UserEntity> r = userRepository.findAll(pageable);
    	result.put("rows", r.getContent());
    	result.put("total", r.getTotalElements());
		return "main";
	}
}