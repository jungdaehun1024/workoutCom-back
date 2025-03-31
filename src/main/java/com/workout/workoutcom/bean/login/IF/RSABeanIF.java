package com.workout.workoutcom.bean.login.IF;

import java.security.PrivateKey;

public interface RSABeanIF {
    public String getPublicKeyModules();
    public String getPublicKeyExponent() throws Exception;
    public PrivateKey getPrivateKey() throws Exception;
}
