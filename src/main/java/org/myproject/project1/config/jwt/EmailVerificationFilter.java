package org.myproject.project1.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.myproject.project1.config.security.user.UserDetailsImpl;
import org.myproject.project1.db.AccountService;
import org.myproject.project1.exception.CustomFilterExceptionHandler;
import org.myproject.project1.utils.SecurityContextUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author nguyenle
 * @since 4:26 PM Fri 12/6/2024
 */
@Component
@RequiredArgsConstructor
public class EmailVerificationFilter extends OncePerRequestFilter {

	private final AccountService accountService;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		try {
			String path = request.getServletPath();
			if (path.startsWith("/auth") || path.startsWith("/verify")) {
				filterChain.doFilter(request, response);
				return;
			}

			Authentication authentication = SecurityContextUtils.getCurrentAuthentication();

			if (authentication != null && authentication.isAuthenticated()) {
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

				if (userDetails.isVerified()) {
					List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

					authorities.add(new SimpleGrantedAuthority("EMAIL_VERIFIED"));

					Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
							authentication.getPrincipal(),
							authentication.getCredentials(),
							authorities
					);

					SecurityContextHolder.getContext().setAuthentication(newAuthentication);
				}
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			CustomFilterExceptionHandler.handleUnexpectedExceptionEmailVerificationFilter(request, response);
		}

	}
}
