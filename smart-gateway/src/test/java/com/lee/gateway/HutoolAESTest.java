package com.lee.gateway;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class HutoolAESTest {
    public static void main(String[] args) {
        try {
            a();
        } catch (Exception e) {
            e.printStackTrace();
        }
        decode();
    }

    public static void a() throws Exception {
        //传给crypto的key、iv要使用base64格式
        //ZGIyMTM5NTYxYzlmZTA2OA==
        byte[] bytes = "1234123412ABCDEF".getBytes();
        byte[] bytes1 = "ABCDEF1234123412".getBytes();
        //bytes = "db2139561c9fe068".getBytes();
        String base64Str = Base64Utils.encodeToString(bytes);
        System.out.println(base64Str);

        String crypto = "34439a96e68b129093105b67de81c0fc";
        crypto = "2e26c63de132066249922c604680c7fa";
        byte[] data = HexUtil.decodeHex(crypto.toCharArray());
        byte[] s = AES_CBC_Decrypt(data, bytes, bytes1);
        System.out.println(new String(s));
    }

    public static byte[] AES_CBC_Decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(data);
    }

    private static Cipher getCipher(int mode, byte[] key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        cipher.init(mode, secretKeySpec, new IvParameterSpec(iv));

        return cipher;
    }

    private static void decode() {
        String pass = "2e26c63de132066249922c604680c7fa";
        String aseKey = "1234123412ABCDEF";
        String aseIv = "ABCDEF1234123412";
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,
                new SecretKeySpec(aseKey.getBytes(), "AES"),
                new IvParameterSpec(aseIv.getBytes()));
        byte[] result = aes.decrypt(HexUtil.decodeHex(pass.toCharArray()));
        String r = new String(result, StandardCharsets.UTF_8);
        System.out.println(r);
    }
}
