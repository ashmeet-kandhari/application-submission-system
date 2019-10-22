package com.brillio.counter;

import com.brillio.queue.ProducerKafka;
import com.brillio.token.NormalTokenGenerator;
import com.brillio.token.PremiumTokenGenerator;
import com.brillio.token.TokenGenerator;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;

public class TokenGenerationCounter {

  final static Logger logger = Logger.getLogger(TokenGenerationCounter.class);

  private TokenGenerator normalTokenGenerator;
  private TokenGenerator premiumTokenGenerator;
  private ProducerKafka producerKafka;
  private String normalQueueName;
  private String premiumQueueName;

  public TokenGenerationCounter(Properties properties, ProducerKafka producerKafka) {
    this.normalQueueName = properties.getProperty("normal.queue.name");
    this.premiumQueueName = properties.getProperty("premium.queue.name");
    this.producerKafka = producerKafka;
    this.normalTokenGenerator = new NormalTokenGenerator();
    this.premiumTokenGenerator = new PremiumTokenGenerator();

  }

  public TokenGenerationCounter(Properties properties, ProducerKafka producerKafka,
      NormalTokenGenerator normalTokenGenerator, PremiumTokenGenerator premiumTokenGenerator) {
    this.normalQueueName = properties.getProperty("normal.queue.name");
    this.premiumQueueName = properties.getProperty("premium.queue.name");
    this.producerKafka = producerKafka;
    this.normalTokenGenerator = normalTokenGenerator;
    this.premiumTokenGenerator = premiumTokenGenerator;
  }

  public String generateNormalTokenAfterVerification() {
    String token = normalTokenGenerator.generateToken();
    try {
      producerKafka.sendMessage(normalQueueName, token);
      return token;
    } catch (ExecutionException | InterruptedException e) {
      logger.error("Publishing to Queue Failed", e);
      producerKafka.close();
    }
    return "Err";
  }

  public String generatePremiumTokenAfterVerification() {
    String token = premiumTokenGenerator.generateToken();
    try {
      producerKafka.sendMessage(premiumQueueName, token);
      return token;
    } catch (ExecutionException | InterruptedException e) {
      logger.error("Publishing to Queue Failed", e);
      producerKafka.close();
    }
    return "Err";
  }
}
