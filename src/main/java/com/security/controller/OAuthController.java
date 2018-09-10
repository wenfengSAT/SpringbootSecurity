package com.security.controller;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Controller
@RequestMapping("/OAuth")
public class OAuthController {
	
	/**
	 * 1.访问用户登录的验证接口
		https://github.com/login/oauth/authorize?client_id=xxxxxxxxxxxxxxxxxx&scope=user,public_repo
     2.访问上面接口后会github会让其跳转到你预定的url(Authorization callback URL)，并且带上code参数,例如
		http://localhost:8080/callback?code=****************
     3.然后，开发者可以通过code,client_id以及client_secret这三个参数获取用户的access_token即用户身份标识，请求如下
		https://github.com/login/oauth/access_token?client_id=xxxxxxxxxxxxxxxxxxx&client_secret=xxxxxxxxxxxxxxxxx&code=xxxxxxxxxxxxxxxxxxx 
      	这样就会返回access_token,如下
		access_token=xxxxxxxxxxxxxxxxxxxxxxxxx&scope=public_repo%2 Cuser&token_type=bearer
     4. 这样我们就可以用这个access_token来获取用户的信息
		https://api.github.com/user?access_token=xxxxxxxxxxxxxxxxx xxxxxxxx
	 * @return
	 */

	

    private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";
	@RequestMapping(value = "/authLogin", method = RequestMethod.GET)
    public void authLogin(HttpServletRequest request, HttpServletResponse response) {
		String url ="https://github.com/login/oauth/authorize?client_id=eff0d64563b0ec06f4e9&scope=user,public_repo";
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	
	@RequestMapping(value = "/callback/getOAuth", method = RequestMethod.GET)
	public void getOAuth(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException, ExecutionException {
		String secretState = "secret" + new Random().nextInt(999_999);
	    OAuth20Service service = new ServiceBuilder("eff0d64563b0ec06f4e9")
                .apiSecret("42dd76388c5aea9dba9420de42ec84ce170e6ba5")
                .state(secretState)
                .callback("http://127.0.0.1:8080/OAuth/callback/getOAuth")
                .build(GitHubApi.instance());
		String code = request.getParameter("code");
		OAuth2AccessToken accessToken = service.getAccessToken(code);
        final com.github.scribejava.core.model.OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, oAuthRequest);
        final com.github.scribejava.core.model.Response oAuthresponse = service.execute(oAuthRequest);
        Object result = JSON.parse(oAuthresponse.getBody());
        System.out.println(oAuthresponse.getBody());
		System.out.println("code:"+code);
		System.out.println("登录成功了.........");
	} 
}
