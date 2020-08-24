package com.newrelic.shopjava.security;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.newrelic.shopjava.security.SecurityConstants.SECRET;

public class JWTAuthFilter extends OncePerRequestFilter {
	private final String HEADER = "Authorization"; 
	private final String PREFIX = "SHOP";


	@Override //Method that filters out token from header to verify data
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
		try {
			if (checkJWTToken(req, res)) {
				Claims claims = validateToken(req);
				if(claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				}else {
					SecurityContextHolder.clearContext();
				}
			}
			chain.doFilter(req, res);
		} catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			res.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) res).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}
	
	//Validates token once extracted
	private Claims validateToken(HttpServletRequest req) {
		String jwtToken = req.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}
	
	//Sets up authentication for filter
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");
		Object userId = claims.get("Id");
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), userId, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	//Gets token from HTTP request
	private boolean checkJWTToken(HttpServletRequest req, HttpServletResponse res) {
		String authenticationHeader = req.getHeader(HEADER);
		if(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}
}
