package com.siigfp.SIIGFPV1.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.siigfp.SIIGFPV1.dao.UserDao;
import com.siigfp.SIIGFPV1.entity.JwtRequest;
import com.siigfp.SIIGFPV1.entity.JwtResponse;
import com.siigfp.SIIGFPV1.entity.Users;
import com.siigfp.SIIGFPV1.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	public JwtResponse createJwtToken (JwtRequest jwtRequest) throws Exception {
		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		authenticate(userName, userPassword);
		final UserDetails userDetails = loadUserByUsername(userName);
		
		String newGeneratedToke = jwtUtil.generateToke(userDetails);
		
		Users user = userDao.findAllByUserName(userName).get(0);
		
		return new JwtResponse(user , newGeneratedToke);
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user = userDao.findAllByUserName(username).get(0);
		
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(
					user.getUserName(), user.getUserPassword(), getAuthorities(user));
		}else {
			throw new UsernameNotFoundException("User name is not valid");
		}
	}
	
	private Set getAuthorities (Users user) {
		Set authorities = new HashSet();
		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		});
		
		return authorities;
	}
	
	private void authenticate (String userName , String userPassword ) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new Exception ("User is disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad credentials from user");
		}
	}

}
