package com.workout.workoutcom.service.auth;

import com.workout.workoutcom.bean.login.IF.RSABeanIF;
import com.workout.workoutcom.bean.login.RSADecryptBean;
import com.workout.workoutcom.bean.login.RSARandBean;
import com.workout.workoutcom.bean.login.RsaArrBean;
import com.workout.workoutcom.dao.user.UserMapper;
import com.workout.workoutcom.dto.auth.CreateUserDto;
import com.workout.workoutcom.dto.auth.PublicKeyResponseDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.PrivateKey;

@Service
public class AuthService {
    private final RsaArrBean rsaArrBean;
    private final RSARandBean rsaRandBean;
    private final RSADecryptBean rsaDecryptBean;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${RSASETTING}")
    private String resSetting;

    @Autowired
    public AuthService(RsaArrBean rsaArrBean ,RSARandBean rsaRandBean,RSADecryptBean rsaDecryptBean,PasswordEncoder passwordEncoder,UserMapper userMapper){
        this.rsaArrBean = rsaArrBean;
        this.rsaRandBean = rsaRandBean;
        this.rsaDecryptBean = rsaDecryptBean;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    // Session에 키 등록 및 공개키 모듈러스,공개지수 반환
    public PublicKeyResponseDto keyGenerate(HttpSession session)throws Exception{
            RSABeanIF rsa = getRsa();
            String publicKeyModules =rsa.getPublicKeyModules();
            String publicKeyExponent = rsa.getPublicKeyExponent();
            PrivateKey privateKey = rsa.getPrivateKey();

            session.setAttribute("rsaPublicKeyModules", publicKeyModules);
            session.setAttribute("rsaPublicKeyExponent",publicKeyExponent);
            session.setAttribute("rsaPrivateKey",privateKey);
            PublicKeyResponseDto publicKey = new PublicKeyResponseDto(publicKeyModules,publicKeyExponent);
            return  publicKey;
    }

    public RSABeanIF getRsa(){
        if("Y".equals(resSetting)){
            return rsaArrBean;
        }else{
            return rsaRandBean;
        }
    }

    //회원가입
    public void registerUser(CreateUserDto user,HttpSession session)throws Exception{
        String encryptedPwd = user.getEncryptPwd();
        String rawPwd = rsaDecryptBean.decryptRsa(session,encryptedPwd); // RSA 복호화 진행
        String encodePwd = passwordEncoder.encode(rawPwd); // 원문(rawPwd) -> Bcrypt암호화
        user.setEncryptPwd(encodePwd);
        userMapper.registerUser(user);
    }
}
