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
import org.springframework.web.client.RestTemplate;

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
        if(Objects.nonNull(userRepository.findUserByEmail(registerDto.getEmail()))){
                throw new BadRequestException(Message.EMAIL);
        }
        log.info(registerDto);
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
    public ResponseEntity<?> getUser(String token) throws IOException {

//        String uri =
//        "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=eyJhbG" +
//                "ciOiJSUzI1NiIsImtpZCI6IjcyOTE4OTQ1MGQ0OTAyODU3MDQy" +
//                "NTI2NmYwM2U3MzdmNDVhZjI5MzIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW5" +
//                "0cy5nb29nbGUuY29tIiwiYXpwIjoiNjYxNTQxMDQzMDY0LTFwcnIxYnJtazdycG84ZGl2YzFl" +
//                "bjYza2wwN2Q5YWxlLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNjYxNTQxMDQzMDY" +
//                "0LTFwcnIxYnJtazdycG84ZGl2YzFlbjYza2wwN2Q5YWxlLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3V" +
//                "iIjoiMTEzNTgzNTM2OTE3ODgyMzc5MDk3IiwiaGQiOiJhem9vbS5qcCIsImVtYWlsIjoiZHUuY29uZy5jdW9uZ0Bhem9v" +
//                "bS5qcCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoidVlRR3RBMHJMSm5RTEZPazNhZ2NhUSIsIm5hbWU" +
//                "iOiJDdW9uZyBEdSBDb25nIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hLS9BT2gx" +
//                "NEdpMWZFLUo4bzduVjM5ZkVNckF0b0dvWW9CLXZaZFJBVXdLSzRtMz1zOTYtYyIsImdpdmVuX25hbWUiOiJDdW9uZyIs" +
//                "ImZhbWlseV9uYW1lIjoiRHUgQ29uZyIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNjQ3NTkwMzQzLCJleHAiOjE2NDc1OTM5NDMs" +
//                "Imp0aSI6ImU3MmVlNWUwOTkzZGJlMDMwMDRlZGExZTY0YTBjOWRkZmU0NTQ5NzAifQ.H72FDyl6D5MThbqsWJTNTOuR038EAYK" +
//                "knSYXDJbpbywNTFTpHTL8PiRPefwRKFrH6mqvuF17IrsJnxbfVxifdleunAQjVxoh1Qk3tb1zlUv7SMtYovagsLHY7M8TIBrX" +
//                "oY-NqY1UkyjDaEXJcnuvCq_FdZkKWzWRoA0wNQSWqQG7eMW3SbLPXWh4-PXpclp7-XxcOTV4zUd8-9Xysm3PKWdLwP0M2-e4mV" +
//                "Xqlu8FyIMJCLMiP8RO8UwHktXsO-Lgt7GdSyt_3TeGbXNg3Cy_leXm16I_" +
//                "DOWXZe2b51nUYgUOUdRq8tS80axcPabZMwMIlVNLBo4h1hSV-aNms3wF7w";
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);
//        log.info(result);
        Long id = jwtTokenProvider.getUserIdFromJWT(token);
        Users user = userRepository.findUsersById(id);
        if(Objects.isNull(user)) {
            throw new BadRequestException(Message.NOT_FOUND);
        }
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        return ResponseEntity.ok(userDto);
    }
}
