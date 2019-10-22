package com.brillio.counter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.brillio.queue.ConsumerKafka;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class NormalServiceCounterTest {

  @Mock
  ConsumerKafka consumerKafka;
  private ServiceCounter serviceCounter;

  @BeforeEach
  void setUp() {
    serviceCounter = new NormalServiceCounter(consumerKafka);
  }


  @Test
  void nextTokenCallsConsumerKafkaGetMessageMethod() {
    when(consumerKafka.getMessage()).thenReturn("This test passed");
    serviceCounter.nextToken();

    verify(consumerKafka, times(1)).getMessage();
  }
}