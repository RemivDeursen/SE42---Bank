package com.company;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 25-6-2018.
 */
public class VerifyTest {
    @Test
    public void verifyFileTrue() throws Exception {
        String test = "test";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));

        assertTrue(Verify.verifyFile(stream));
    }

    @Test
    public void verifyFileFalse() throws Exception {
        String test = "test123";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));

        assertFalse(Verify.verifyFile(stream));
    }

}