package com.company;

import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import static sun.security.util.Password.readPassword;

/**
 * Created by maxhe on 21-6-2018.
 */
public class Decrypt {
    public static EncryptionHolder readFile(InputStream filename) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException,
    InvalidArgumentException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
        System.out.println("Give the name of the file you want to decrypt");
        String result = new BufferedReader(new InputStreamReader(filename)).readLine();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(result));
        byte[] salt = (byte[]) objectInputStream.readObject();
        byte[] encryptMessage = (byte[]) objectInputStream.readObject();
        objectInputStream.close();

        EncryptionHolder holder = new EncryptionHolder();
        holder.setCiphertext(encryptMessage);
        holder.setSalt(salt);

        return holder;
    }

    public static String decrypt(EncryptionHolder holder, InputStream stream) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(holder.getSalt(),25);
        System.out.println("What's your password?");
        PBEKeySpec pbeKeySpec = new PBEKeySpec(readPassword(stream));

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.DECRYPT_MODE,secretKey,pbeParameterSpec);

        byte[] message = cipher.doFinal(holder.getCiphertext());
        System.out.println("Output: " + new String(message,"UTF-8"));

        return new String(message,"UTF-8");
    }
}
