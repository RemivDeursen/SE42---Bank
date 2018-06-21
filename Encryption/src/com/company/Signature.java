package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;

/**
 * Created by maxhe on 20-6-2018.
 */
public class Signature {

    public static void main (String[] args){

        PrivateKey privateKey = GenerateKey.getPrivateKey();

        String input = getInput();

        try {

            java.security.Signature signature = java.security.Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);

            byte[] signatureBytes = signature.sign();

            String name = new BufferedReader(new InputStreamReader(System.in)).readLine();
            File signatureFile = new File(String.format("INPUT(SignedBy%s)",name.replace(" ","")));
            signatureFile.createNewFile();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(signatureFile));

            objectOutputStream.writeInt(signatureBytes.length);
            objectOutputStream.writeObject(signatureBytes);
            objectOutputStream.writeObject(input);
            objectOutputStream.close();
        }

        catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException e){
            e.printStackTrace();
        }
    }

    public static String getInput(){
        String input = "";

        try {
            String UTF_8 = StandardCharsets.UTF_8.toString();
            input = new String(Files.readAllBytes(Paths.get("INPUT.ext")), UTF_8);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return input;
    }
}
