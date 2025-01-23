package com.task_management_system.task.services;


import com.task_management_system.task.CustomUserDetail;
import com.task_management_system.task.model.User;
import com.task_management_system.task.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    CustomUserDetailService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findByLoginField(login);

//        User user=userRepository.findByUserName(username);
        if(Objects.isNull(user)){
            System.out.println("User Not available");
            throw new UsernameNotFoundException("Use Not Found");
        }
        return new CustomUserDetail(user);
    }

    private User findByLoginField(String login) {
        // Try finding by username
        User user = userRepository.findByUserName(login);
        if (user != null) return user;

        // Try finding by email
        user = userRepository.findByEmail(login);
        if (user != null) return user;

        // Try finding by phone
        return userRepository.findByPhoneNumber(login);
    }
}
