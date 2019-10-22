package com.brillio.queue;

import com.brillio.PropertiesLoader;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerKafka implements com.brillio.queue.Consumer {

  private static final Properties appConfig = PropertiesLoader.loadProperties();
  private static final Properties props = consumerKafkaConfig();
  private static List<ConsumerRecord<String, String>> records;
  private List<String> topics;

  public ConsumerKafka(String... topics) {
    this.topics = Arrays.asList(topics);
  }

  private static Properties consumerKafkaConfig() {
    Properties consumerProps = new Properties();
    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.get("kafka.brokers"));
    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, appConfig.get("group.id"));
    consumerProps
        .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, appConfig.get("key.deserializer"));
    consumerProps
        .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, appConfig.get("value.deserializer"));
    consumerProps
        .put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, appConfig.get("consumer.auto.commit"));
    consumerProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,
        appConfig.get("consumer.auto.commit.interval.ms"));
    consumerProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, appConfig.get("max.poll.records"));

    return consumerProps;
  }

  private synchronized void poll() {
    Consumer<String, String> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(topics);
    int noMessageToFetch = 0;

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
      if (records.isEmpty()) {
        noMessageToFetch++;
        if (noMessageToFetch > Integer
            .parseInt(appConfig.getProperty("max.no.message.polling.seconds"))) {
          break;
        }
        continue;
      }
      List<ConsumerRecord<String, String>> actualList = new LinkedList<>();
      records.iterator().forEachRemaining(actualList::add);
      ConsumerKafka.records = actualList;
      break;
    }
    consumer.close();
  }

  @Override
  public String getMessage() {
    if (records == null || records.isEmpty()) {
      poll();
    }
    ConsumerRecord<String, String> record = records.remove(0);
    return record.value();
  }
}

