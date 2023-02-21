package com.api.bookrating.services;

import com.api.bookrating.repositories.UserRepository;
import com.api.bookrating.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userNotReady = userRepository.findByUsername(username);
        if(userRepository.findByUsername(username) == null){
            throw new UsernameNotFoundException(username + " was not found");
        }

        return org.springframework.security.core.userdetails
                .User
                .withUsername(userNotReady.getUsername())
                .password(userNotReady.getPassword())
                .authorities(userNotReady.getRole().name())
                .build();
    }


}
