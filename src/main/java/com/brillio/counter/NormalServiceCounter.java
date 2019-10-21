package com.brillio.counter;

import com.brillio.kafka.ConsumerKafka;

public class NormalServiceCounter implements ServiceCounter {
    private ConsumerKafka consumerKafka;

    public NormalServiceCounter() {
        this.consumerKafka = new ConsumerKafka("normalToken");
    }

    public NormalServiceCounter(ConsumerKafka consumerKafka) {
        this.consumerKafka = consumerKafka;
    }

    @Override
    public String nextToken() {
        return consumerKafka.getMessage();
    }
}
