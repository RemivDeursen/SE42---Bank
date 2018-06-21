package com.company;

import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.crypto.*;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import static sun.security.util.Password.readPassword;
/**
 * Created by maxhe on 21-6-2018.
 */
public class Encrypt {
    public static void encrypt()throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException,
            InvalidArgumentException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        byte[] salt = new byte[8];
        new SecureRandom().nextBytes(salt);

        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt,25);
        System.out.println("Create a password");
        PBEKeySpec pbeKeySpec = new PBEKeySpec(readPassword(System.in));

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);

        System.out.println("What do you want to encrypt?");
        String input = new BufferedReader(new InputStreamReader(System.in)).readLine();
        byte[] ciphertext = cipher.doFinal(input.getBytes());

        System.out.println("Give the file a name");
        File file = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        file.createNewFile();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(salt);
        objectOutputStream.writeObject(ciphertext);
        objectOutputStream.close();
    }
}
