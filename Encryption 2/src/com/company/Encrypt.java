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

    public static EncryptionHolder encrypt(InputStream password )throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException,
            InvalidArgumentException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        byte[] salt = new byte[8];
        new SecureRandom().nextBytes(salt);

        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt,25);
        System.out.println("Create a password");
        PBEKeySpec pbeKeySpec = new PBEKeySpec(readPassword(password));

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

        return new EncryptionHolder(secretKey,pbeParameterSpec,salt);
    }

    public static byte[] encryptInput(EncryptionHolder encryptionHolder, InputStream input){
        try {
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            cipher.init(Cipher.ENCRYPT_MODE, encryptionHolder.getSecretKey(), encryptionHolder.getPbeParameterSpec());

            System.out.println("What do you want to encrypt?");
            String inputEncrypt = new BufferedReader(new InputStreamReader(input)).readLine();
            byte[] ciphertext = cipher.doFinal(inputEncrypt.getBytes());
            encryptionHolder.setCiphertext(ciphertext);

            return ciphertext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writeFile(EncryptionHolder holder, InputStream stream)  {
        try {
            System.out.println("Give the file a name");
            File file = new File(new BufferedReader(new InputStreamReader(stream)).readLine());
            file.createNewFile();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(holder.getSalt());
            objectOutputStream.writeObject(holder.getCiphertext());
            objectOutputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
