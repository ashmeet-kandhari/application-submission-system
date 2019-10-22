package com.brillio.token;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NormalTokenGeneratorTest {

  TokenGenerator tokenGenerator;

  @BeforeEach
  void setUp() {
    tokenGenerator = new NormalTokenGenerator();
  }

  @Test
  void generateTokenShouldGenerateInIncrementsWhenCalledMultipleTimes() {
    String token1 = tokenGenerator.generateToken();
    String token2 = tokenGenerator.generateToken();

    assertEquals(token1, "N1");
    assertEquals(token2, "N2");

  }
}