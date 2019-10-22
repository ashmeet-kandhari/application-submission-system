package com.brillio.counter;

import com.brillio.queue.Producer;
import com.brillio.queue.ProducerKafka;
import com.brillio.token.NormalTokenGenerator;
import com.brillio.token.PremiumTokenGenerator;
import com.brillio.token.TokenGenerator;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.log4j.Logger;

public class TokenGenerationCounter {

  private final static Logger logger = Logger.getLogger(TokenGenerationCounter.class);

  private TokenGenerator normalTokenGenerator;
  private TokenGenerator premiumTokenGenerator;
  private Producer producer;
  private String normalQueueName;
  private String premiumQueueName;

  /**
   * Creates a TokenGeneration counter with default `NormalTokenGenerator` and
   * `PremiumTokenGenerator`
   *
   * @param properties should have normal.queue.name property and premium.queue.name property
   * @param producer   the queue producer to be used
   */
  public TokenGenerationCounter(Properties properties, Producer producer) {
    this.normalQueueName = properties.getProperty("normal.queue.name");
    this.premiumQueueName = properties.getProperty("premium.queue.name");
    this.producer = producer;
    this.normalTokenGenerator = new NormalTokenGenerator();
    this.premiumTokenGenerator = new PremiumTokenGenerator();

  }

  /**
   * Creates a TokenGenerationCounter
   *
   * @param properties            should have normal.queue.name property and premium.queue.name
   *                              property
   * @param producer              the queue producer to be used
   * @param normalTokenGenerator  the normal token generator to be used
   * @param premiumTokenGenerator the premium token generator to be used
   */
  public TokenGenerationCounter(Properties properties, ProducerKafka producer,
      NormalTokenGenerator normalTokenGenerator, PremiumTokenGenerator premiumTokenGenerator) {
    this.normalQueueName = properties.getProperty("normal.queue.name");
    this.premiumQueueName = properties.getProperty("premium.queue.name");
    this.producer = producer;
    this.normalTokenGenerator = normalTokenGenerator;
    this.premiumTokenGenerator = premiumTokenGenerator;
  }

  /**
   * Generates normal token and publishes to queue
   *
   * @return generated token string or `Err` if publishing to queue fails
   */
  public String generateNormalTokenAfterVerification() {
    String token = normalTokenGenerator.generateToken();
    try {
      producer.sendMessage(normalQueueName, token);
      return token;
    } catch (ExecutionException | InterruptedException e) {
      logger.error("Publishing to Queue Failed", e);
      producer.close();
    }
    return "Err";
  }


  /**
   * Generates premium token and publishes to queue.
   *
   * @return generated token string or `Err` if publishing to queue fails
   */
  public String generatePremiumTokenAfterVerification() {
    String token = premiumTokenGenerator.generateToken();
    try {
      producer.sendMessage(premiumQueueName, token);
      return token;
    } catch (ExecutionException | InterruptedException e) {
      logger.error("Publishing to Queue Failed", e);
      producer.close();
    }
    return "Err";
  }
}
