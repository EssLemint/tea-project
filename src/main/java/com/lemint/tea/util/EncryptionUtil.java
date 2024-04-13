package com.lemint.tea.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptionUtil {

  public static String alg = "AES/CBC/PKCS5Padding";
  private static final String key = "7E6YRDQ59E9UTAM9M3G4OJU2RZTGF6K4";
  private static final String iv = key.substring(0, 16); // 16byte

  public static String encrypt(String text) throws Exception {
    Cipher cipher = Cipher.getInstance(alg);
    SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
    IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

    byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
    return Base64Utils.encodeToUrlSafeString(encrypted);
  }

  public static  String decrypt(String cipherText) {
    try {
      // #. 암호화되지 않은 일반 텍스트의 경우 그냥 리턴
      // #. TODO 현재는 아이디만을 대상으로 하지만 다른 데이터를 암호화할 경우도 포함하기
      if (cipherText.matches("[+-]?\\d*(\\.\\d+)?")) {
        return cipherText;
      }

      Cipher cipher = Cipher.getInstance(alg);
      SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
      IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
      byte[] decodedBytes = Base64Utils.decodeFromUrlSafeString(cipherText);
      byte[] decrypted = cipher.doFinal(decodedBytes);
      return new String(decrypted, StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
