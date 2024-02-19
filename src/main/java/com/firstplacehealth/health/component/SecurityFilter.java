package com.firstplacehealth.health.component;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.firstplacehealth.health.services.TokenService;
import com.firstplacehealth.health.services.UsersService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Value("${api.authorization.header}")
	private String AUTORIZATION_HEADER;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	UsersService usersService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
		var token = this.recoverToken(request);
		if (token != null) {
			var login = tokenService.validateToken((String) token);
			UserDetails userDetails = (UserDetails) usersService.findByLogin(login);
			
			var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private Object recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader(AUTORIZATION_HEADER);
		if (authHeader == null)
			return null;
		return authHeader.replace("Bearer ", "");
	}
}
