package com.brillio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import org.junit.jupiter.api.Test;

class PropertiesLoaderTest {

  @Test
  void loadPropertiesShouldHaveSameKeysAsPresentInTest() throws IOException {
    Properties props = new Properties();
    ClassLoader classLoader = PropertiesLoaderTest.class.getClassLoader();
    try (InputStream resourceStream = classLoader.getResourceAsStream("application.properties")) {
      props.load(Objects.requireNonNull(resourceStream));
    }
    assertEquals(PropertiesLoader.loadProperties().keySet(), props.keySet());
  }
}