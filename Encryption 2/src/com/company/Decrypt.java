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
    public static void decrypt() throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException,
    InvalidArgumentException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
        System.out.println("Give the name of the file you want to decrypt");
        String result = new BufferedReader(new InputStreamReader(System.in)).readLine();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(result));
        byte[] salt = (byte[]) objectInputStream.readObject();
        byte[] encryptMessage = (byte[]) objectInputStream.readObject();
        objectInputStream.close();

        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt,25);
        System.out.println("What's your password?");
        PBEKeySpec pbeKeySpec = new PBEKeySpec(readPassword(System.in));

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.DECRYPT_MODE,secretKey,pbeParameterSpec);

        byte[] message = cipher.doFinal(encryptMessage);
        System.out.println(new String(message,"UTF-8"));
    }
}
