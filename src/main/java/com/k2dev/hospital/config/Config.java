package com.k2dev.hospital.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.k2dev.hospital.jwt.AuthEntryPointJwt;
import com.k2dev.hospital.jwt.JwtRequestFilter;
import com.k2dev.hospital.service.UserDetailsServiceImp;
import com.k2dev.hospital.util.RoleType;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceImp userDetailsService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public JwtRequestFilter authenticationJwtTokenFilter() {
	    return new JwtRequestFilter();
	}
	 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//disable csrf protocols 
		http.csrf().disable().cors();
		
		http.authorizeRequests()
			.antMatchers("/auth/**").permitAll()
			.antMatchers("/v1/area/**").permitAll()
			.antMatchers("/v1/public/**").permitAll()
			.antMatchers("/v1/patients/**").hasAnyAuthority("PATIENT")
			.antMatchers("/v1/hospital-admins/**").hasAnyAuthority("HOSPITAL_ADMIN")
			.antMatchers("/v1/doctors/**").hasAnyAuthority("DOCTOR", "HOSPITAL_ADMIN")
			.antMatchers("/v1/admin/**").hasAnyAuthority("ADMIN")
			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	/**
	 * CORS configuration
	 */
	@Value("${client.address}")
	private String CLIENT;
	
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration configuration= new CorsConfiguration();
		configuration.setAllowCredentials(true);
		
		//list of valid hosts(clients)
		configuration.setAllowedOrigins(Arrays.asList(CLIENT));
		
		//allowed headers
		configuration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin","Content-Type",
				"Accept", "Authorization", "Origin,Accept", "X-Requested-With", "Accept-Control-Request-Method",
				"Accept-Control-Request-Header"));
		
		configuration.setExposedHeaders(Arrays.asList("Origin","Accept", "Authorization","Content-Type",
				"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
		
		//allowed methods
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource= 
				new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
		
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
	
}
