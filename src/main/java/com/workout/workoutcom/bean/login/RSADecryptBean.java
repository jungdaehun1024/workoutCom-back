package com.workout.workoutcom.bean.login;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.util.Base64;

@Component
public class RSADecryptBean {
    public String decryptRsa(HttpSession session,String encryptedData) throws Exception {
        //세션은 모든 데이터를 Object타입으로 저장한다.
        PrivateKey privateKey = (PrivateKey) session.getAttribute("rsaPrivateKey");

            //암호화된 데이터를 Base64로 디코딩
            byte[] encryptBytes = Base64.getDecoder().decode(encryptedData);

            //RSA-OAEP 복호화
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptData = cipher.doFinal(encryptBytes);
            return new String(decryptData,"UTF-8");

    }
}
