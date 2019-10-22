package com.brillio.counter;

import com.brillio.queue.ConsumerKafka;

public class PremiumServiceCounter implements ServiceCounter {

  private ConsumerKafka consumerKafka;

  public PremiumServiceCounter(ConsumerKafka consumerKafka) {
    this.consumerKafka = consumerKafka;
  }

  @Override
  public String nextToken() {
    return consumerKafka.getMessage();
  }
}
