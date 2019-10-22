package com.brillio.token;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PremiumTokenGeneratorTest {

  TokenGenerator tokenGenerator;

  @BeforeEach
  void setUp() {
    tokenGenerator = new PremiumTokenGenerator();
  }

  @Test
  void generateTokenShouldGenerateInIncrementsWhenCalledMultipleTimes() {
    String token1 = tokenGenerator.generateToken();
    String token2 = tokenGenerator.generateToken();

    assertEquals(token1, "P1");
    assertEquals(token2, "P2");

  }
}