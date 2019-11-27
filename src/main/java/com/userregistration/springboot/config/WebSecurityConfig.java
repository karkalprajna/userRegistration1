package com.userregistration.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.userregistration.springboot.service.RoleService;
import com.userregistration.springboot.service.RoleServiceImpl;
import com.userregistration.springboot.service.SecurityService;
import com.userregistration.springboot.service.SecurityServiceImpl;
import com.userregistration.springboot.service.UserService;
import com.userregistration.springboot.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		return super.userDetailsService();
	}

	/*
	 * @Bean public RoleService roleService() { return new RoleServiceImpl(); }
	 * 
	 * @Bean public SecurityService securityService() { return new
	 * SecurityServiceImpl(); }
	 */
	 
	
	/*
	 * @Bean public UserService userService() { return new UserServiceImpl(); }
	 */
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.inMemoryAuthentication().withUser("admin").password(
	 * bCryptPasswordEncoder().encode("test123")).roles("USER", "ADMIN"); }
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/registration/**").permitAll() 
        .antMatchers("/forgotpassword").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/logout").permitAll()
        .antMatchers("/**").permitAll()        
        .anyRequest().authenticated()
        .and().httpBasic().realmName("user-reg-app")
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
    
		
		
		
		/* correct  http.httpBasic().
          realmName("user-reg-app").
          and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
          and().csrf().disable().
          authorizeRequests()
          .antMatchers( "/registration/**").hasRole("admin")
          .antMatchers("/**").permitAll().anyRequest().authenticated();*/
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
}
