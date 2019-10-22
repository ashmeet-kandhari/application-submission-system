package com.brillio.counter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.brillio.queue.ProducerKafka;
import com.brillio.token.NormalTokenGenerator;
import com.brillio.token.PremiumTokenGenerator;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TokenGenerationCounterTest {

  TokenGenerationCounter tokenGenerationCounter;

  @Mock
  Properties properties;
  @Mock
  ProducerKafka producerKafka;
  @Mock
  NormalTokenGenerator normalTokenGenerator;
  @Mock
  PremiumTokenGenerator premiumTokenGenerator;

  @BeforeEach
  void setUp() {
    when(properties.getProperty(eq("normal.queue.name"))).thenReturn("normalTest");
    when(properties.getProperty(eq("premium.queue.name"))).thenReturn("premiumTest");

    tokenGenerationCounter = new TokenGenerationCounter(properties, producerKafka,
        normalTokenGenerator, premiumTokenGenerator);
  }

  @Test
  void shouldInitiateDefaultTokenGeneratorsIfNotProvidedInArguments() {
    TokenGenerationCounter tokenGenerationCounter = new TokenGenerationCounter(properties,
        producerKafka);

    verify(properties, atLeastOnce()).getProperty(eq("normal.queue.name"));
    verify(properties, atLeastOnce()).getProperty(eq("premium.queue.name"));
  }

  @Test
  void generateNormalTokenAfterVerificationShouldCallTokenGeneratorToGenerateNormalTokenAndPublishToProducerForGivenQueueName()
      throws ExecutionException, InterruptedException {
    String expectedToken = "N12";
    when((normalTokenGenerator.generateToken())).thenReturn(expectedToken);

    String actualToken = tokenGenerationCounter.generateNormalTokenAfterVerification();

    verify(normalTokenGenerator, times(1)).generateToken();
    verify(producerKafka, times(1)).sendMessage(eq("normalTest"), eq(expectedToken));
    Assertions.assertEquals(expectedToken, actualToken);
  }

  @Test
  void generateNormalTokenAfterVerificationShouldCallTokenGeneratorToGenerateNormalTokenAndProducerThrowsExceptionWhichMakesItClose()
      throws ExecutionException, InterruptedException {
    String expectedToken = "Err";
    when(normalTokenGenerator.generateToken()).thenReturn("N12");
    doThrow(ExecutionException.class).when(producerKafka).sendMessage(anyString(), anyString());

    String actualToken = tokenGenerationCounter.generateNormalTokenAfterVerification();

    verify(normalTokenGenerator, times(1)).generateToken();
    verify(producerKafka, times(1)).sendMessage(anyString(), anyString());
    verify(producerKafka, times(1)).close();
    Assertions.assertEquals(expectedToken, actualToken);
  }


  @Test
  void generatePremiumTokenAfterVerificationShouldCallTokenGeneratorToGeneratePremiumTokenAndPublishToProducerForGivenQueueName()
      throws ExecutionException, InterruptedException {
    String expectedToken = "P12";
    when((premiumTokenGenerator.generateToken())).thenReturn(expectedToken);

    String actualToken = tokenGenerationCounter.generatePremiumTokenAfterVerification();

    verify(premiumTokenGenerator, times(1)).generateToken();
    verify(producerKafka, times(1)).sendMessage(eq("premiumTest"), eq(expectedToken));
    Assertions.assertEquals(expectedToken, actualToken);
  }


  @Test
  void generatePremiumTokenAfterVerificationShouldCallTokenGeneratorToGeneratePremiumTokenAndProducerThrowsExceptionWhichMakesItClose()
      throws ExecutionException, InterruptedException {
    String expectedToken = "Err";
    when(premiumTokenGenerator.generateToken()).thenReturn("P12");
    doThrow(ExecutionException.class).when(producerKafka).sendMessage(anyString(), anyString());

    String actualToken = tokenGenerationCounter.generatePremiumTokenAfterVerification();

    verify(premiumTokenGenerator, times(1)).generateToken();
    verify(producerKafka, times(1)).sendMessage(anyString(), anyString());
    verify(producerKafka, times(1)).close();
    Assertions.assertEquals(expectedToken, actualToken);
  }
}