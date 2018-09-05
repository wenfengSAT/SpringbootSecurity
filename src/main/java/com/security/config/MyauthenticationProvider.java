package com.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.security.repository.UserRepository;
import com.security.security.entity.UserEntity;

/**
 * 
 * @Description： 自定义验证工具
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2018年9月5日上午9:55:08]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
public class MyauthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserRepository userRepository;
	/*@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RolePermissionDao rolePermissionDao;*/

	//@Autowired
	//private PasswordEncoder passwordEncoder;

	/**
	 * Description:登录认证
	 * 
	 * @param authentication
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String status = "1";
		String userName = authentication.getName();
		String passWord = (String) authentication.getCredentials();
		UserEntity user = userRepository.findByUsername(userName);
		if (user == null) {
			throw new BadCredentialsException("用户不存在！");
		}

		// 加密过程在这里体现,matches(CharSequence rawPassword, String encodedPassword)
		/*if (!passwordEncoder.matches(passWord, user.getPassword())) {
			throw new BadCredentialsException("密码错误！");
		}*/
		
		if (!user.getPassword().equals(passWord)) {
			throw new BadCredentialsException("密码错误！");
		}

		if (status.equals("2")) {
			throw new BadCredentialsException("账号不可用，请联系系统管理员！");
		}

		if (status.equals("3")) {
			throw new BadCredentialsException("账号已锁定，请联系系统管理员！");
		}

		MyUserDetails myUserDetails = new MyUserDetails();
		myUserDetails.setUsername(userName);
		myUserDetails.setPassword(user.getPassword());
		//List<UserRoleEntity> userRoleEntitys = userRoleService.getUserRoleEntitiesByUserId(user.getUserId());
		//List<Role2PermissionEntity> role2PermissionEntities = new ArrayList<>();
		//for (UserRoleEntity userRoleEntity : userRoleEntitys) {
		//	role2PermissionEntities.addAll(rolePermissionDao.getRolePermissionByRoleId(userRoleEntity.getRoleId()));
		//}
		//myUserDetails.setPermissions(role2PermissionEntities);
		//Collection<? extends GrantedAuthority> authorities = myUserDetails.getAuthorities();
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.addAll(user.getAuthorities());
		return new UsernamePasswordAuthenticationToken(user, passWord, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}


}
