package com.workout.workoutcom.configuration.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
public class JwtUtil {

    private final long EXPIRATIONTIME;//만료시간
    private final KeyPair keyPair; // ES256용 KeyPair

    public JwtUtil(@Value("${jwt.EXPIRATIONTIME}")long expirationTime) {
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256); //256비트 키 크기 설정
            this.keyPair = keyPairGenerator.generateKeyPair(); // 공개 키 & 개인 키 생성
            EXPIRATIONTIME = expirationTime;
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("키 생성 실패",e);
        }

    }

    //토큰 생성(ES256알고리즘 사용)
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 토큰의 subject(사용자 정보)설정
                .setIssuedAt(new Date()) // 토큰의 생성 시간 설정
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATIONTIME)) // 토큰 만료시간설정
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.ES256)//서명(Signature)추가
                .compact(); //최종적으로 JWT문자열 생성
    }

    //토큰 검증
    public boolean validateToken(String token){
        Jwts.parserBuilder() //JWT파서를 생성하는 빌더 객체
                .setSigningKey(keyPair.getPublic()) // 서명을 검증할 때 사용할 키 지정
                .build() //실제 파서를 생성
                .parseClaimsJws(token); //토큰 파싱 및 검증
        return true;
    }

    //토큰에서 사용자명 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())//토큰 검증을 위해 서명 키 지정
                .build()
                .parseClaimsJws(token) // 토큰 파싱
                .getBody() // 토큰 페이로드 부분 가져오기
                .getSubject(); // 페이로드에서 subject(username)추출
    }
}