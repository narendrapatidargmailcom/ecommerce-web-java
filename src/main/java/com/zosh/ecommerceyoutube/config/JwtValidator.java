package com.zosh.ecommerceyoutube.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {
	
	private static final Logger loger = LogManager.getLogger(JwtValidator.class.getName());


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		loger.info("Enter into the doFilterInternal");
		 String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		 
		 if(jwt!=null)
		 {
			 loger.info("Enter into the jwt if condition");
			 System.out.println("jwt==>"+jwt);	
			 jwt = jwt.substring(7);
			 System.out.println("jwt==>"+jwt);		
			 try {
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				 System.out.println("key==>"+key);
				 Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				 System.out.println("claims==>"+claims);
				 String email = String.valueOf(claims.get("email"));
				 System.out.println("email==>"+email);
				 String authorities = String.valueOf(claims.get("authorities"));
				 System.out.println("authorities==>"+authorities);
				 
				 List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				 Authentication authentication = new UsernamePasswordAuthenticationToken(email,null, auths);
				 SecurityContextHolder.getContext().setAuthentication(authentication);
				 
				 
			} catch (Exception e) {
				 System.out.println(e.getMessage());
				throw new BadCredentialsException("invalid token from jwt.....validator");
				
			}
		 }
		 loger.info("doing  the filter-->>doFilter(request, response)"+request.getRequestURI());
		filterChain.doFilter(request, response);
		loger.info("Exit From the doFilterInternal");
	}

}
//eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2OTY5MzM4MTksImV4cCI6MTY5NjA4NzgxOSwiZW1haWwiOiJuYXJlbmRyYXBhdGlkYXIwNjAzQGdtYWlsLmNvbSJ9.oYMo2LwFMHlkCnlO1Em4ZePSRUYtc48Ejfzb2y9SVwjKmvY1NfV-wgW-xug6K2mGHiSgtAsh6zN-0w-cDL-i5w