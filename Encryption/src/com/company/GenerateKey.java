package com.company;

import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.*;

public class GenerateKey {

    public static void main(String[] args) {

        System.out.println("Generating keys");

        try{
            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = new SecureRandom();
            keyGenerator.initialize(1024,secureRandom);
            KeyPair keyPair = keyGenerator.generateKeyPair();

            new ObjectOutputStream(new FileOutputStream("rsa_public")).writeObject(keyPair.getPublic());
            new ObjectOutputStream(new FileOutputStream("rsa_private")).writeObject(keyPair.getPrivate());

            System.out.println("Keys generated");
        }
        catch (IOException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public static PublicKey getPublicKey(){

        PublicKey key = null;

        try{
            key = (PublicKey) new ObjectInputStream(new FileInputStream("rsa_public")).readObject();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return key;
    }

    public static PrivateKey getPrivateKey(){

        PrivateKey key = null;

        try {
            key = (PrivateKey) new ObjectInputStream(new FileInputStream("rsa_private")).readObject();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return key;
    }
}
