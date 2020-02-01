package com.springboot.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

/**
 * 
 * @author anilt
 *
 */
@Configuration
@EnableAutoConfiguration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private TrackmasterAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private TrackmasterAuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private TrackmasterLogoutSuccessHandler logoutSuccessHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/login/*","/endpoint/*","/endpoint/**","/js/*","/css/*","/images/*","/lib/*",
						"/*/customer-status/**",
						"/*/customer-requests/**",
						"/*/profiles/summary/**",
						"/*/entries/basic/**",
						"/*/entries/full/**",
						"/*/results/basic/**",
						"/*/results/quick/**",
						"/*/workouts/basic/**").permitAll()
//		.antMatchers("/admin/**").access("hasAnyRole('LEVEL_1,LEVEL_2')")
		
		.accessDecisionManager(defaultAccessDecisionManager())
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.usernameParameter("username").passwordParameter("password")
		.loginPage("/login/formlogin")
	    .loginProcessingUrl("/j_spring_security_check").permitAll()
		.successHandler(authenticationSuccessHandler)
		.failureHandler(authenticationFailureHandler).and()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.logout()
		.logoutUrl("/login/logout").logoutSuccessHandler(logoutSuccessHandler).logoutSuccessUrl("/login/logoutSuccess").permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error/403")
		;
	}

	
	
	@Bean
	public AffirmativeBased defaultAccessDecisionManager(){
	    WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
	    DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
	    expressionHandler.setDefaultRolePrefix("");
	    webExpressionVoter.setExpressionHandler(expressionHandler);
	    List<AccessDecisionVoter<?>> voters = new ArrayList<AccessDecisionVoter<?>>();
	    voters.add(webExpressionVoter);
	    return new AffirmativeBased(voters);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(getPasswordEncoder());
	}

	 @Bean
	 public BCryptPasswordEncoder getPasswordEncoder() {
		 return new BCryptPasswordEncoder(10);
	 }
	 
	 @Bean 
	 @Override 
	 public AuthenticationManager authenticationManagerBean() throws Exception {
		 return super.authenticationManagerBean();
	 }
	 
	 
	 
}
