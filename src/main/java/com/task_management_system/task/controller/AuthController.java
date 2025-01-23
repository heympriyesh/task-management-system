package com.task_management_system.task.controller;

import com.task_management_system.task.CustomUserDetail;
import com.task_management_system.task.dto.common.ApiResponse;
import com.task_management_system.task.dto.request.LoginRequest;
import com.task_management_system.task.dto.response.CreateUserResponse;
import com.task_management_system.task.dto.response.LoginResponse;
import com.task_management_system.task.model.User;
import com.task_management_system.task.repository.UserRepository;
import com.task_management_system.task.services.JwtService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    AuthController(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtService jwtService){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @PostMapping("login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest loginRequest){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword())
        );
        if(authentication.isAuthenticated()){
            CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
            LoginResponse loginResponse=modelMapper.map(userDetails,LoginResponse.class);
            loginResponse.setToken(jwtService.generateToken(userDetails.getUser()));
            return
                    ResponseEntity
                            .status(HttpStatus.OK)
                            .body(ApiResponse.success("User Logged In Successfully",loginResponse));

        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Authentication failed. Please check your credentials."));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CreateUserResponse>> register(@RequestBody @Valid User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        CreateUserResponse createUserResponse=modelMapper.map(savedUser,CreateUserResponse.class);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User Create successfully",createUserResponse));
    }

}
