package com.fixOnGo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fixOnGo.backend.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	// ---------------- PASSWORD ENCODER ----------------
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// ---------------- AUTH MANAGER ----------------
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	// ---------------- SECURITY FILTER CHAIN ----------------
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				// Disable CSRF for REST APIs
				.csrf(csrf -> csrf.disable())
			    .cors(Customizer.withDefaults())

				// .cors(cors -> cors) // just enable it; Spring will use the
				// CorsConfigurationSource bean

				// Allow H2 Console
				.headers(headers -> headers.addHeaderWriter((request, response) -> {
					response.setHeader("X-Frame-Options", "SAMEORIGIN");
				}))

				// Stateless session (JWT)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// ---------------- AUTHORIZATION ----------------
				.authorizeHttpRequests(auth -> auth

						// ---------- PUBLIC ----------
						.requestMatchers("/h2-console/**").permitAll()

						.requestMatchers("/api/admin/signup", "/api/admin/login", "/api/admin/forgot-password",
								"/api/admin/reset-password")
						.permitAll()

						.requestMatchers("/api/worker/signup", "/api/worker/login", "/api/worker/forgot-password",
								"/api/worke/reset-password")
						.permitAll().requestMatchers("/api/worker/login").permitAll()
						.requestMatchers("/worker/set-password/**").permitAll()

						.requestMatchers("/api/customer/signup", "/api/customer/login", "/api/customer/forgot-password",
								"/api/customer/reset-password", "/api/customer/show/service", "/error")
						.permitAll()

						.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

						.requestMatchers("/uploads/**").permitAll()
						
						.requestMatchers("/ws/**").permitAll()
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

						// ---------- 🔥 SPECIFIC FIRST (VERY IMPORTANT) ----------
						.requestMatchers("/api/admin/worker/workload-summary").hasRole("ADMIN")

						.requestMatchers("/api/worker/workload").hasAnyRole("WORKER", "ADMIN")

						// ---------- ROLE BASED ----------
						.requestMatchers("/api/admin/**").hasRole("ADMIN").requestMatchers("/api/worker/**")
						.hasRole("WORKER").requestMatchers("/api/customer/**").hasRole("CUSTOMER")

						// ---------- ANY OTHER ----------
						.anyRequest().authenticated())

				// JWT Filter
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
