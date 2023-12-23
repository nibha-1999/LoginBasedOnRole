package com.jwt.implementation.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.implementation.model.Role;
import com.jwt.implementation.model.User;
import com.jwt.implementation.model.UserDTO;
import com.jwt.implementation.repository.RoleRepository;
import com.jwt.implementation.repository.UserRepository;

@Service
public class DefaultUserServiceImpl implements DefaultUserService {

	private final Logger logger = LoggerFactory.getLogger(DefaultUserServiceImpl.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userRepo.findByUserName(username);
			return new org.springframework.security.core.userdetails.User(
					user.getUserName(),
					user.getPassword(),
					mapRolesToAuthorities(user.getRole())
			);
		} catch (Exception ex) {
			logger.error("Error loading user by username: {}", ex.getMessage());
			throw new UsernameNotFoundException("Error loading user by username", ex);
		}
	}

	public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList());
	}

	@Override
	public User save(UserDTO userRegisteredDTO) {
		try {
			if (userRegisteredDTO == null || userRegisteredDTO.getRole() == null) {
				throw new IllegalArgumentException("UserDTO or role cannot be null");
			}

			Role role;

			// Add a null check for userRegisteredDTO.getRole() before comparing its value
			if ("USER".equals(userRegisteredDTO.getRole())) {
				role = roleRepo.findByRole("ROLE_USER");
			} else if ("ADMIN".equals(userRegisteredDTO.getRole())) {
				role = roleRepo.findByRole("ROLE_ADMIN");
			} else {
				throw new IllegalArgumentException("Invalid role: " + userRegisteredDTO.getRole());
			}

			User user = new User();
			user.setEmail(userRegisteredDTO.getEmail());
			user.setUserName(userRegisteredDTO.getUserName());
			user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
			user.setRole(role);

			return userRepo.save(user);
		} catch (Exception ex) {
			logger.error("Error saving user: {}", ex.getMessage());
			// You may want to throw a custom exception here
			throw new RuntimeException("Error saving user", ex);
		}
	}
}
