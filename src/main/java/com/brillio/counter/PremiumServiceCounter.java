package com.brillio.counter;

import com.brillio.queue.Consumer;

public class PremiumServiceCounter implements ServiceCounter {

  private Consumer consumer;

  public PremiumServiceCounter(Consumer consumer) {
    this.consumer = consumer;
  }

  @Override
  public String nextToken() {
    return consumer.getMessage();
  }
}
