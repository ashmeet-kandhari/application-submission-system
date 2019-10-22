package com.brillio.queue;

import com.brillio.PropertiesLoader;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerKafka implements Producer {

  private static KafkaProducer<String, String> producer = new KafkaProducer<>(
      kafkaProducerConfig());

  private static Properties kafkaProducerConfig() {
    Properties producerProps = new Properties();
    Properties appConfig = PropertiesLoader.loadProperties();
    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.get("kafka.brokers"));
    producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, appConfig.get("client.id"));
    producerProps.put(ProducerConfig.ACKS_CONFIG, appConfig.get("acks"));
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, appConfig.get("key.serializer"));
    producerProps
        .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, appConfig.get("value.serializer"));
    return producerProps;
  }

  @Override
  public void sendMessage(String topicName, String message)
      throws ExecutionException, InterruptedException {
    producer.send(new ProducerRecord<>(topicName, message)).get();
  }

  @Override
  public void close() {
    producer.close();
  }
}
