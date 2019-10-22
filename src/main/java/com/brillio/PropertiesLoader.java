package com.brillio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import org.apache.log4j.Logger;

public class PropertiesLoader {

  final static Logger logger = Logger.getLogger(PropertiesLoader.class);
  private static final String fileName = "application.properties";
  private static Properties props;

  public static Properties loadProperties() {
    if (props == null) {
      props = new Properties();
      ClassLoader classLoader = PropertiesLoader.class.getClassLoader();
      try (InputStream resourceStream = classLoader.getResourceAsStream(fileName)) {
        props.load(Objects.requireNonNull(resourceStream));
      } catch (IOException | NullPointerException e) {
        logger.error("Properties file loading error", e);
      }
    }
    return props;
  }

}
