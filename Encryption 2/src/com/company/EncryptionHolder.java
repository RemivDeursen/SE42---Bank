package com.company;

import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Created by maxhe on 25-6-2018.
 */
public class EncryptionHolder {
    private SecretKey secretKey;
    private PBEParameterSpec pbeParameterSpec;
    private byte[] salt;
    private byte[] ciphertext;

    public EncryptionHolder(SecretKey secretKey, PBEParameterSpec pbeParameterSpec, byte[] salt) {
        this.secretKey = secretKey;
        this.pbeParameterSpec = pbeParameterSpec;
        this.salt = salt;
    }

    public EncryptionHolder() {
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public PBEParameterSpec getPbeParameterSpec() {
        return pbeParameterSpec;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(byte[] ciphertext) {
        this.ciphertext = ciphertext;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public void setPbeParameterSpec(PBEParameterSpec pbeParameterSpec) {
        this.pbeParameterSpec = pbeParameterSpec;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
