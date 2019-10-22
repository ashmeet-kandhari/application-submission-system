package com.brillio.counter;

import com.brillio.queue.Consumer;

public class NormalServiceCounter implements ServiceCounter {

  private Consumer consumer;

  public NormalServiceCounter(Consumer consumer) {
    this.consumer = consumer;
  }

  @Override
  public String nextToken() {
    return consumer.getMessage();
  }
}
