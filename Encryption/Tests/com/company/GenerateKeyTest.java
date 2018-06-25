package com.company;

import org.junit.Before;
import org.junit.Test;

import java.security.PublicKey;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 25-6-2018.
 */
public class GenerateKeyTest {

    @Test
    public void main() throws Exception {
    }

    @Test
    public void getPublicKey() throws Exception {
        PublicKey publicKey = GenerateKey.getPublicKey();
        assertNotNull(publicKey);
    }

    @Test
    public void getPrivateKey() throws Exception {
        assertNotNull(GenerateKey.getPrivateKey());
    }

}