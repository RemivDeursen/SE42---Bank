package com.company;

import java.io.*;
import java.security.*;
import java.security.Signature;

/**
 * Created by maxhe on 20-6-2018.
 */
public class Verify {

    public static void main(String[] args){
        boolean validFile = false;

        try {
            String name = new BufferedReader(new InputStreamReader(System.in)).readLine();

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(String.format("INPUT(SignedBy%s)",name.replace(" ",""))));

            int singnatureSize =  objectInputStream.readInt();
            byte[] signatureBytes = (byte[]) objectInputStream.readObject();

            String output = (String) objectInputStream.readObject();

            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(GenerateKey.getPublicKey());
            validFile = signature.verify(signatureBytes);

            if(validFile){
                System.out.println("Signature is valid");
                System.out.println(output);
            }
            else{
                System.out.println("Signature isn't valid");
            }

        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }


    }
}
