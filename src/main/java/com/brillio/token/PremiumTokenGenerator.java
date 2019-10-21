package com.brillio.token;

public class PremiumTokenGenerator implements TokenGenerator {
    private static int counter = 1;

    public String generateToken() {
        return "P" + counter++;
    }
}
