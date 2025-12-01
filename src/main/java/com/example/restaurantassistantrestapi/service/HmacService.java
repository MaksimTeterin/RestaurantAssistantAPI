package com.example.restaurantassistantrestapi.service;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class HmacService {

    @Value("${RESTAURANTAPIAUTH_SECRET}")
    private String bffSecret;

    public boolean verifySignature(String email, long timestamp, String signature) {
        try {
            String data = email + timestamp;

            Mac sha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(bffSecret.getBytes(), "HmacSHA256");
            sha256.init(secretKey);

            byte[] hash = sha256.doFinal(data.getBytes());
            String expected = Hex.encodeHexString(hash);

            return expected.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }
}
