//package com.task_management_system.task.services;
//
//import com.task_management_system.task.model.User;
//import com.task_management_system.task.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String login = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        User user = findUserByLoginIdentifier(login);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Invalid password");
//        }
//
//        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
//
//        return new UsernamePasswordAuthenticationToken(user, password, authorities);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
//
//    private User findUserByLoginIdentifier(String login) {
//        User user = userRepository.findByUserName(login);
//        if (user != null) return user;
//
//        user = userRepository.findByEmail(login);
//        if (user != null) return user;
//
//        return userRepository.findByPhoneNumber(login);
//    }
//}