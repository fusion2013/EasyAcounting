package com.fusion.ea.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.fusion.ea.entity.User;
import com.fusion.ea.repository.RoleRepository;
import com.fusion.ea.repository.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		boolean authenticate = false;

		User user = userRepository.findByUserName(name);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				authenticate = true;
			}

			// use the credentials to try to authenticate against the third
			// party system
			if (authenticate) {
				List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
				// for(Role r : roleRepository.f) {
				// grantedAuthorities.add(new
				// GrantedAuthorityImpl(r.getName()));
				// }
				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
				PreAuthenticatedAuthenticationToken result = new PreAuthenticatedAuthenticationToken(
						authentication.getPrincipal(),
						authentication.getCredentials(), grantedAuthorities);
				result.setDetails(user);
				return result;
			} else {
				throw new AuthenticationException(
						"Unable to auth against third party systems") {
					private static final long serialVersionUID = 1L;
				};
			}
		} else {
			throw new AuthenticationException(
					"Unable to auth against third party systems") {
				private static final long serialVersionUID = 1L;
			};
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
