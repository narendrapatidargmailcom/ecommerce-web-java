package com.zosh.ecommerceyoutube.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	
	private static final Logger loger = LogManager.getLogger(JwtProvider.class.getName());
	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	public String generateToken(Authentication authentication) {
		loger.info("Enter into the generateToken");
		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 846000000))
				.claim("email", authentication.getName())
				.signWith(key)
				.compact();
		loger.info("Exit from the generateToken");
		return jwt;
	}
	
	public String getEmailFromToken(String jwt) {
		loger.info("Enter into the getEmailFromToken");
		jwt =jwt.substring(7);
		Claims claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email = String.valueOf(claim.get("email"));
		loger.info("Exit from the getEmailFromToken");
		return email;
		
		
	}
}
