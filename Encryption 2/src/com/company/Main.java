package com.company;

import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("What do want to do? (encrypt or decrypt)");

            String result = new BufferedReader(new InputStreamReader(System.in)).readLine();

            if(result.equals("encrypt")){
                Encrypt encrypt = new Encrypt();
                EncryptionHolder holder = encrypt.encrypt(System.in);
                encrypt.encryptInput(holder,System.in);
                Encrypt.writeFile(holder,System.in);
            }
            else if(result.equals("decrypt")){
                Decrypt decrypt = new Decrypt();
                EncryptionHolder holder = decrypt.readFile(System.in);
                decrypt.decrypt(holder, System.in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
