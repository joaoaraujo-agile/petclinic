package com.agile.petclinic.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.agile.petclinic.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	private static final Long expirationTime = 1800000L;
	private String key = "Secret String";
	
	public String generatetoken(User user) {
		return Jwts.builder()
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setSubject("Pet Clinic Jwt API")
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS256, key)
				.compact();
	}

	public Claims decodeToken(String token) {
		return Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token)
				.getBody();
	}
	
}
