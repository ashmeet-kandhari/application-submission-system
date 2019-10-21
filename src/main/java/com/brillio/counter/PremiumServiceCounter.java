package com.brillio.counter;

import com.brillio.kafka.ConsumerKafka;

public class PremiumServiceCounter implements ServiceCounter {
    private ConsumerKafka consumerKafka;

    public PremiumServiceCounter() {
        this.consumerKafka = new ConsumerKafka("premiumToken");
    }

    public PremiumServiceCounter(ConsumerKafka consumerKafka) {
        this.consumerKafka = consumerKafka;
    }

    @Override
    public String nextToken() {
        return consumerKafka.getMessage();
    }
}
