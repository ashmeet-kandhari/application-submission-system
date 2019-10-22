package com.brillio.token;

public class NormalTokenGenerator implements TokenGenerator {

  private static int counter = 1;

  public String generateToken() {
    return "N" + counter++;
  }
}
