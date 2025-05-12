package com.workout.workoutcom.configuration.auth;

import com.workout.workoutcom.dao.user.UserMapper;
import com.workout.workoutcom.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    @Autowired
    public PrincipalDetailService(UserMapper userMapper){
        this.userMapper = userMapper;

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userMapper.getUserInfoByAccount(username)
                            .orElseThrow(()->new UsernameNotFoundException("User not found"));
        
        return new PrincipalDetails(user); // PrincipalDetails객체 생성

    }
}
