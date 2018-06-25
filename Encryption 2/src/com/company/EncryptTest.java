package com.company;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 25-6-2018.
 */
public class EncryptTest {

    public EncryptionHolder holder;

    @Test
    public void encrypt() throws Exception {
        String test = "max";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));

        holder = Encrypt.encrypt(stream);

        assertNotNull(holder.getPbeParameterSpec());
        assertNotNull(holder.getSalt());
        assertNotNull(holder.getSecretKey());

        String test1 = "max";
        InputStream stream1 = new ByteArrayInputStream(test1.getBytes(StandardCharsets.UTF_8));

        assertNotNull(Encrypt.encryptInput(holder,stream1));

        String test2 = "max";
        InputStream stream2 = new ByteArrayInputStream(test2.getBytes(StandardCharsets.UTF_8));

        EncryptionHolder holder = Decrypt.readFile(stream2);

        InputStream stream3 = new ByteArrayInputStream(test2.getBytes(StandardCharsets.UTF_8));

        String result = Decrypt.decrypt(holder,stream3);

        assertEquals("max",result);
    }

}