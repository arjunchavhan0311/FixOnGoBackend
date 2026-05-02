package com.fixOnGo.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fixOnGo.backend.entity.Role;
import com.fixOnGo.backend.repository.AdminRepo;
import com.fixOnGo.backend.repository.CustomerRepo;
import com.fixOnGo.backend.repository.WorkerRepo;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private WorkerRepo workerRepo;

	@Autowired
	private AdminRepo adminRepo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// ✅ BYPASS JWT for uploaded images
		String requestPath = request.getRequestURI();
		if (requestPath.startsWith("/uploads/")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader("Authorization");

		// ⛔ No token → continue filter chain
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);
		String email = jwtUtils.extractEmail(token);
		String roleFromToken = jwtUtils.extractRole(token);

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			if (jwtUtils.validateToken(token)) {

				// 🔥 FIX: Remove ROLE_ prefix before enum conversion
				String cleanRole = roleFromToken.startsWith("ROLE_") ? roleFromToken.substring(5) : roleFromToken;

				Role roleEnum;
				try {
					roleEnum = Role.valueOf(cleanRole);
				} catch (IllegalArgumentException ex) {
					filterChain.doFilter(request, response);
					return;
				}

				Object user = null;
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleEnum.name());

				switch (roleEnum) {
				case CUSTOMER -> user = customerRepo.findByEmail(email);
				case WORKER -> user = workerRepo.findByEmail(email);
				case ADMIN -> user = adminRepo.findByEmail(email);
				}

				if (user != null) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
							Collections.singletonList(authority));

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}

		filterChain.doFilter(request, response);
	}
}
