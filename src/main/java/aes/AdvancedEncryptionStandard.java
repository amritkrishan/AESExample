package aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AdvancedEncryptionStandard {

    //Encryption
    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    //Decryption
    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /*Pad key with '0' character to length 16, if less than 16*/
    public static String pad(String str, int size, char padChar)
    {
        StringBuffer padded = new StringBuffer(str);
        while (padded.length() < size)
        {
            padded.append(padChar);
        }
        return padded.toString();
    }

    public static void main(String[] args) {
        String key = "SomeRandomKey";
        String paddedKey = pad(key, 16, '0');
        String initVector = "RandomInitVector";

        System.out.println(decrypt(paddedKey, initVector,
                encrypt(paddedKey, initVector, "{\"key1\":\"name\",\"key2\":\"age\",\"key1\":\"name\"}")));
    }
}