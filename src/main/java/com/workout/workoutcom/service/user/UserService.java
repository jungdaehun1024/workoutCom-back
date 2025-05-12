package com.workout.workoutcom.service.user;

import com.workout.workoutcom.configuration.auth.PrincipalDetails;
import com.workout.workoutcom.dao.user.UserMapper;
import com.workout.workoutcom.dto.user.PasswordDto;
import com.workout.workoutcom.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //회원정보 조회 (MyPage)
    public UserDto findUserInfoByUsername(PrincipalDetails principalDetails) throws Exception {
        String account = principalDetails.getUsername();
        UserDto userInfo = userMapper.getUserInfoByAccount(account).orElseThrow(()->new RuntimeException("사용자 정보를 찾을 수 없습니다."));
        return userInfo;
    }
    
    //패스워드 변경 (MyPage)
    @Transactional
    public void changePassword(PrincipalDetails principalDetails,PasswordDto passwordDto) throws Exception {
        String account = principalDetails.getUsername();
        UserDto userInfo = userMapper.getUserInfoByAccount(account).orElseThrow(()->new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(passwordDto.getOriginPassword(),userInfo.getPassword())){
            throw new RuntimeException("입력한 기존 비밀번호가 일치하지 않습니다.");
        }
        userInfo.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));

        int updateCount = userMapper.updateUserPassword(userInfo);
        if(updateCount == 0){
            throw new RuntimeException("비밀번호 업데이트에 실패했습니다.");
        }
    }
}
