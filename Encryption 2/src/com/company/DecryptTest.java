package com.company;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 25-6-2018.
 */
public class DecryptTest {

    private EncryptionHolder holder;

    @Test
    public void readFileAndDecrypt() throws Exception {
        String test = "max";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));

        holder = Decrypt.readFile(stream);

        assertNotNull(holder.getSalt());
        assertNotNull(holder.getCiphertext());

        InputStream stream1 = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));

        assertEquals("max",Decrypt.decrypt(holder,stream1));
    }

}