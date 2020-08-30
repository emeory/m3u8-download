package com.gitee.nihaoa.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CommonUtil {

  /**
   *
   * 解决java不支持AES/CBC/PKCS7Padding模式解密
   *
   */
  static {
    Security.addProvider(new BouncyCastleProvider());
  }

  public static byte[] decrypt(byte[] data, byte[] key, byte[] ivByte) throws Exception {
    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding  ", "BC");

    if (ivByte != null){
      if (ivByte.length != 16){
        throw new IllegalArgumentException("加密向量长度不是16字节：" + ivByte.length);
      }
      //如果m3u8有IV标签，那么IvParameterSpec构造函数就把IV标签后的内容转成字节数组传进去
      IvParameterSpec paramSpec = new IvParameterSpec(ivByte);
      cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
    }else {
      cipher.init(Cipher.DECRYPT_MODE, keySpec);
    }
    return cipher.doFinal(data);
  }

  public static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    if ((len & 1) == 1) {
      s = "0" + s;
      len++;
    }
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
        + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }

  private static String getMD5Str(String str) {
    byte[] digest = null;
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      digest  = md5.digest(str.getBytes("utf-8"));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    //16是表示转换为16进制数
    return new BigInteger(1, digest).toString(16);
  }

  public static String get16MD5(String str){
    return getMD5Str(str).substring(8, 24);
  }

}
