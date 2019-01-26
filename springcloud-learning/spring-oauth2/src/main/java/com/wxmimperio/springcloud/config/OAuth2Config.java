package com.wxmimperio.springcloud.config;

import com.wxmimperio.springcloud.service.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;


@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private AuthUserDetailsService authUserDetailsService;

    @Autowired
    public OAuth2Config(AuthenticationManager authenticationManager, AuthUserDetailsService authUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 存储方式，memory or jdbc
                .withClient("wxmimperio") // 注册应用名称
                .secret("password") // 访问令牌时秘钥
                .authorizedGrantTypes("refresh_token", "password", "client_credentials") // 授权类型列表
                .scopes("webClient", "mobileClient"); // 可操作范围
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(authUserDetailsService);
    }
}
