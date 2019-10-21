package com.brillio.counter;

import com.brillio.kafka.ProducerKafka;
import com.brillio.token.NormalTokenGenerator;
import com.brillio.token.PremiumTokenGenerator;
import com.brillio.token.TokenGenerator;

import java.util.concurrent.ExecutionException;

public class TokenGenerationCounter {
    private TokenGenerator normalTokenGenerator = new NormalTokenGenerator();
    private TokenGenerator premiumTokenGenerator = new PremiumTokenGenerator();


    public String generateNormalTokenAfterVerification() {
        String token = normalTokenGenerator.generateToken();
        try {
            ProducerKafka.sendMessage("normalToken", token);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return token;
    }

    public String generatePremiumTokenAfterVerification() {
        String token = premiumTokenGenerator.generateToken();
        try {
            ProducerKafka.sendMessage("premiumToken", token);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return token;
    }
}
