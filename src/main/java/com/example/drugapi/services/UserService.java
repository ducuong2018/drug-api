package com.example.drugapi.services;

import com.example.drugapi.Dtos.*;
import com.example.drugapi.exceptions.BadRequestException;
import com.example.drugapi.exceptions.Message;
import com.example.drugapi.jwt.JwtTokenProvider;
import com.example.drugapi.models.Users;
import com.example.drugapi.repositories.UserRepository;
import com.example.drugapi.sercurity.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
@Log4j2
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public ResponseEntity<?> addUser(RegisterDto registerDto) throws IOException {
//        log.info(registerDto);
        if(Objects.nonNull(userRepository.findUserByEmail(registerDto.getEmail()))){
                throw new BadRequestException(Message.EMAIL);
        }

        registerDto.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        Users users = new Users();
        users.setEmail(registerDto.getEmail());
        users.setName(registerDto.getName());
        users.setPassword(registerDto.getPassword());
        userRepository.save(users);
        MessageDto message = new MessageDto();
        message.setMessage("Đăng ký thành công!");
        return ResponseEntity.ok(message);
    }
    public ResponseEntity<?> createToken(LoginDto loginDto) {
        ResponseToken responseToken;
        Users user = userRepository.findUserByEmail(loginDto.getEmail());
        if(Objects.isNull(user)){
            throw new BadRequestException(Message.NOT_FOUND_EMAIL);
        }
        try {
            responseToken = responseToken(loginDto,user);
        }
        catch (Exception e) {
            throw new BadRequestException(Message.PASSWORD_INVALID);
        }
        return ResponseEntity.ok(responseToken);
    }
    public UserDetails loadUserById(Long id){
        Users user = userRepository.findUsersById(id);
        return new CustomUserDetails(user);
    }
    protected ResponseToken responseToken(LoginDto loginDTO, Users user){
        ResponseToken responseToken = new ResponseToken();
        TokenInfo tokenInfo = new TokenInfo();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        tokenInfo.assignForm(user.getId());
        String token = jwtTokenProvider.generateToken(tokenInfo);
        String refreshToken = jwtTokenProvider.generateRefreshToken(tokenInfo);
        responseToken.setToken(token);
        return responseToken;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        Users user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new BadRequestException(Message.NOT_FOUND);
        }
        return new CustomUserDetails(user);
    }
}
