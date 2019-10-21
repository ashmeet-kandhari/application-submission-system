package com.brillio.kafka;

public interface IKafkaConstants {

    String KAFKA_BROKERS = "localhost:9092";
    Integer MESSAGE_COUNT = 1000;
    String CLIENT_ID = "client1";
    String GROUP_ID_CONFIG = "consumerGroup1";
    Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;
    Integer MAX_POLL_RECORDS = 1;

}