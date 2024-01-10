package com.icbc.match.config;

import com.icbc.match.security.JWTAuthorizationFilter;
import com.icbc.match.security.JwtAuthenticationHandler;
import com.icbc.match.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 配置SpringSecurity框架，采用JWT认证
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public JwtTokenService jwtTokenService;

    @Autowired
    private JwtAuthenticationHandler authenticationHandler;

    /**
     * 禁止csrf
     * 未授权下仅允许访问登陆接口
     * 关闭默认登陆
     * 基于JWT TOKEN
     *
     * @param http HttpSecurity对象
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationHandler)
                .and()
                .authorizeRequests()//开始设置请求权限
                .antMatchers(SecurityConstants.CORP_LOGIN_URL).permitAll()
                .antMatchers("/zyzrnotice").permitAll()
                .antMatchers("/finapply").permitAll()
                .antMatchers("/pledgelock").permitAll()

     //           .antMatchers("/zyzrnotice").permitAll()
                .anyRequest().authenticated()//其余所有请求都需要用户登录后才可以访问
                .and()
                .formLogin().disable()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtTokenService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//基于TOKEN，不需要session
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

}
