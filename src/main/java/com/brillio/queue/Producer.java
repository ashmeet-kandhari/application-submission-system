package com.brillio.queue;

import java.util.concurrent.ExecutionException;

public interface Producer {

  void sendMessage(String topicName, String message)
      throws ExecutionException, InterruptedException;

  void close();

}
