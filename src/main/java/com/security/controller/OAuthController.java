package com.security.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.security.entity.GithubUser;
import com.security.entity.ResourceEntity;
import com.security.entity.RoleEntity;
import com.security.entity.UserEntity;
import com.security.repository.RoleRepository;
import com.security.repository.UserRepository;
import com.security.util.ResourceUtil;
import com.github.scribejava.core.model.Response;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Controller
@RequestMapping("/OAuth")
public class OAuthController {

	/**
	 * 1.访问用户登录的验证接口
	 * https://github.com/login/oauth/authorize?client_id=xxxxxxxxxxxxxxxxxx&scope=user,public_repo
	 * 2.访问上面接口后会github会让其跳转到你预定的url(Authorization callback URL)，并且带上code参数,例如
	 * http://localhost:8080/callback?code=****************
	 * 3.然后，开发者可以通过code,client_id以及client_secret这三个参数获取用户的access_token即用户身份标识，请求如下
	 * https://github.com/login/oauth/access_token?client_id=xxxxxxxxxxxxxxxxxxx&client_secret=xxxxxxxxxxxxxxxxx&code=xxxxxxxxxxxxxxxxxxx
	 * 这样就会返回access_token,如下
	 * access_token=xxxxxxxxxxxxxxxxxxxxxxxxx&scope=public_repo%2
	 * Cuser&token_type=bearer 
	 * 4. 这样我们就可以用这个access_token来获取用户的信息
	 * https://api.github.com/user?access_token=xxxxxxxxxxxxxxxxxxxxxxxxx
	 * 
	 */

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	protected AuthenticationManager authenticationManager;

	private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";
	@Value("${github.appId}")  
	private String appId;
	@Value("${github.appSecret}")  
	private String appSecret; 
	@Value("${github.callbackUrl}")  
	private String callbackUrl; 
	@Value("${github.redrictUrl}")  
	private String redrictUrl; 

	@RequestMapping(value = "/authLogin", method = RequestMethod.GET)
	public void authLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(redrictUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/callback/getOAuth", method = RequestMethod.GET)
	public String getOAuth(@RequestParam(value = "code", required = true) String code, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String secretState = "secret" + new Random().nextInt(999_999);
		OAuth20Service service = new ServiceBuilder(appId)
				.apiSecret(appSecret).state(secretState)
				.callback(callbackUrl).build(GitHubApi.instance());
		OAuth2AccessToken accessToken = null;
		GithubUser githubUser = null;
		try {
			accessToken = service.getAccessToken(code);
			final OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			service.signRequest(accessToken, oAuthRequest);
			final Response oAuthresponse = service.execute(oAuthRequest);
			githubUser = JSON.parseObject(oAuthresponse.getBody(), new TypeReference<GithubUser>() {});
			model.addAttribute("user", oAuthresponse.getBody());
			System.out.println(oAuthresponse.getBody());
		} catch (IOException e) {
			model.addAttribute("error", "github登录失败！");
			return "login";
		} catch (InterruptedException e) {
			model.addAttribute("error", "github登录失败！");
			return "login";
		} catch (ExecutionException e) {
			model.addAttribute("error", "github登录失败！");
			return "login";
		} catch (OAuthException e) {
			model.addAttribute("error", "github登录失败！");
			return "login";
		}
		// TODO
		// 1、判断是不是第一次授权登录,新增用户写进数据库，给一个默认角色
		/*
		 * if(第一次授权登录){ 
		 * 新增用户写进数据库，给一个默认角色，user表新增字段第三方登录方式及第三方用户id
		 * 新增userDetail表来存储用户详细信息 
		 * }else{ 
		 * 	根据第三方用户id获取以前存进库里的信息登录 
		 * }
		 */
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		roles.add(roleRepository.findOne((long) 2));//普通用户
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(githubUser.getLogin());
		userEntity.setPassword("123456");
		userEntity.setId((long) 3);
		userEntity.setRoles(roles);
		userEntity = userRepository.saveAndFlush(userEntity);
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword(), authorities);
		//List<GrantedAuthority> authorities = new ArrayList<>();
		//authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		//authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		//Authentication authentication = new UsernamePasswordAuthenticationToken("admin", "123456", authorities);
		// 将token传递给Authentication进行验证
		Authentication result = authenticationManager.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(result);
		UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<RoleEntity> roleList = user.getRoles();
		Set<ResourceEntity> resourceList = new HashSet<ResourceEntity>();
		String roles1 = "";
		for (RoleEntity role : roleList) {
			roles1 += role.getName() + ",";
			resourceList.addAll(role.getResources());
		}
		roles1 = roles1.substring(0, roles1.length() - 1);
		request.getSession().setAttribute("roles", roles1);
		System.out.println("====================="+roles1);
		for(ResourceEntity r: resourceList) {
			System.out.println(r.getName());
		}
		System.out.println(ResourceUtil.format(new ArrayList<>(resourceList)));
		model.addAttribute("resourceList", ResourceUtil.format(new ArrayList<>(resourceList)));
		return "github_success";
	}
}
