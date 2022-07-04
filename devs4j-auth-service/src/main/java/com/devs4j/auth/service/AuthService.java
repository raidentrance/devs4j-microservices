package com.devs4j.auth.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devs4j.auth.config.JwtProvider;
import com.devs4j.auth.model.dto.TokenDto;
import com.devs4j.auth.model.dto.UserDto;
import com.devs4j.auth.model.entities.UserEntity;
import com.devs4j.auth.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private ModelMapper mapper;

	public UserDto save(UserDto user) {
		Optional<UserEntity> response = userRepository.findByUsername(user.getUsername());
		if (response.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					String.format("User %s already exist", user.getUsername()));
		}
		UserEntity entity = userRepository.save(new UserEntity(user.getUsername(), encoder.encode(user.getPassword())));
		return mapper.map(entity, UserDto.class);
	}

	public TokenDto login(UserDto user) {
		UserEntity userEntity = userRepository.findByUsername(user.getUsername())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		if (encoder.matches(user.getPassword(), userEntity.getPassword())) {
			return new TokenDto(jwtProvider.createToken(userEntity));
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	public TokenDto validate(String token) {
		jwtProvider.validate(token);
		String username = jwtProvider.getUsernameFromToken(token);
		userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		return new TokenDto(token);
	}
}
