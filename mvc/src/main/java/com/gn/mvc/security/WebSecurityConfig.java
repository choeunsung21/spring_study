package com.gn.mvc.security;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration	// 환경설정 파일
@EnableWebSecurity	// 스프링 보안
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final DataSource dataSource;
	
	
	// 요청중에 정적 리소스가 있는 경우 -> Security 비활성화
	@Bean
	WebSecurityCustomizer configure() {
		return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	
	// 특정 요청이 들어왔을 때 어떻게 처리할 것인가
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService customUserDetailsService) throws Exception{
		http.userDetailsService(customUserDetailsService).authorizeHttpRequests(request -> request.requestMatchers("/login","/signup","logout","/").permitAll().requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
		.formLogin(login -> login.loginPage("/login").successHandler(new MyLoginSuccessHandler()).failureHandler(new MyLoginFailureHandler()))
		.logout(logout -> logout.logoutUrl("/logout").clearAuthentication(true).logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("remember-me"))
		.rememberMe(rememberMe -> rememberMe.rememberMeParameter("remember-me").tokenValiditySeconds(60*60*24*30).alwaysRemember(false).tokenRepository(tokenRepository()));
		
		return http.build();
	}

	
	// 데이터베이스 접근 Bean 등록
	@Bean
	PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}
	
	// 비밀번호 암호화에 사용될 Bean 등록
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// AuthenticationManager(인증 관리)
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
