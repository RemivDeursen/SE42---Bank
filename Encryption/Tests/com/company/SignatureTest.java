package com.company;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 25-6-2018.
 */
public class SignatureTest {
    @Test
    public void createSignature() throws Exception {
        String test = "test";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));

        assertNotSame(test,Signature.createSignature(stream));
    }


    @Test
    public void getInput() throws Exception {
        String input = Signature.getInput();
        assertEquals("Here's a message ".trim(),input.trim());
    }

}