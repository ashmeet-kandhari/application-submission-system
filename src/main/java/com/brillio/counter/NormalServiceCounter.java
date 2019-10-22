package com.brillio.counter;

import com.brillio.queue.ConsumerKafka;

public class NormalServiceCounter implements ServiceCounter {

  private ConsumerKafka consumerKafka;

  public NormalServiceCounter(ConsumerKafka consumerKafka) {
    this.consumerKafka = consumerKafka;
  }

  @Override
  public String nextToken() {
    return consumerKafka.getMessage();
  }
}
