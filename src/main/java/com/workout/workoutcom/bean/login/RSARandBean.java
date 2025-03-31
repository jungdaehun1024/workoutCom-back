package com.workout.workoutcom.bean.login;

import com.workout.workoutcom.bean.login.IF.RSABeanIF;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Service
public class RSARandBean implements RSABeanIF {
    private KeyPairGenerator keyPairGenerator;
    private RSAPublicKeySpec publicSpec;
    public PrivateKey privateKey;

    public RSARandBean(){
        try{
            // KeyPairGenerator를 RSA 알고리즘을 사용해 초기화
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            //설정된 알고리즘을 기반으로 RSA 공개키 & 개인키 쌍 생성
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA"); //RSA에 대한 KeyFactory 객체 반환
            PublicKey publicKey = keyPair.getPublic(); //공개 키 저장
            privateKey = keyPair.getPrivate();//개인키 저장
            publicSpec = (RSAPublicKeySpec)keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class); // 공개키 스팩 추출


//            //개인키를 Byte배열로
//           byte[] privateKeyBytes = privateKey.getEncoded();
//
//           //Byte배열을 Base64문자열로 표현
//           String base64PrivateKey = Base64.getEncoder().encodeToString(privateKeyBytes);
//
//           System.out.println("-----------------START-----------------------");
//           System.out.println("개인키 (base64인코딩):");
//           System.out.println(base64PrivateKey);
//           System.out.println("공개키 모듈러스(16진수화한 값):");
//           System.out.println(getPublicKeyModules());
//           System.out.println("공개키 모듈러스(원본):");
//           System.out.println(publicSpec.getModulus());
//           System.out.println("-----------------END-----------------------");
        }catch (Exception e){

        }
    }


    @Override
    public String getPublicKeyModules() { // 공개키 모듈러스를 16진수 문자열로 반환
        return publicSpec.getModulus().toString(16);
    }

    @Override
    public String getPublicKeyExponent() throws Exception { // 공개키의 공개지수를 16진수 문자열로 반환
        return publicSpec.getPublicExponent().toString(16);
    }

    @Override
    public PrivateKey getPrivateKey() throws Exception {
        return privateKey;
    }
}
