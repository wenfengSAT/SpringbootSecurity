package com.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.security.security.entity.UserEntity;


/**
 * 
 * @Description：自定义扩展用户登录时加载的用户信息
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2018年9月5日上午9:56:46]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Component
public class MyUserDetails extends UserEntity implements UserDetails {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8363642492904508724L;

    /** 用户拥有的权限 **/
    //private List<Role2PermissionEntity> permissions;

    public MyUserDetails() {
    }

    /*public MyUserDetails(UserEntity user, List<Role2PermissionEntity> permissions) {
        super(user);
        this.permissions = permissions;
    }*/

    @Override
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //for (Role2PermissionEntity permission : permissions) {
        //    authorities.add(new SimpleGrantedAuthority("ROLE_" + permission.getPermissionId()));
        //}
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /*public List<Role2PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Role2PermissionEntity> permissions) {
        this.permissions = permissions;
    }*/

}
