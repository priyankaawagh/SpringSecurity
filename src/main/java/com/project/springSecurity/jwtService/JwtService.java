package com.project.springSecurity.jwtService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// Spring security doesn't provide JWT so we need to implement our own logic for JWT tokens

@Service
public class JwtService {
	
	public static final String SECRET = "gdhtrdhdmhdhkrtmh356565465465hfghfghfghgfh";
	
	public String generateToken(String username) {
		
		return Jwts.builder()
					.setSubject(username)
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
					.addClaims(new HashMap<>())
					.signWith(getSingedKey(), SignatureAlgorithm.HS256)
					.compact();
					
	}
	
	private Key getSingedKey() {
		
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}
	
	public Claims verifySignatureAndExtractClaims(String token) {
		return Jwts.parserBuilder()
					.setSigningKey(getSingedKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
	}
	
	public String extractUsername(String token)
	{
		return verifySignatureAndExtractClaims(token).getSubject();
	}
	
	public Date getExpiration(String token)
	{
		return verifySignatureAndExtractClaims(token).getExpiration();
	}
	
	public Boolean isTokenExpired(String token)
	{
		return getExpiration(token).before(new Date());
	}

}
