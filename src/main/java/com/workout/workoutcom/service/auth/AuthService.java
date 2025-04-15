package com.workout.workoutcom.service.auth;

import com.workout.workoutcom.bean.login.IF.RSABeanIF;
import com.workout.workoutcom.bean.login.RSADecryptBean;
import com.workout.workoutcom.bean.login.RSARandBean;
import com.workout.workoutcom.bean.login.RsaArrBean;
import com.workout.workoutcom.configuration.auth.JwtUtil;
import com.workout.workoutcom.configuration.auth.PrincipalDetailService;
import com.workout.workoutcom.configuration.auth.PrincipalDetails;
import com.workout.workoutcom.dao.user.UserMapper;
import com.workout.workoutcom.dto.auth.UserDto;
import com.workout.workoutcom.dto.auth.PublicKeyResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.PrivateKey;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    private final RsaArrBean rsaArrBean;
    private final RSARandBean rsaRandBean;
    private final RSADecryptBean rsaDecryptBean;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PrincipalDetailService principalDetailService;
    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${RSASETTING}")
    private String resSetting;

    @Autowired
    public AuthService(RsaArrBean rsaArrBean ,RSARandBean rsaRandBean,RSADecryptBean rsaDecryptBean,PasswordEncoder passwordEncoder,UserMapper userMapper,JwtUtil jwtUtil,PrincipalDetailService principalDetailService,AuthenticationManager authenticationManager,RedisTemplate<String,Object> redisTemplate){
        this.rsaArrBean = rsaArrBean;
        this.rsaRandBean = rsaRandBean;
        this.rsaDecryptBean = rsaDecryptBean;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.principalDetailService = principalDetailService;
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;

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
    public void registerUser(UserDto user, HttpSession session)throws Exception{
        String encryptedPwd = user.getPassword();
        String rawPwd = rsaDecryptBean.decryptRsa(session,encryptedPwd); // RSA 복호화 진행
        String encodePwd = passwordEncoder.encode(rawPwd); // 원문(rawPwd) -> Bcrypt암호화
        user.setPassword(encodePwd);
        userMapper.registerUser(user);
    }

    //로그인 처리
    public void loginUser(HttpServletResponse response, UserDto user)throws Exception{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getAccount(),user.getPassword())
        );
        PrincipalDetails userDetails = (PrincipalDetails)authentication.getPrincipal();
        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

        ResponseCookie cookie = ResponseCookie.from("jwt",jwtToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(60*60)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
    }

    //로그인 여부 체크
    public boolean loginCheck(HttpServletRequest request){
        if(request.getCookies() == null) return false;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("jwt")) {
                    String token = cookie.getValue();
                    if(jwtUtil.validateToken(token)){
                        return true;
                    };
                }
            }
        }
        return false;
    }

    //로그아웃
    public void logout(HttpServletResponse response, HttpServletRequest request){
        String token = JwtUtil.extractTokenFromCookie(request); //HttpOnly쿠키에서 JWT추출
        if(token == null) {
            return;
        }
        long expiration = jwtUtil.getExpirationTime(token); // 만료시간(밀리초)
        long ttl = expiration - System.currentTimeMillis(); // 현재를 기준으로 남은 유효 시간 계산
        if(ttl > 0) { //만료 시간이 있으면 Redis에 토큰 저장
            redisTemplate.opsForValue()
                    .set("blacklist:"+token,"true",ttl, TimeUnit.MILLISECONDS); // "blacklist:토큰 (key) , true(value) , ttl(유효기간),MILLISECONDS 단위로 저장
        }
        ResponseCookie expiredCookie = ResponseCookie.from("jwt","")
                .httpOnly(true)
                .secure(false)
                .path("/") // "/"로 설정 시 사이트 전체에서 쿠키가 적용
                .sameSite("Lax") // 다른 사이트에서 쿠키가 전송되는 방식을 제한
                .maxAge(0)// 즉시 만료
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE,expiredCookie.toString());

    }
}
